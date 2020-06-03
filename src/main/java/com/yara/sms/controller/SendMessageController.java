package com.yara.sms.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.utils.ClientFactory;
import com.mashape.unirest.request.body.MultipartBody;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SendMessageController {

    @Value("${application.key}")
    private String API_KEY;

    @Value("${application.username}")
    private String USERNAME;

    private String MESSAGE;

    @Value("${application.serviceCode}")
    private String SERVICE_CODE;

    @Value("${application.sms.endpoint}")
    private String AT_ENDPOINT;

    private Boolean ENQUEUE=false;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("sms/send")
    public Map handleSendMessage() throws UnirestException {
        generateMessage();
        setObjectMapper();
        Map response = sendSMSWithUnirest(getRecipients());
        //Map response1 = sendSMSWithRestTemplate(getRecipients());
        return response;
    }

    private Map sendSMSWithUnirest(String[] recipients) throws UnirestException {
        Map<String, Object> requestParams= new HashMap<>();
        requestParams.put("username", USERNAME);
        requestParams.put("from", SERVICE_CODE);
        requestParams.put("to", String.join(",", recipients));
        requestParams.put("message", "Hello");

        HttpResponse<Map> response0 = Unirest.post(AT_ENDPOINT)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .header("apiKey", API_KEY)
                .fields(requestParams).asObject(Map.class);

        return response0.getBody();
    }

    private Map sendSMSWithRestTemplate(String[] recipients) throws UnirestException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("apiKey", API_KEY);

        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("username", USERNAME);
        requestParams.add("from", SERVICE_CODE);
        requestParams.add("to", String.join(",", recipients));
        requestParams.add("message", "Hello");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestParams, headers);

        ResponseEntity<Map> response = restTemplate.exchange(AT_ENDPOINT, HttpMethod.POST, request, Map.class);
        return response.getBody();
    }

    private String[] getRecipients() {
        return new String[] {"+254739496441", "+254739496030"};
    }

    private void generateMessage() {
        MESSAGE="Dear Mauriz , XXXX is helping you to improve your yield with free XXXXXX supply. kindly dial *384*44135# more details";
    }

    private void setObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            @SneakyThrows
            public String writeValue(Object value) {
                return mapper.writeValueAsString(value);
            }

            @SneakyThrows
            public <T> T readValue(String value, Class<T> valueType) {
                return mapper.readValue(value, valueType);
            }
        });
    }

}

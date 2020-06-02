package com.yara.sms.controller;

import com.africastalking.AfricasTalking;
import com.africastalking.sms.FetchMessageResponse;
import com.africastalking.sms.SendMessageResponse;
import com.yara.sms.model.AfricasTalkingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

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
    public AfricasTalkingResponse handleSendMessage() {
        generateMessage();
        ResponseEntity<AfricasTalkingResponse> response = sendSMS(getRecipients());
        if(response.hasBody()) {
            return response.getBody();
        }
        return null;
    }

    private ResponseEntity<AfricasTalkingResponse> sendSMS(String[] recipients) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("apiKey", API_KEY);

        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("username", USERNAME);
        requestParams.add("from", SERVICE_CODE);
        requestParams.add("to", "+254739496030");
        requestParams.add("message", "Hello");

        HttpEntity request = new HttpEntity(requestParams, headers);

        ResponseEntity<AfricasTalkingResponse> response = restTemplate.exchange(AT_ENDPOINT, HttpMethod.POST, request, AfricasTalkingResponse.class);
        return response;
    }

    private String[] getRecipients() {
        return new String[] {"+254739496441", "+254739496030"};
    }

    private void generateMessage() {
        MESSAGE="Dear Mauriz , XXXX is helping you to improve your yield with free XXXXXX supply. kindly dial *384*44135# more details";
    }

}

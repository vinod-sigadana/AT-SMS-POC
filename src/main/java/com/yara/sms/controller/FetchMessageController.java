package com.yara.sms.controller;

import com.africastalking.sms.FetchMessageResponse;
import com.yara.sms.model.AfricasTalkingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@RestController
public class FetchMessageController {

    @Value("${application.key}")
    private String API_KEY;

    @Value("${application.username}")
    private String USERNAME;

    @Value("${application.sms.endpoint}")
    private String AT_ENDPOINT;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("sms/fetch")
    public AfricasTalkingResponse handleFetchMessage() throws IOException {
        ResponseEntity<AfricasTalkingResponse> response = getSMS();
        if(response.hasBody()) {
            return response.getBody();
        }
        return null;
    }

    private ResponseEntity<AfricasTalkingResponse> getSMS() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("apiKey", API_KEY);

        HttpEntity request = new HttpEntity(headers);

        return restTemplate.exchange(AT_ENDPOINT + "?username=" + USERNAME, HttpMethod.GET, request, AfricasTalkingResponse.class);
    }

}

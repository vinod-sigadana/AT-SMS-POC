package com.yara.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

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
    public Map handleFetchMessage() {
        ResponseEntity<Map> response = getSMS();
        if(response.hasBody()) {
            return response.getBody();
        }
        return null;
    }

    private ResponseEntity<Map> getSMS() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("apiKey", API_KEY);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(AT_ENDPOINT + "?username=" + USERNAME, HttpMethod.GET, request, Map.class);
        return response;
    }

}

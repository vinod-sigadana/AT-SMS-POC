package com.yara.sms.controller;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class FetchMessageController {

    @Value("${application.key}")
    private String API_KEY;

    @Value("${application.username}")
    private String USERNAME;

    @GetMapping("sms/fetch/{lastReceivedId}")
    public List<Message> handleFetchMessage(@PathVariable Integer lastReceivedId) {
        List<Message> response = getSMSWithAT(lastReceivedId);
        return response;
    }

    private List<Message> getSMSWithAT(Integer lastReceivedId) {
        AfricasTalking.initialize(USERNAME, API_KEY);
        SmsService smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        List<Message> recipientList = null;
        try {
            recipientList = smsService.fetchMessages(lastReceivedId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipientList;
    }

}

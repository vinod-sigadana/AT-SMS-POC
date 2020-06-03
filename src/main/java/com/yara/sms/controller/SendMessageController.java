package com.yara.sms.controller;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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

    private Boolean ENQUEUE=false;

    @PostMapping("sms/send")
    public List<Recipient> handleSendMessage() {
        generateMessage();
        List<Recipient> response = sendSMSWithAT(getRecipients());
        return response;
    }

    private List<Recipient> sendSMSWithAT(String[] recipients) {
        AfricasTalking.initialize(USERNAME, API_KEY);
        SmsService smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        List<Recipient> recipientList = null;
        try {
            recipientList = smsService.send(MESSAGE, SERVICE_CODE, recipients, ENQUEUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipientList;
    }

    private String[] getRecipients() {
        return new String[] {"+254739496441", "+254739496030"};
    }

    private void generateMessage() {
        MESSAGE="Dear Mauriz , XXXX is helping you to improve your yield with free XXXXXX supply. kindly dial *384*44135# more details";
    }

}

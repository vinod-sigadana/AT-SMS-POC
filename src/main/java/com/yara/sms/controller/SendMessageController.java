package com.yara.sms.controller;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Message;
import com.africastalking.sms.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
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
    public String handleSendMessage() throws IOException {
        generateMessage();
        AfricasTalking.initialize(API_KEY, USERNAME);
        List<Recipient> responses = sendSMS(getRecipients());
        System.out.println(responses.size());
        for (Recipient response : responses) {
            System.out.println(String.format("{ Number : %1$s\n" +"  Cost : %2$s \n" + "  Status : %3$s \n"+"  MessageId : %4$s \n}",
                    response.number, response.cost,response.status,response.messageId));
        }
        return "SUCCESS";
    }

    @GetMapping("sms/fetch")
    public String handleFetchMessage() throws IOException {
        AfricasTalking.initialize(API_KEY, USERNAME);
        List<Message> responses = getSMS();
        System.out.println(responses.size());
        return "SUCCESS";
    }

    private List<Message> getSMS() throws IOException {
        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        return sms.fetchMessages(0);
    }

    private List<Recipient> sendSMS(String[] recipients) throws IOException {
        SmsService smsService =AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        return smsService.send(MESSAGE, SERVICE_CODE, recipients, ENQUEUE);
    }



    private String[] getRecipients() {
        return new String[] {"+254739496441", "+254739496030"};
    }

    private void generateMessage() {
        MESSAGE="Dear Mauriz , XXXX is helping you to improve your yield with free XXXXXX supply. kindly dial *384*44135# more details";
    }

}

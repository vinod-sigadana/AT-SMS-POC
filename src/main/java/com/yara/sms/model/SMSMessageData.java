package com.yara.sms.model;

import com.africastalking.sms.Message;
import com.africastalking.sms.Recipient;
import lombok.Data;

import java.util.List;

@Data
public class SMSMessageData {
    private List<Message> Messages;
    private String Message;
    private List<Recipient> Recipients;
}

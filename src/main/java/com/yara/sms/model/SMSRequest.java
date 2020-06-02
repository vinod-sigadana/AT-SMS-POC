package com.yara.sms.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SMSRequest {
    private String username;
    private String to;
    private String message;
}

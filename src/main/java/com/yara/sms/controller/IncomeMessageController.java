package com.yara.sms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IncomeMessageController {
    @PostMapping(value = "/sms/income", produces = "text/plain")
    public String handleIncomingMessages(@RequestParam String date,
                                         @RequestParam String from,
                                         @RequestParam String id,
                                         @RequestParam @Nullable String linkid,
                                         @RequestParam String text,
                                         @RequestParam String to,
                                         @RequestParam String networkCode) {
        log.info("Request: {}", text);
        return text;
    }
}

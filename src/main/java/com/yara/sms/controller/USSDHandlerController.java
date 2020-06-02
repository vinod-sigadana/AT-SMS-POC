package com.yara.sms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class USSDHandlerController {

    private String province = "";
    private String crop = "";

    private static final List<String> PROVINCES = Arrays.asList("Kakamega (Western Province)",
            "Machakos (Eastern Province)", "Rift Valley", "Nyanza");
    private static final List<String> CROPS = Arrays.asList("Maize", "Wheat", "Bananas");

    @PostMapping(value = "/", produces = "text/plain")
    public String handleATRequest(@RequestParam String sessionId,
                                  @RequestParam String phoneNumber,
                                  @RequestParam String networkCode,
                                  @RequestParam String serviceCode,
                                  @RequestParam String text) {
        log.info("USSD request from AT Service:\nsessionId: {}, phoneNumber: {}, networkCode: {}, serviceCode: {}, text: {}",
                sessionId, phoneNumber, networkCode, serviceCode, text);
        String response;
        text = (text == null) ? "" : text;
        if(text.equals("")) {
            response = getMainMenu();
        } else if (text.equals("1")) {
            response = getProvinceMenu();
        } else if (text.equals("2")) {
            response = getCropMenu();
        } else if (text.startsWith("1*") && text.length() == 3) {
            response = processProvinceSelection(text.substring(text.length() - 1));
        } else if (text.startsWith("2*") && text.length() == 3) {
            response = processCropSelection(text.substring(text.length() - 1));
        } else {
            response = "END Invalid input. Please try again.";
        }
        return response;
    }

    private String processCropSelection(String input) {
        String response = getThanksMsg();
        switch (input) {
            case "1":
                crop = CROPS.get(0);
                break;
            case "2":
                crop = CROPS.get(1);
                break;
            case "3":
                crop = CROPS.get(2);
                break;
            case "4":
                crop = CROPS.get(3);
                break;
            default:
                response = "END Invalid input\n";
        }
        response = response.concat("\n selected crop: " + crop);
        return response;
    }

    private String processProvinceSelection(String input) {
        String response = getThanksMsg();
        switch (input) {
            case "1":
                province = PROVINCES.get(0);
                break;
            case "2":
                province = PROVINCES.get(1);
                break;
            case "3":
                province = PROVINCES.get(2);
                break;
            case "4":
                province = PROVINCES.get(3);
                break;
            default:
                response = "END Invalid input\n";
        }
        response = response.concat("\nselected province: " + province);
        return response;
    }

    private String getThanksMsg() {
        return "END Thanks for your time. Have a good day.";
    }

    private String getCropMenu() {
        StringBuilder response = new StringBuilder("CON Please select your crops:\n");
        for(int i=1;i<=CROPS.size();i++) {
            response.append(i).append(". ").append(CROPS.get(i - 1)).append("\n");
        }
        return response.toString();
    }

    private String getMainMenu() {
        return "CON Please select an option enter:\n" +
                "1. Province Info\n" +
                "2. Crop Details";
    }

    private String getProvinceMenu() {
        StringBuilder response = new StringBuilder("CON Please select your province:\n");
        for(int i=1;i<=PROVINCES.size();i++) {
            response.append(i).append(". ").append(PROVINCES.get(i - 1)).append("\n");
        }
        return response.toString();
    }
}

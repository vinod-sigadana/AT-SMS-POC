package com.yara.sms.csv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Farmer {
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private List<String> crops;
    private List<String> address;
}

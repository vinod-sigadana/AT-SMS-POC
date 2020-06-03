package com.yara.sms.csv.controller;

import com.yara.sms.csv.model.Farmer;
import com.yara.sms.csv.util.ExcelGenerator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DownloadExcelController {

    @GetMapping(value = "/download/farmers.xlsx")
    public ResponseEntity<InputStreamResource> excelCustomersReport() throws IOException {

        ByteArrayInputStream in = ExcelGenerator.prepareExcel(getFarmersList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=farmers.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    private List<Farmer> getFarmersList() {
        List<Farmer> farmers = new ArrayList<>();
        farmers.add(new Farmer("1234567890", "John", "Gelecki", null, null));
        farmers.add(new Farmer("1234567890", "Jim", "Parrison", null, null));
        farmers.add(new Farmer("1234567890", "Jack", "Pearson", null, null));
        farmers.add(new Farmer("1234567890", "Rebecca", "Pearson", null, null));
        farmers.add(new Farmer("1234567890", "Mandy", "Moore", null, null));
        farmers.add(Farmer.builder().mobileNumber("1234667890").build());
        farmers.add(Farmer.builder().mobileNumber("1234767890").build());
        farmers.add(Farmer.builder().mobileNumber("1234867890").build());
        farmers.add(Farmer.builder().mobileNumber("1234967890").build());
        return farmers;
    }
}

package com.yara.sms.csv.controller;

import com.yara.sms.csv.model.Farmer;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class UploadExcelController {

    @PostMapping(value = "/upload")
    public ResponseEntity<List<Farmer>> uploadExcelFile(MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheet("Farmers");

        List<Farmer> farmersList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Row row = sheet.getRow(i);
            farmersList.add(new Farmer(row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getStringCellValue()));
        }
        return new ResponseEntity<>(farmersList, HttpStatus.OK);
    }
}

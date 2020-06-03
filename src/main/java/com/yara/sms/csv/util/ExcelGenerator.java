package com.yara.sms.csv.util;

import com.yara.sms.csv.model.Farmer;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTProtectedRange;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExcelGenerator {
    public static ByteArrayInputStream prepareExcel(List<Farmer> farmers) throws IOException {
        String[] columnHeaders = {"Mobile Number", "First Name", "Last Name"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.createSheet("Farmers");

            sheet.setAutoFilter(CellRangeAddress.valueOf("A:C"));
            ((XSSFSheet)sheet).lockAutoFilter(false);

            //Below commented code is to enable SORTING in protected range. But the range will editable.
            /*
            CTProtectedRange protectedRange = ((XSSFSheet)sheet).getCTWorksheet()
                    .addNewProtectedRanges()
                    .addNewProtectedRange();
            protectedRange.setName("enableSorting");
            protectedRange.setSqref(Arrays.asList("A:C"));
            */
            ((XSSFSheet)sheet).lockSort(false);
            sheet.protectSheet("");

            createHeaderRow(columnHeaders, sheet, workbook);
            createBodyRows(farmers, sheet, workbook);
            autoSizeColumns(sheet, columnHeaders);
            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private static void autoSizeColumns(Sheet sheet, String[] columnHeaders) {
        for (int column = 0; column < columnHeaders.length; column++) {
            sheet.autoSizeColumn(column);
        }
    }

    private static void createBodyRows(List<Farmer> farmers, Sheet sheet, Workbook workbook) {
        CellStyle unlockedCell = workbook.createCellStyle();
        unlockedCell.setLocked(false);

        CellStyle lockedCell = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
        lockedCell.setFont(font);
        lockedCell.setLocked(true);

        int rowIdx = 1;
        for (Farmer farmer:farmers) {
            Row row = sheet.createRow(rowIdx++);

            Cell mobileNumberCell = row.createCell(0);
            mobileNumberCell.setCellValue(farmer.getMobileNumber());
            mobileNumberCell.setCellStyle(lockedCell);

            Cell firstNameCell = row.createCell(1);
            firstNameCell.setCellValue(farmer.getFirstName());
            firstNameCell.setCellStyle(unlockedCell);

            Cell lastNameCell = row.createCell(2);
            lastNameCell.setCellValue(farmer.getLastName());
            lastNameCell.setCellStyle(unlockedCell);
        }
    }

    private static void createHeaderRow(String[] columnHeaders, Sheet sheet, Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setLocked(true);

        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columnHeaders.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columnHeaders[col]);
            cell.setCellStyle(headerCellStyle);
        }
    }
}

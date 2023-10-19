//package com.emtechhouse.reconmasterLitebackend.Services;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class JsonData {
//
//    public static String extractDataToJson(MultipartFile file) throws IOException {
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            JSONArray jsonArray = new JSONArray();
//            Iterator<Row> rowIterator = sheet.iterator();
//
//            Row headerRow = rowIterator.next();
//            List<String> columnNames = extractColumnNames(headerRow);
//
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//
//                // Check if the row is empty (all cells are empty)
//                if (isRowEmpty(row)) {
//                    continue;
//                }
//
//                JSONObject jsonObject = new JSONObject();
//
//                for (int i = 0; i < columnNames.size(); i++) {
//                    Cell cell = row.getCell(i);
//                    String columnName = columnNames.get(i);
//
//                    // Convert cell value to string
//                    String cellValue = "";
//                    if (cell != null) {
//                        cell.setCellType(CellType.STRING);
//                        cellValue = cell.getStringCellValue();
//                    }
//
//                    jsonObject.put(columnName, cellValue);
//                }
//
//                jsonArray.put(jsonObject);
//            }
//
//            return jsonArray.toString();
//        }
//    }
//
//    private static List<String> extractColumnNames(Row headerRow) {
//        List<String> columnNames = new ArrayList<>();
//        Iterator<Cell> cellIterator = headerRow.cellIterator();
//
//        while (cellIterator.hasNext()) {
//            Cell cell = cellIterator.next();
//            String columnName = cell.getStringCellValue().trim();
//
//            if (!columnName.isEmpty()) {
//                columnNames.add(columnName);
//            }
//        }
//
//        return columnNames;
//    }
//
//    private static boolean isRowEmpty(Row row) {
//        Iterator<Cell> cellIterator = row.cellIterator();
//        while (cellIterator.hasNext()) {
//            Cell cell = cellIterator.next();
//            if (cell != null && cell.getCellType() != CellType.BLANK) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//}

//package com.emtechhouse.reconmasterLitebackend.Services;
//import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
//import com.emtechhouse.reconmasterLitebackend.Models.ReconManager;
//import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
//import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
//import com.emtechhouse.reconmasterLitebackend.Repositories.ReconManagerRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.*;
//import java.util.*;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class FileReader {
//    private final FinacleStagingRepository finacleStagingRepository;
//    private final ReconManagerRepository reconManagerRepository;
//
//    @Value("${spring.application.files.hello-paissa}")
//    String filePath1;
//
//    public EntityResponse<List<Map<String, String>>> readTextFile(MultipartFile file) {
//        EntityResponse<List<Map<String, String>>> response = new EntityResponse<>();
//        List<Map<String, String>> fileData = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            String line;
//
//            String headerLine = br.readLine();
//            if (headerLine == null) {
//                response.setMessage("File is empty.");
//                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                return response;
//            }
//
//            String detectedDelimiter = detectDelimiter(headerLine);
//
//            if (detectedDelimiter == null) {
//                response.setMessage("Delimiter not detected in the header.");
//                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                return response;
//            }
//
//            String[] headers = headerLine.trim().split(detectedDelimiter);
//
//            while ((line = br.readLine()) != null) {
//                String[] divLine = line.trim().split(detectedDelimiter);
//                if (divLine.length >= headers.length) {
//                    Map<String, String> dataMap = new HashMap<>();
//
//                    for (int i = 0; i < headers.length; i++) {
//                        String header = headers[i];
//                        String columnValue = divLine[i].trim();
//                        dataMap.put(header, columnValue);
//                    }
//
//                    fileData.add(dataMap);
//                }
//            }
//
//            response.setMessage("File read successfully.");
//            response.setEntity(fileData);
//            response.setStatusCode(HttpStatus.OK.value());
//        } catch (IOException e) {
//            response.setMessage("Failed to read the file: " + e.getMessage());
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//        return response;
//    }
//
//    private String detectDelimiter(String headerLine) {
//        String[] potentialDelimiters = {",", "\t", "|", ";"};
//
//        for (String delimiter : potentialDelimiters) {
//            if (headerLine.contains(delimiter)) {
//                return delimiter;
//            }
//        }
//        return null;
//    }
//
//
////    public static String extractDataToJson(MultipartFile file) throws IOException {
////        try (InputStream inputStream = file.getInputStream()) {
////            Workbook workbook = new XSSFWorkbook(inputStream);
////
////            Sheet sheet = workbook.getSheetAt(0);
////
////            JSONArray jsonArray = new JSONArray();
////            Iterator<Row> rowIterator = sheet.iterator();
////
////            // Skip the first three rows (including headers)
////            for (int i = 0; i < 3; i++) {
////                if (rowIterator.hasNext()) {
////                    rowIterator.next();
////                }
////            }
////
////            List<String> columnNames = extractColumnNames(rowIterator.next());
////
////            while (rowIterator.hasNext()) {
////                Row row = rowIterator.next();
////
////                // Check if the row is empty (all cells are empty)
////                if (isRowEmpty(row)) {
////                    continue;
////                }
////
////                JSONObject jsonObject = new JSONObject();
////
////                for (int i = 0; i < columnNames.size(); i++) {
////                    Cell cell = row.getCell(i);
////                    String columnName = columnNames.get(i);
////
////                    // Check the cell type
////                    if (cell != null) {
////                        if (cell.getCellType() == CellType.NUMERIC) {
////                            // Handle numeric cells appropriately (e.g., convert to string)
////                            double numericValue = cell.getNumericCellValue();
////                            String cellValue = Double.toString(numericValue);
////                            jsonObject.put(columnName, cellValue);
////                        } else {
////                            cell.setCellType(CellType.STRING);
////                            String cellValue = cell.getStringCellValue();
////                            jsonObject.put(columnName, cellValue);
////                        }
////                    } else {
////                        jsonObject.put(columnName, ""); // Handle empty cells
////                    }
////                }
////
////                jsonArray.put(jsonObject);
////            }
////
////            return jsonArray.toString();
////        }
////    }
////
////    private static List<String> extractColumnNames(Row headerRow) {
////        List<String> columnNames = new ArrayList<>();
////        Iterator<Cell> cellIterator = headerRow.cellIterator();
////
////        while (cellIterator.hasNext()) {
////            Cell cell = cellIterator.next();
////            String columnName = cell.getStringCellValue().trim();
////
////            if (!columnName.isEmpty()) {
////                columnNames.add(columnName);
////            }
////        }
////
////        return columnNames;
////    }
////
////    private static boolean isRowEmpty(Row row) {
////        Iterator<Cell> cellIterator = row.cellIterator();
////        while (cellIterator.hasNext()) {
////            Cell cell = cellIterator.next();
////            if (cell != null && cell.getCellType() != CellType.BLANK) {
////                return false; // Row has at least one non-empty cell
////            }
////        }
////        return true;
////    }
//
//    public Map<String, ThirdPartyStaging> readPaissaData(String filePath) throws IOException {
//        Map<String, ThirdPartyStaging> paissaDataMap = new HashMap<>();
//
////    String filePath = "/home/konj/Downloads/HP  Daily Report.xlsx";
//
////    File file = new File(dir + "HP Daily Report"+dateString+".xlsx:")
//
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//        Workbook workbook = WorkbookFactory.create(fileInputStream);
//
//        Sheet sheet = workbook.getSheetAt(0);
//
//        for (Row curRow : sheet) {
//            // Skip rows 1 to 4
//            if (curRow.getRowNum() < 4) {
//                continue;
//            }
//
//            // Check if cells at index 0, 1, and 2 are empty
//            Cell cell0 = curRow.getCell(0);
//            Cell cell1 = curRow.getCell(1);
//            Cell cell2 = curRow.getCell(2);
//            if (cell0 == null || cell0.getCellType() == CellType.BLANK ||
//                    cell1 == null || cell1.getCellType() == CellType.BLANK ||
//                    cell2 == null || cell2.getCellType() == CellType.BLANK) {
//                // Skip the row if any of the cells at index 0, 1, or 2 are empty
//                continue;
//            }
//
//            ThirdPartyStaging paissaData = new ThirdPartyStaging();
//
//            // Set the data
//            Cell rrnCell = curRow.getCell(6);
//            if (rrnCell != null) {
//                String rrnValue;
//                if (rrnCell.getCellType() == CellType.NUMERIC) {
//                    rrnValue = String.valueOf((long) rrnCell.getNumericCellValue());
//                } else {
//                    rrnValue = rrnCell.getStringCellValue().trim();
//                }
//                paissaData.setRrn(rrnValue);
//            } else {
//                // Handles the case when the cell is null
//                paissaData.setRrn("");
//            }
////            paissaData.setSendDate(String.valueOf(curRow.getCell(7)));
////            paissaData.setPaidDate(String.valueOf(curRow.getCell(8)));
//////        paissaData.setAmount(String.valueOf(curRow.getCell(9)));
//
//            Cell amountCell = curRow.getCell(9);
//// Initialize the amount string
//            String amountString = "";
//// Check if the cell is not null and not blank
//            if (amountCell != null && amountCell.getCellType() != CellType.BLANK) {
//                // Extract the raw cell value as a string
//                String rawAmount = amountCell.toString();
//                // Remove any non-numeric characters (except for the decimal point)
//                amountString = rawAmount.replaceAll("[^\\d.]", "");
//            }
//            //paissaData.setAmount(amountString);
//
//            paissaDataMap.put(paissaData.getRrn(), paissaData);
//
//            // Console log the data
////        log.info("==============================");
////        log.info("Rrn: {}", paissaData.getRrn());
////        log.info("Sent Date: {}", paissaData.getSendDate());
////        log.info("Paid Date: {}", paissaData.getPaidDate());
////        log.info("Amount: {}", paissaData.getAmount());
////        log.info("==============================");
//        }
//
////    log.info("Total rows processed: {}", paissaDataMap.size());
//        return paissaDataMap;
//    }
//
//
//    //road to testing
//
//    public static String extractDataTo(MultipartFile file) throws IOException {
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
////    private static boolean isRowEmpty(Row row) {
////        Iterator<Cell> cellIterator = row.cellIterator();
////        while (cellIterator.hasNext()) {
////            Cell cell = cellIterator.next();
////            if (cell != null && cell.getCellType() != CellType.BLANK) {
////                return false; // Row has at least one non-empty cell
////            }
////        }
////        return true;
////    }
//
//    public EntityResponse createMultiple(List<ReconManager> reconManager) {
//        EntityResponse response = new EntityResponse();
//        try {
//            for (ReconManager reconManager1 : reconManager) {
//                Optional<ReconManager> reconManagerOptional = reconManagerRepository.findByRrn(reconManager1.getRrn());
//                if (reconManagerOptional.isPresent()) {
//                    ReconManager reconManager2 = reconManagerOptional.get();
//                    reconManager2.setRrn(reconManager2.getRrn());
//                    ReconManager savedRecon = reconManagerRepository.save(reconManager2);
//                } else {
////
////                    ReconManager savedRecon = reconManagerRepository.save();
////                    reconManager1.setRrn(savedRecon.getRrn());
//                }
//            }
//            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
//            response.setStatusCode(HttpStatus.CREATED.value());
//            response.setEntity(reconManager);
//            return response;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            response.setMessage("An error occurred while saving priority rrns.");
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            return response;
//        }
//    }
//
//    public static List<Map<String, String>> extractDataToList(MultipartFile file) throws IOException {
//        List<Map<String, String>> dataList = new ArrayList<>();
//
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            int headerRowIndex = 3;
//            Row headerRow = sheet.getRow(headerRowIndex);
//
//            if (headerRow != null) {
//                Iterator<Row> rowIterator = sheet.iterator();
//
//                while (rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
//
//                    // Skip rows before the header row
//                    if (row.getRowNum() < headerRowIndex) {
//                        continue;
//                    }
//                    // Extract data from the Excel columns based on the headers
//                    Iterator<Cell> cellIterator = row.iterator();
//                    Map<String, String> dataMap = new HashMap<>();
//
//                    while (cellIterator.hasNext()) {
//                        Cell cell = cellIterator.next();
//                        int columnIndex = cell.getColumnIndex();
//                        Cell headerCell = headerRow.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                        String header = headerCell.getStringCellValue().trim();
//                        String cellValue = cell.getStringCellValue().trim();
//                        dataMap.put(header, cellValue);
//                    }
//
//                    dataList.add(dataMap);
//                }
//            }
//        }
//
//        return dataList;
//    }
//
//    private static boolean isRowEmpty(Row row) {
//        if (row == null || row.getLastCellNum() <= 0) {
//            return true;
//        }
//
//        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
//            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//            if (cell != null && cell.getCellType() != CellType.BLANK) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public static String extractDataToJson(MultipartFile file) throws IOException {
//        List<Map<String, String>> dataList = extractDataToList(file);
//
//        JSONArray jsonArray = new JSONArray();
//        for (Map<String, String> dataMap : dataList) {
//            JSONObject jsonObject = new JSONObject();
//            for (String header : dataMap.keySet()) {
//                jsonObject.put(header, dataMap.get(header));
//            }
//            jsonArray.put(jsonObject);
//        }
//
//        return jsonArray.toString();
//    }
//
//    public static List<Map<String, String>> extractE(MultipartFile file) throws IOException {
//        List<Map<String, String>> dataList = new ArrayList<>();
//
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            int headerRowIndex = 3;
//            Row headerRow = sheet.getRow(headerRowIndex);
//
//            if (headerRow != null) {
//                Iterator<Row> rowIterator = sheet.iterator();
//
//                while (rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
//
//                    // Skip rows before the header row
//                    if (row.getRowNum() < headerRowIndex) {
//                        continue;
//                    }
//                    // Extract data from the Excel columns based on the headers
//                    Iterator<Cell> cellIterator = row.iterator();
//                    Map<String, String> dataMap = new HashMap<>();
//
//                    // Create a map to store the header and cell value pairs
//                    Map<Integer, String> headerCellValueMap = new HashMap<>();
//
//                    // Iterate over the header row and store the header and cell value pairs in the map
//                    for (int i = 0; i < headerRow.getLastCellNum(); i++) {
//                        Cell headerCell = headerRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                        String header = headerCell.getStringCellValue().trim();
//
//                        Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                        String cellValue = cell.getStringCellValue().trim();
//
//                        headerCellValueMap.put(i, header + ":" + cellValue);
//                    }
//
//                    // Iterate over the header and cell value pairs and add them to the data map
//                    for (Integer columnIndex : headerCellValueMap.keySet()) {
//                        String headerCellValue = headerCellValueMap.get(columnIndex);
//                        String header = headerCellValue.split(":")[0];
//                        String cellValue = headerCellValue.split(":")[1];
//
//                        dataMap.put(header, cellValue);
//                    }
//
//                    dataList.add(dataMap);
//                }
//            }
//        }
//
//        return dataList;
//    }
//
//    public static List<Map<String, String>> extractDataToLis(MultipartFile file) throws IOException {
//        List<Map<String, String>> dataList = new ArrayList<>();
//
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            int headerRowIndex = 3; // headers start at row index 3
//
//            Row headerRow = sheet.getRow(headerRowIndex);
//            int lastCellIndex = headerRow.getLastCellNum();
//
//            for (int rowIndex = headerRowIndex + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//                Row row = sheet.getRow(rowIndex);
//                if (row != null && isRowComplete(row, lastCellIndex)) {
//                    Map<String, String> dataMap = new HashMap<>();
//                    for (int cellIndex = 0; cellIndex < lastCellIndex; cellIndex++) {
//                        Cell headerCell = headerRow.getCell(cellIndex);
//                        Cell dataCell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//
//                        String header = headerCell.getStringCellValue().trim();
//                        String cellValue = dataCell.getStringCellValue().trim();
//
//                        if (!header.isEmpty() && !cellValue.isEmpty()) {
//                            dataMap.put(header, cellValue);
//                        }
//                    }
//
//                    dataList.add(dataMap);
//                }
//            }
//        }
//
//        return dataList;
//    }
//
//    private static boolean isRowComplete(Row row, int lastCellIndex) {
//        for (int cellIndex = 0; cellIndex < lastCellIndex; cellIndex++) {
//            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//            if (cell == null || cell.getCellType() == CellType.BLANK) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    public static List<Map<String, String>> extractDataFromResource(
//            String filetyp, String filename, String fileext, List<String> columns,
//            int headers, String rowSeparator, String fieldSeparator) throws IOException {
//        List<Map<String, String>> dataList = new ArrayList<>();
//
//       // String filePath = directoryPath + filename + "." + fileext;
//        String filePath = "src/main/resources/hp/test3.xlsx";
//
//
//        // Use Spring's resource loader to load the file
//        Resource resource = new ClassPathResource(filePath);
//
//        // Check if the resource exists
//        if (resource.exists()) {
//            try (InputStream inputStream = resource.getInputStream()) {
//                FileInputStream inputStream1=new FileInputStream(filePath);
//                Workbook workbook = new XSSFWorkbook(inputStream1);
//
//                if ("xlsx".equalsIgnoreCase(fileext)) {
//                    workbook = new XSSFWorkbook(inputStream1);
//                } else if ("csv".equalsIgnoreCase(fileext)) {
//                    // Handle CSV files
//                    // You can use a library like OpenCSV to parse CSV files
//                    // Example: CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
//                    // Read and process the CSV data
//                    // Close the reader when done
//                    // Remember to set the correct rowSeparator and fieldSeparator
//                } else {
//                    // Handle unsupported file types
//                    throw new IllegalArgumentException("Unsupported file extension: " + fileext);
//                }
//
//                Sheet sheet = workbook.getSheetAt(0);
//
//                int headerRowIndex = headers; // User-specified number of header rows
//
//                for (int rowIndex = headerRowIndex; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//                    Row row = sheet.getRow(rowIndex);
//                    if (row != null && isRowComplete(row, row.getLastCellNum())) {
//                        Map<String, String> dataMap = new HashMap<>();
//                        for (int cellIndex = 0; cellIndex < columns.size(); cellIndex++) {
//                            String header = columns.get(cellIndex); // Use user-specified columns
//                            Cell dataCell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//
//                            String cellValue = dataCell.getStringCellValue().trim();
//
//                            if (!header.isEmpty() && !cellValue.isEmpty()) {
//                                dataMap.put(header, cellValue);
//                            }
//                        }
//
//                        dataList.add(dataMap);
//                    }
//                }
//            } catch (Exception e) {
//                log.error("" + e);
//
//                return null;
//            }
//
//
//        }
//
//        return dataList;
//    }
//
//    public static List<Map<String, String>> extractD(
//            MultipartFile multipartFile, String filetyp, String fileext, List<String> columns,
//            int headers, String rowSeparator, String fieldSeparator) throws IOException {
//        List<Map<String, String>> dataList = new ArrayList<>();
//        DataFormatter dataFormatter = new DataFormatter();
//
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Workbook workbook;
//
//            if ("xlsx".equalsIgnoreCase(fileext)) {
//                workbook = new XSSFWorkbook(inputStream);
//            } else if ("csv".equalsIgnoreCase(fileext)) {
//                // Handle CSV files
//                // You can use a library like OpenCSV to parse CSV files
//                // Example: CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
//                // Read and process the CSV data
//                // Close the reader when done
//                // Remember to set the correct rowSeparator and fieldSeparator
//                throw new IllegalArgumentException("CSV file handling is not implemented yet.");
//            } else {
//                // Handle unsupported file types
//                throw new IllegalArgumentException("Unsupported file extension: " + fileext);
//            }
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            int headerRowIndex = headers; // User-specified number of header rows
//            Row headerRow = sheet.getRow(headerRowIndex);
//
//            if (headerRow != null) {
//                for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
//                    Cell headerCell = headerRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                    String header = dataFormatter.formatCellValue(headerCell).trim();
//                    columns.add(header);
//                }
//            }
//
//            for (int rowIndex = headerRowIndex + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//                Row row = sheet.getRow(rowIndex);
//                if (row != null) {
//                    Map<String, String> dataMap = new HashMap<>();
//
//                    for (int cellIndex = 0; cellIndex < columns.size(); cellIndex++) {
//                        String header = columns.get(cellIndex);
//                        Cell dataCell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//
//                        String cellValue = dataFormatter.formatCellValue(dataCell).trim();
//
//                        dataMap.put(header, cellValue);
//                    }
//
//                    dataList.add(dataMap);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return dataList;
//    }
//
//}
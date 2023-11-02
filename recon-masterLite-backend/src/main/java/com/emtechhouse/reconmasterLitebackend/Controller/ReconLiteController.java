//package com.emtechhouse.reconmasterLitebackend.Controller;
//import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
//import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
//import com.emtechhouse.reconmasterLitebackend.Models.ReconManager;
//import com.emtechhouse.reconmasterLitebackend.Services.*;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//
//@CrossOrigin
//@RestController
//@RequestMapping("api/v1/hello-paissa")
//@Slf4j
//@RequiredArgsConstructor
//public class ReconLiteController {
//    @Value("${spring.application.files.hello-paissa}")
//    String filePath;
//    //private final FileHandler fileHandler;
//    private final FileReader fileReader;
//    //private final FinacleStagingService finacleStagingService;
//    //private final ThirdPartyStagingService thirdPartyStagingService;
//    //private final ReconService reconService;
//    //private  final JsonComparisonService jsonComparisonService;
//   // private final JsonComparison jsonComparison;
//
//    private static final Map<String, String> JSON_FIELD_MAPPING = new HashMap<>();
//    static {
//        JSON_FIELD_MAPPING.put("rrn", "rrn");
//        JSON_FIELD_MAPPING.put("amount", "amount");
//    }
//    @PostMapping("/upload-hp-files")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") List<MultipartFile> files) throws IOException {
//
//        EntityResponse response = new EntityResponse();
//        if (files.isEmpty() || files.size() < 2) {
//            response.setMessage("Please select two files to upload.");
//            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        Path ExcPath = null;
//        Path FinPath = null;
//
//        for (MultipartFile file : files) {
//            byte[] bytes;
//            try {
//                bytes = file.getBytes();
//            } catch (IOException e) {
//                log.error("Failed to read the uploaded file: {}", file.getOriginalFilename());
//                response.setMessage("Failed to read the uploaded file: " + file.getOriginalFilename());
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            Path path = Paths.get(filePath + file.getOriginalFilename());
//            try {
//
//                Files.write(path, bytes);
//            } catch (Exception e) {
//                log.error("Failed to save the uploaded file: {}", file.getOriginalFilename());
//                response.setMessage("Failed to save the uploaded file: " + file.getOriginalFilename());
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            // Check file patterns
//            String fileName = file.getOriginalFilename();
//            if (fileName.contains("HP Daily Report")) {
//                ExcPath = Paths.get(filePath + file.getOriginalFilename());
//            } else if (fileName.contains("HP FINACLE")) {
//               FinPath = Paths.get(filePath + file.getOriginalFilename());
//            } else {
//                response.setMessage("Invalid file pattern. Please upload files with names containing 'HP Daily Report' and 'HP FINACLE'.");
//                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        if (ExcPath != null && FinPath != null) {
//            try {
//                //fileHandler.reconHandler(ExcPath, FinPath);
//
//                response.setMessage("Files uploaded and reconciliation is successful.");
//                response.setStatusCode(HttpStatus.OK.value());
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } catch (Exception e) {
//                log.error("Failed to reconcile the files: {}", e.getMessage());
//                response.setMessage("Failed to reconcile the files. Please try again.");
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } else {
//            response.setMessage("One or both of the files does not exist.");
//            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/upload-hp-files/test")
//    public ResponseEntity<?> uploadFiles(
//            @RequestParam("file") List<MultipartFile> files,
//            @RequestParam("columnHeader") String columnHeaderToCompare
//    ) throws IOException {
//
//        EntityResponse response = new EntityResponse();
//        if (files.isEmpty() || files.size() < 2) {
//            response.setMessage("Please select two files to upload.");
//            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        // Create HashMap to store data
//        Map<String, List<String>> dataMap = new HashMap<>();
//
//        boolean columnHeaderExistsInAllFiles = true;
//
//        for (MultipartFile file : files) {
//            try {
//                // Read the uploaded file's content as a string
//                String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
//
//                // Split the file content into rows (assuming rows are separated by newline characters)
//                String[] rows = fileContent.split("\r?\n");
//
//                String fileName = file.getOriginalFilename();
//
//                dataMap.put(fileName, Arrays.asList(rows));
//
//                // Check if the column header exists in the first row
//                String firstRow = rows[0];
//                String[] headers = firstRow.split(",");
//                boolean columnHeaderExists = false;
//
//                for (String header : headers) {
//                    if (header.trim().equals(columnHeaderToCompare)) {
//                        columnHeaderExists = true;
//                        break;
//                    }
//                }
//
//                if (!columnHeaderExists) {
//                    // Column header not found in this file
//                    columnHeaderExistsInAllFiles = false;
//                }
//            } catch (IOException e) {
//                log.error("Failed to read the uploaded file: {}", file.getOriginalFilename());
//                response.setMessage("Failed to read the uploaded file: " + file.getOriginalFilename());
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        if (!columnHeaderExistsInAllFiles) {
//            // The specified column header does not exist in all uploaded files
//            response.setMessage("The specified column header '" + columnHeaderToCompare + "' does not exist in all uploaded files.");
//            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        // Continue with the comparison of the specified column data
//
//        // Retrieve the column data for the first file
//        List<String> firstFileColumn = getColumnData(dataMap.values().iterator().next(), columnHeaderToCompare);
//
//        // Check if the data in the specified column matches across all files
//        boolean allFilesMatch = dataMap.values().stream()
//                .allMatch(fileData -> getColumnData(fileData, columnHeaderToCompare).equals(firstFileColumn));
//
//        if (allFilesMatch) {
//            response.setMessage("The data in the '" + columnHeaderToCompare + "' column of uploaded files matches.");
//        } else {
//            response.setMessage("The data in the '" + columnHeaderToCompare + "' column of uploaded files does not match.");
//        }
//
//        response.setStatusCode(HttpStatus.OK.value());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    private List<String> getColumnData(List<String> fileData, String columnHeader) {
//        System.out.println("getColumnData() called with columnHeader='" + columnHeader + "'");
//
//        // Find the index of the specified column header in the first row
//        String firstRow = fileData.get(0);
//        String[] headers = firstRow.split(",");
//        int columnIndex = -1;
//
//        for (int i = 0; i < headers.length; i++) {
//            if (headers[i].trim().equals(columnHeader)) {
//                columnIndex = i;
//                break;
//            }
//        }
//        if (columnIndex < 0) {
//            // The column header does not exist in the file
//            System.out.println("Column header '" + columnHeader + "' does not exist in the file.");
//            return new ArrayList<>();
//        }
//
//        // Store the values of the specified column in the file
//        // Store the values of the specified column in the file
//        List<String> columnData = new ArrayList<>();
//        for (int i = 1; i < fileData.size(); i++) {
//            String[] rowValues = fileData.get(i).split(",");
//            if (rowValues.length > columnIndex) {
//                columnData.add(rowValues[columnIndex].trim());
//            }
//        }
//
//        return columnData;
//    }
//
//
//    @PostMapping("/upload-hp-files/test2")
//    public ResponseEntity<String> compareExcelColumns(
//            @RequestParam("file1") MultipartFile file1,
//            @RequestParam("file2") MultipartFile file2,
//            @RequestParam("columnName1") String columnName1,
//            @RequestParam("columnName2") String columnName2
//    ) {
//        try {
//            Workbook workbook1 = new XSSFWorkbook(file1.getInputStream());
//            Workbook workbook2 = new XSSFWorkbook(file2.getInputStream());
//
//            if (workbook1.getNumberOfSheets() != workbook2.getNumberOfSheets()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("The number of sheets in the two files is different.");
//            }
//
//            for (int sheetIndex = 0; sheetIndex < workbook1.getNumberOfSheets(); sheetIndex++) {
//                Sheet sheet1 = workbook1.getSheetAt(sheetIndex);
//                Sheet sheet2 = workbook2.getSheetAt(sheetIndex);
//
//// Find the index of the specified column names in each sheet
//                int columnIndex1 = findColumnIndex(sheet1, columnName1);
//                int columnIndex2 = findColumnIndex(sheet2, columnName2);
//
//                if (columnIndex1 == -1 || columnIndex2 == -1) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                            .body("One or both of the specified columns do not exist in the sheets.");
//                }
//
//// Compare the values of the cells in the corresponding columns
//                if (!compareColumns(sheet1, sheet2, columnIndex1, columnIndex2)) {
//                    return ResponseEntity.status(HttpStatus.OK)
//                            .body("The data in the columns specified by '" + columnName1 + "' and '" + columnName2 + "' do not match.");
//                }
//            }
//
//            return ResponseEntity.status(HttpStatus.OK).body("The data in the columns specified by '" + columnName1 + "' and '" + columnName2 + "' matches.");
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while processing the files.");
//        }
//    }
//
//    private boolean compareColumns(Sheet sheet1, Sheet sheet2, int columnIndex1, int columnIndex2) {
//// Find the smaller number of rows between the two sheets
//        int minRows = Math.min(sheet1.getPhysicalNumberOfRows(), sheet2.getPhysicalNumberOfRows());
//
//// Loop through the rows of the smaller sheet
//        for (int rowIndex = 0; rowIndex < minRows; rowIndex++) {
//            Row row1 = sheet1.getRow(rowIndex);
//            Row row2 = sheet2.getRow(rowIndex);
//
//            Cell cell1 = row1.getCell(columnIndex1);
//            Cell cell2 = row2.getCell(columnIndex2);
//
//            if (!compareCells(cell1, cell2)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    private int findColumnIndex(Sheet sheet, String columnName) {
//        Row headerRow = sheet.getRow(0);
//        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
//            Cell cell = headerRow.getCell(i);
//            if (cell != null && cell.getCellType() == CellType.STRING) {
//                String headerValue = cell.getStringCellValue().trim();
//                if (headerValue.equalsIgnoreCase(columnName)) {
//                    return i;
//                }
//            }
//        }
//        return -1; // Column not found
//    }
//
//    private boolean compareCells(Cell cell1, Cell cell2) {
//        if (cell1 == null || cell2 == null) {
//            return false; // One or both cells are empty
//        }
//
//        if (cell1.getCellType() != cell2.getCellType()) {
//            return false; // Cell types are different
//        }
//
//        if (cell1.getCellType() == CellType.STRING) {
//            String value1 = cell1.getStringCellValue();
//            String value2 = cell2.getStringCellValue();
//            return Objects.equals(value1, value2);
//        } else if (cell1.getCellType() == CellType.NUMERIC) {
//            double value1 = cell1.getNumericCellValue();
//            double value2 = cell2.getNumericCellValue();
//            return Double.compare(value1, value2) == 0;
//        }
//
//        return true;
//    }
//
////    @PostMapping("/upload-hp-files/test3")
////    public ResponseEntity<EntityResponse<?>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
////        EntityResponse<?> response;
////
////        try {
////            response = fileHandler.fileTypeHandler(files);
////
////            if (response.getStatusCode() == HttpStatus.OK.value()) {
////                // If files were processed successfully, include the data in the response
////                EntityResponse<List<Map<String, String>>> fileDataResponse = fileReader.readTextFile(files.get(0));
////                response = EntityResponse.<List<Map<String, String>>>builder()
////                        .message("Files processed successfully.")
////                        .entity(fileDataResponse.getEntity())
////                        .statusCode(HttpStatus.OK.value())
////                        .build();
////                return ResponseEntity.ok(response);
////            } else {
////                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
////            }
////        } catch (IOException e) {
////            // Handle IOException if necessary
////            response = EntityResponse.<String>builder()
////                    .message("Failed to process uploaded files. Please try again.")
////                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
////                    .build();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
////        }
////    }
////
////    @PostMapping("/Upload/test")
//    public String uploadDataToDatabase(@RequestBody String jsonDataFromFrontend) {
//        try {
//            JSONArray jsonArray = new JSONArray(jsonDataFromFrontend);
//            List<FinacleStaging> dataToInsert = new ArrayList<>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                FinacleStaging data = new FinacleStaging();
//
//                // Map JSON values to entity fields dynamically
//                Iterator<String> jsonKeys = jsonObject.keys();
//                while (jsonKeys.hasNext()) {
//                    String jsonKey = jsonKeys.next();
//                    String entityField = JSON_FIELD_MAPPING.get(jsonKey);
//
//                    if (entityField != null) {
//                        Field field = FinacleStaging.class.getDeclaredField(entityField);
//                        field.setAccessible(true); // Make the field accessible
//
//                        // Determine the field's data type and perform appropriate conversion
//                        if (field.getType().equals(BigDecimal.class)) {
//                            BigDecimal value = new BigDecimal(jsonObject.getString(jsonKey));
//                            field.set(data, value);
//                        } else if (field.getType().equals(String.class)) {
//                            field.set(data, jsonObject.getString(jsonKey));
//                        } // Add more data types as needed
//                    }
//                }
//
//                dataToInsert.add(data);
//            }
//
//            finacleStagingService.saveData(dataToInsert); // Save data to the database
//
//            return "Data saved successfully";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }
////    @PostMapping("/compare")
////    public ResponseEntity<String> compareJsonFiles(
////            @RequestParam("finacleFile") MultipartFile fina,
////            @RequestParam("thirdPartyFile") MultipartFile third,
////            @RequestParam("columnsToCompare") List<String> columnsToCompare
////    ) {
////        try {
////            if (fina == null || third == null) {
////                // Log an error message if either file is missing
////                log.error("Please input two files for comparison.");
////                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
////                        .body("Please input two files for comparison.");
////            }
////
////            // Read and store JSON data using the service method
////            Map<String, Object> dataMap = jsonComparisonService.readAndStoreFiles(fina, third);
////
////            // Pass the data map to the comparison method
////            boolean comparisonResult = jsonComparisonService.compareJsonData(dataMap, columnsToCompare);
////
////            if (comparisonResult) {
////                return ResponseEntity.ok()
////                        .contentType(MediaType.TEXT_PLAIN)
////                        .body("The JSON data matches in the specified columns.");
////            } else {
////                return ResponseEntity.ok()
////                        .contentType(MediaType.TEXT_PLAIN)
////                        .body("The JSON data does not match in the specified columns.");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("An error occurred");
////        }
////    }
//
//    @PostMapping("/Upload/excel/file")
//    public ResponseEntity<String> uploadDataToDatabase(@RequestParam("file") MultipartFile file) {
//        try {
//            String jsonData = fileReader.extractDataTo(file);
//
//            if (jsonData != null && !jsonData.isEmpty()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(jsonData);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("Failed to extract data from the uploaded file");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred");
//        }
//    }
//    @PostMapping("/extract-data/one")
//    public String extractData(@RequestParam("file") MultipartFile file) throws IOException {
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//
//        // Skip the first three rows.
//        int startRow = 3;
//
//        // Get the header row.
//        Row headerRow = sheet.getRow(startRow);
//
//        // Create a map to store the extracted data.
//        Map<String, List<String>> data = new HashMap<>();
//
//        // Iterate over the rows in the sheet, starting from the fifth row.
//        for (int i = startRow + 1; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//
//            // Create a list to store the values in the row.
//            List<String> values = new ArrayList<>();
//
//            // Iterate over the cells in the row.
//            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
//                Cell cell = row.getCell(j);
//
//                // Get the value of the cell.
//                String value = cell.getStringCellValue();
//
//                // Add the value to the list.
//                values.add(value);
//            }
//
//            // Add the values to the map, using the header row values as keys.
//            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
//                String key = headerRow.getCell(j).getStringCellValue();
//                data.put(key, values);
//            }
//        }
//
//        // Close the workbook.
//        workbook.close();
//
//        // Convert the data map to JSON.
//        String json = new ObjectMapper().writeValueAsString(data);
//
//        // Return the JSON data.
//        return json;
//    }
//
//    @PostMapping("/upload/xml")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please upload an XML file.");
//        }
//
//        try {
//            XmlMapper xmlMapper = new XmlMapper();
//            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            Map<String, Object> data = xmlMapper.readValue(file.getInputStream(), Map.class);
//
//            ObjectMapper jsonMapper = new ObjectMapper();
//
//            String jsonData = jsonMapper.writeValueAsString(data);
//
//            // Optionally, you can pretty-print the JSON
//            // jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
//            // String prettyJsonData = jsonMapper.writeValueAsString(data);
//
//            return ResponseEntity.ok(jsonData);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the XML file.");
//        }
//    }
//    @PostMapping("/Upload/road")
//    public ResponseEntity<List> uploadDataToList(@RequestParam("file") MultipartFile file) {
//        try {
//            List jsonData = fileReader.extractDataToList(file);
//
//            if (jsonData != null && !jsonData.isEmpty()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(jsonData);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(Collections.singletonList("Failed to extract data from the uploaded file"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.singletonList("An error occured"));
//        }
//    }
//    @PostMapping("/create/multiple")
//    public EntityResponse create(@RequestBody List<ReconManager> custom) {
//        try {
//            EntityResponse response = fileReader.createMultiple(custom);
//            return response;
//        }catch (Exception e) {
//            log.info("Error {} "+e);
//            return null;
//        }
//    }
//
//    @GetMapping("/convert/csv")
//    public List<Map<String, String>> convertCsvToJson(@RequestParam("csvFilePath") String csvFilePath) {
//        List<Map<String, String>> jsonDataList = new ArrayList<>();
//
//        try (CSVReader csvReader = new CSVReader(new java.io.FileReader(csvFilePath))) {
//            String[] headers = csvReader.readNext();
//
//            if (headers != null) {
//                String[] rowData;
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                while ((rowData = csvReader.readNext()) != null) {
//                    Map<String, String> jsonData = new HashMap<>();
//                    for (int i = 0; i < headers.length; i++) {
//                        jsonData.put(headers[i], rowData[i]);
//                    }
//                    jsonDataList.add(jsonData);
//                }
//            }
//        } catch (IOException | CsvValidationException e) {
//            e.printStackTrace();
//        }
//
//        return jsonDataList;
//    }
//    @PostMapping(value = "/extract/E", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String extractDataToJson(@RequestParam("file") MultipartFile file) throws IOException {
//        return fileReader.extractE(file).toString();
//    }
//    @PostMapping("/Upload/excel/filess")
//    public ResponseEntity<EntityResponse<List<Map<String, String>>>> uploadDataToDatabas(@RequestParam("file") MultipartFile file) {
//        EntityResponse<List<Map<String, String>>> response = new EntityResponse<>();
//
//        try {
//            List<Map<String, String>> dataList = FileReader.extractDataToLis(file);
//
//            if (dataList != null && !dataList.isEmpty()) {
//                response.setMessage("Data extracted successfully.");
//                response.setEntity(dataList);
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setReconStatus(true); // Set your recon status as needed
//                response.setReconDate(new Date()); // Set your recon date as needed
//            } else {
//                response.setMessage("No data found in the uploaded file.");
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                response.setReconStatus(false); // Set your recon status as needed
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            response.setMessage("An error occurred while processing the file: " + e.getMessage());
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setReconStatus(false); // Set your recon status as needed
//        }
//
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @PostMapping("/upload/excel/files")
//    public ResponseEntity<EntityResponse<List<Map<String, String>>>> uploadDataToDatabase(
//            @RequestParam("filetype") String filetyp,
//            @RequestParam("filename") String filename,
//            @RequestParam("fileextension") String fileext,
//            @RequestParam("columns") List<String> columns,
//            @RequestParam("headers") int headers,
//            @RequestParam("rowseparator") String rowSeparator,
//            @RequestParam("fieldseparator") String fieldSeparator) {
//        EntityResponse<List<Map<String, String>>> response = new EntityResponse<>();
//
//        try {
//            List<Map<String, String>> dataList = FileReader.extractDataFromResource(
//                    filetyp, filename, fileext,columns, headers, rowSeparator, fieldSeparator);
//
//            if (dataList != null && !dataList.isEmpty()) {
//                response.setMessage("Data fetched successfully.");
//                response.setEntity(dataList);
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setReconStatus(true); // Set your recon status as needed
//                response.setReconDate(new Date()); // Set your recon date as needed
//            } else {
//                response.setMessage("No data found in the uploaded file.");
//                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                response.setReconStatus(false); // Set your recon status as needed
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMessage("An error occurred while processing the file: " + e.getMessage());
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setReconStatus(false); // Set your recon status as needed
//        }
//
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//    @PostMapping("/updated")
//    public ResponseEntity<ResponseEntity<Map<String, Object>>>
//    uploadExcelFiles(@RequestParam("filed") MultipartFile multipartFile,
//                     @RequestParam("filetype") String filetyp,
//                     @RequestParam("filename") String filename,
//                     @RequestParam("fileextension") String fileext,
//                     @RequestParam("columns") List<String> columns,
//                     @RequestParam("headers") int headers,
//                     @RequestParam("rowseparator") String rowSeparator,
//                     @RequestParam("fieldseparator") String fieldSeparator) {
//
//        Map<String, Object> userData = new HashMap<>();
//        userData.put("filed", multipartFile);
//        userData.put("filetype", filetyp);
//        userData.put("filename", filename);
//        userData.put("fileextension", fileext);
//        userData.put("columns", columns);
//        userData.put("headers", headers);
//        userData.put("rowseparator", rowSeparator);
//        userData.put("fieldseparator", fieldSeparator);
//
//        // Return the user data as JSON
//        ResponseEntity<Map<String, Object>> userDataResponse = ResponseEntity.ok(userData);
//        EntityResponse<List<Map<String, String>>> response = new EntityResponse<>();
////        try {
////            List<Map<String, String>> dataList = FileReader.extractD(
////                    multipartFile, filetyp, fileext, columns, headers, rowSeparator, fieldSeparator);
////
////            if (dataList != null && !dataList.isEmpty()) {
////                response.setMessage("Data fetched successfully.");
////                response.setEntity(dataList);
////                response.setStatusCode(HttpStatus.OK.value());
////                response.setReconStatus(true);
////                response.setReconDate(new Date());
////            } else {
////                response.setMessage("No data found in the uploaded file.");
////                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
////                response.setReconStatus(false);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            response.setMessage("An error occurred while processing the file: " + e.getMessage());
////            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            response.setReconStatus(false);
////        }
//
//        return ResponseEntity.status(response.getStatusCode()).body(userDataResponse);
//    }
//
//
//}
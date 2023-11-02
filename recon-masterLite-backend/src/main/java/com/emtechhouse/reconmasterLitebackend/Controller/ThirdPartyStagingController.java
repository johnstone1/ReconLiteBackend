//package com.emtechhouse.reconmasterLitebackend.Controller;
//import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
//import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
//import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
//import com.emtechhouse.reconmasterLitebackend.Services.ThirdPartyStagingService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.util.*;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//public class ThirdPartyStagingController {
//
//    @Value("${spring.application.files.hello-paissa}")
//    String filePath;
//    private  final ThirdPartyStagingService thirdPartyStagingService;
//    private static final Map<String, String> JSON_FIELD_MAPPING = new HashMap<>();
//    static {
//        JSON_FIELD_MAPPING.put("rrn", "rrn");
//        JSON_FIELD_MAPPING.put("amount", "amount");
//
//    }
////    @PostMapping("/upload-hp-files")
////    public ResponseEntity<EntityResponse<?>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
////        EntityResponse<?> response;
////
////        try {
////            response = fileHandler.fileTypeHandler(files);
////            if (response.getStatusCode() == HttpStatus.OK.value()) {
////                return ResponseEntity.ok(response);
////            } else {
////                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
////            }
////        } catch (IOException e) {
////            log.error("Failed to process uploaded files", e.getMessage());
////            response = EntityResponse.<String>builder()
////                    .message("Failed to process uploaded files. Please try again.")
////                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
////                    .build();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
////        }
////    }
////    @PostMapping("/Upload")
////    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {
////
////        try {
////            String jsonData = String.valueOf(fileReader.extractDataToJson((MultipartFile) file));
////
////            if (jsonData != null && !jsonData.isEmpty()) {
////                return ResponseEntity.ok()
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .body(jsonData);
////            } else {
////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                        .body("Failed to extract data from the uploaded file");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("An error occured");
////        }
////    }
//    @PostMapping("/Upload/test")
//    public String uploadDataToDatabase(@RequestBody String jsonDataFromFrontend) {
//        try {
//            JSONArray jsonArray = new JSONArray(jsonDataFromFrontend);
//            List<ThirdPartyStaging> dataToInsert = new ArrayList<>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ThirdPartyStaging data = new ThirdPartyStaging();
//
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
//            thirdPartyStagingService.saveData(dataToInsert);
//
//            return "Data saved successfully";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }
//
//    @PostMapping("/create/multiple")
//    public EntityResponse createMultiple(@RequestBody List<ThirdPartyStaging> finacleStagingList) {
//        try {
//            EntityResponse response = thirdPartyStagingService.createMultipleTxt(finacleStagingList);
//            return response;
//        }catch (Exception e) {
//            log.info("Error {} "+e);
//            return null;
//        }
//    }
//
//}

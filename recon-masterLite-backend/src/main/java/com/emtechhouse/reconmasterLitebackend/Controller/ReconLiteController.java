package com.emtechhouse.reconmasterLitebackend.Controller;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
import com.emtechhouse.reconmasterLitebackend.Services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/hello-paissa")
@Slf4j
@RequiredArgsConstructor
public class ReconLiteController {
    @Value("${spring.application.files.hello-paissa}")
    String filePath;

    private final FileHandler fileHandler;
    private final FileReader fileReader;
    private final FinacleStagingService finacleStagingService;
    private final ThirdPartyStagingService thirdPartyStagingService;
    private final ReconService reconService;


    private static final Map<String, String> JSON_FIELD_MAPPING = new HashMap<>();
    static {
        JSON_FIELD_MAPPING.put("rrn", "rrn");
        JSON_FIELD_MAPPING.put("amount", "amount");

    }


//    @PostMapping("/upload-hp-files")
//    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
//        log.info(" am here");
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
//                Files.write(path, bytes);
//            } catch (IOException e) {
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
//                fileHandler.reconHandler(ExcPath, FinPath);
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

    @PostMapping("/upload-hp-files")
    public ResponseEntity<EntityResponse<?>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        EntityResponse<?> response;

        try {
            response = fileHandler.fileTypeHandler(files);

            if (response.getStatusCode() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IOException e) {
            log.error("Failed to process uploaded files", e.getMessage());
            response = EntityResponse.<String>builder()
                    .message("Failed to process uploaded files. Please try again.")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/Upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {

        try {
            String jsonData = String.valueOf(fileReader.extractDataToJson((MultipartFile) file));

            if (jsonData != null && !jsonData.isEmpty()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonData);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to extract data from the uploaded file");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occured");
        }
    }


    @PostMapping("/Upload/test")
    public String uploadDataToDatabase(@RequestBody String jsonDataFromFrontend) {
        try {
            JSONArray jsonArray = new JSONArray(jsonDataFromFrontend);
            List<FinacleStaging> dataToInsert = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FinacleStaging data = new FinacleStaging();

                // Map JSON values to entity fields dynamically
                Iterator<String> jsonKeys = jsonObject.keys();
                while (jsonKeys.hasNext()) {
                    String jsonKey = jsonKeys.next();
                    String entityField = JSON_FIELD_MAPPING.get(jsonKey);

                    if (entityField != null) {
                        Field field = FinacleStaging.class.getDeclaredField(entityField);
                        field.setAccessible(true); // Make the field accessible

                        // Determine the field's data type and perform appropriate conversion
                        if (field.getType().equals(BigDecimal.class)) {
                            BigDecimal value = new BigDecimal(jsonObject.getString(jsonKey));
                            field.set(data, value);
                        } else if (field.getType().equals(String.class)) {
                            field.set(data, jsonObject.getString(jsonKey));
                        } // Add more data types as needed
                    }
                }

                dataToInsert.add(data);
            }

            finacleStagingService.saveData(dataToInsert); // Save data to the database

            return "Data saved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/create/multiple")
    public EntityResponse createMultiple(@RequestBody List<FinacleStaging> finacleStagingList) {
        try {
            EntityResponse response = finacleStagingService.createMultiple(finacleStagingList);
            return response;
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PostMapping("/create/multiple/txt")
    public EntityResponse createMultipleTxt(@RequestBody List<ThirdPartyStaging> thirdPartyStagingList) {
        try {
            EntityResponse response = thirdPartyStagingService.createMultipleTxt(thirdPartyStagingList);
            return response;
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/data")
    public List<String> compareData() {
        return reconService.compareData();
    }
    }

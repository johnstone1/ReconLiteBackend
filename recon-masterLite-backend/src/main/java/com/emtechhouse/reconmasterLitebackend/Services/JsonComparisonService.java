//package com.emtechhouse.reconmasterLitebackend.Services;
//import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
//import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class JsonComparisonService {
//
//    public Map<String, Object> readAndStoreFiles(MultipartFile fina, MultipartFile third) throws IOException {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // Print the content of the uploaded files for debugging
//            System.out.println("Content of fina file:");
//            System.out.println(new String(fina.getBytes(), StandardCharsets.UTF_8));
//
//            System.out.println("Content of third file:");
//            System.out.println(new String(third.getBytes(), StandardCharsets.UTF_8));
//
//            // Continue with JSON parsing
//            List<FinacleStaging> finacleData = objectMapper.readValue(fina.getInputStream(), new TypeReference<List<FinacleStaging>>() {});
//            List<ThirdPartyStaging> thirdPartyData = objectMapper.readValue(third.getInputStream(), new TypeReference<List<ThirdPartyStaging>>() {});
//
//            // ... Rest of your code
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e; // Rethrow the exception to handle it appropriately
//        }
//        return null;
//    }
//
//    public boolean compareJsonData(Map<String, Object> dataMap, List<String> columnsToCompare) {
//        JSONObject finacleJson = (JSONObject) dataMap.get("finacle");
//        JSONObject thirdPartyJson = (JSONObject) dataMap.get("thirdParty");
//
//        // Add "Employee" to the columnsToCompare list
//        columnsToCompare.add("Employee");
//
//        // Implement your column comparison logic here
//        return compareColumns(finacleJson, thirdPartyJson, columnsToCompare);
//    }
//
//    private boolean compareColumns(JSONObject finacleJson, JSONObject thirdPartyJson, List<String> columnsToCompare) {
//        for (String column : columnsToCompare) {
//            if (!compareColumnValues(finacleJson, thirdPartyJson, column)) {
//                return false;
//            }
//        }
//        return true;
//    }
//    private boolean compareColumnValues(JSONObject finacleJson, JSONObject thirdPartyJson, String column) {
//        if (finacleJson.has(column) && thirdPartyJson.has(column)) {
//            Object finacleValue = finacleJson.get(column);
//            Object thirdPartyValue = thirdPartyJson.get(column);
//
//            // Check if the types are compatible for comparison
//            if (finacleValue.getClass().equals(thirdPartyValue.getClass())) {
//                if (finacleValue instanceof String) {
//                    // String comparison
//                    return finacleValue.equals(thirdPartyValue);
//                } else if (finacleValue instanceof Number) {
//                    // Numeric comparison
//                    double finacleNumeric = ((Number) finacleValue).doubleValue();
//                    double thirdPartyNumeric = ((Number) thirdPartyValue).doubleValue();
//
//                    return finacleNumeric == thirdPartyNumeric;
//                } else {
//                    // Handle other data types or special cases here
//                    // For unsupported types, consider them as mismatched
//                    return false;
//                }
//            } else {
//                // Types are not compatible for comparison, consider them as mismatched
//                return false;
//            }
//        } else {
//
//            return false;
//        }
//    }
//
//}
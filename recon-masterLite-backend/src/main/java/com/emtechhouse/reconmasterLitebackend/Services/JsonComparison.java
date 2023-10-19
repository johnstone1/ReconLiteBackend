//package com.emtechhouse.reconmasterLitebackend.Services;
//
//import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
//import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
//import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class JsonComparison {
//    @Service
//    public class JsonComparisonService {
//
//        private FinacleStagingRepository finacleStagingRepository;
//
//        public Map<String, Object> readAndStoreFiles(MultipartFile fina, MultipartFile third) throws IOException {
//            // Read data from MultipartFile objects and store it in suitable data structures.
//            // You can use libraries like Jackson to parse JSON data from the files.
//
//            // Example:
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<FinacleStaging> finacleData = objectMapper.readValue(fina.getInputStream(), new TypeReference<List<FinacleStaging>>() {});
//            List<ThirdPartyStaging> thirdPartyData = objectMapper.readValue(third.getInputStream(), new TypeReference<List<ThirdPartyStaging>>() {});
//
//            // Store data in a map
//            Map<String, Object> dataMap = new HashMap<>();
//            dataMap.put("finaData", finacleData);
//            dataMap.put("thirdPartyData", thirdPartyData);
//
//            return dataMap;
//        }
//
//        public boolean compareJsonData(Map<String, Object> dataMap, List<String> columnsToCompare) {
//            // Extract data from the map
//            List<FinacleStaging> finacleData = (List<FinacleStaging>) dataMap.get("finaData");
//            List<ThirdPartyStaging> thirdPartyData = (List<ThirdPartyStaging>) dataMap.get("thirdPartyData");
//
//            // Compare data based on the specified columns
//            // Implement your comparison logic here, comparing the two lists using the specified columns.
//
//            // Example:
//            for (FinacleStaging finacleRow : finacleData) {
//                for (ThirdPartyStaging thirdPartyRow : thirdPartyData) {
//                    // Compare rows based on the specified columns
//                    boolean rowsMatch = compareRows(finacleRow, thirdPartyRow, columnsToCompare);
//                    if (!rowsMatch) {
//                        System.out.println("does not match");
//                        return false; // Data does not match
//
//                    }
//                }
//            }
//            System.out.println("match");
//            return true; // Data matches
//        }
//
//        private boolean compareRows(FinacleStaging finacleRow, ThirdPartyStaging thirdPartyRow, List<String> columnsToCompare) {
//            // Implement row-level comparison logic here based on the specified columns.
//            // Compare the values in the specified columns for both rows.
//
//            // Example:
//            for (String column : columnsToCompare) {
//                String finacleValue = finacleRow.getRrn();
//                String thirdPartyValue = thirdPartyRow.getRrn();
//
//                if (!finacleValue.equals(thirdPartyValue)) {
//                    System.out.println(" do not match");
//                    return false; //
//                }
//            }
//            System.out.println("match");
//            return true; //
//        }
//    }
//}
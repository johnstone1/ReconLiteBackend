//package com.emtechhouse.reconmasterLitebackend.Services;
//import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
//import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
//import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
//import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
//import com.emtechhouse.reconmasterLitebackend.Repositories.ThirdPartyStagingRepository;
//import com.fasterxml.jackson.databind.JsonNode;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class ReconService {
//    private final ThirdPartyStagingRepository thirdPartyStagingRepository;
//    private final FinacleStagingRepository finacleStagingRepository;
//
//    public List<String> compareData() {
//        EntityResponse response=new EntityResponse();
//
//        List<FinacleStaging> finacleStagingList = finacleStagingRepository.findAll();
//        List<ThirdPartyStaging> thirdPartyStagingList = thirdPartyStagingRepository.findAll();
//
//        List<String> results = new ArrayList<>();
//
//        for (FinacleStaging finacleStaging : finacleStagingList) {
//            for (ThirdPartyStaging thirdPartyStaging : thirdPartyStagingList) {
//                if (finacleStaging.equals(thirdPartyStaging)) {
//                    results.add("Matching: " + finacleStaging.toString());
//                } else {
//                    results.add("Does not match: " + finacleStaging.toString() + " | " + thirdPartyStaging.toString());
//                }
//            }
//        }
//        return results;
//    }
//    public List<String> comparisons(JsonNode jsonInput) {
//        if (!jsonInput.isArray()) {
//            throw new IllegalArgumentException("Input should be a JSON array.");
//        }
//
//        List<String> differences = new ArrayList<>();
//
//        for (JsonNode entry : jsonInput) {
//            if (!entry.has("rrn")) {
//                throw new IllegalArgumentException("Each entry in the array should have an 'rrn' property.");
//            }
//
//            String rrnValue = entry.get("rrn").asText();
//
//            // Fetch data from both models based on the 'rrn' column
//            List<FinacleStaging> finacleData = finacleStagingRepository.findByRrn(rrnValue);
//            List<ThirdPartyStaging> thirdPartyData = thirdPartyStagingRepository.findByRrn(rrnValue);
//
//            boolean foundMatch = false;
//
//            for (FinacleStaging finacle : finacleData) {
//                for (ThirdPartyStaging thirdParty : thirdPartyData) {
//                    if (finacle.getRrn().equals(thirdParty.getRrn())) {
//                        foundMatch = true;
//                        break;
//                    }
//                }
//                if (foundMatch) {
//                    break;
//                }
//            }
//
//            if (!foundMatch) {
//                differences.add("Mismatch: No matching 'rrn' found for " + rrnValue);
//            }
//        }
//
//        return differences;
//    }
//}
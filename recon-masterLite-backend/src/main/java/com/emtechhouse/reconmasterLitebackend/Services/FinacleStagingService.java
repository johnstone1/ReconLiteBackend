////package com.emtechhouse.reconmasterLitebackend.Services;
////import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
////import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
////import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
////import lombok.RequiredArgsConstructor;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.http.HttpStatus;
////import org.springframework.stereotype.Service;
////import org.json.JSONArray;
////import org.json.JSONObject;
////import java.math.BigDecimal;
////import java.util.List;
////import java.util.Optional;
////import java.util.stream.Collectors;
////
////@Service
////@Slf4j
////@RequiredArgsConstructor
////
////public class FinacleStagingService {
////    private final FinacleStagingRepository finacleStagingRepository;
////
////    public EntityResponse saveData(List<FinacleStaging> finacleStagingList) {
////
////        EntityResponse response = new EntityResponse();
////
////        try {
////            finacleStagingList = finacleStagingList.stream().map(r -> {
////
////                Optional<FinacleStaging> rrn = finacleStagingRepository.findByRrn(r.getRrn());
////                r.setRrn(jsonObject.getString("rrn"));
////                r.setAmount(new BigDecimal(jsonObject.getString("amount")));
////                System.out.println("nice am here hurray");
////                return r;
////            }).collect(Collectors.toList());
////            finacleStagingRepository.saveAll(finacleStagingList);
////            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
////            response.setStatusCode(HttpStatus.CREATED.value());
////            response.setEntity(finacleStagingList);
////
////        } catch (Exception e) {
////            log.error(e.getMessage()+e);
////        }
////        return respon
//package com.emtechhouse.reconmasterLitebackend.Services;
//import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
//import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
//import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class FinacleStagingService {
//    private final FinacleStagingRepository finacleStagingRepository;
//
//    public EntityResponse saveData(List<FinacleStaging> jsonObjects) {
//        EntityResponse response = new EntityResponse();
//        List<FinacleStaging> finacleStagingList = new ArrayList<>();
//
//        try {
//            for (int i = 0; i < jsonObjects.size(); i++) {
//                FinacleStaging jsonObject = jsonObjects.get(i);
//                FinacleStaging r = new FinacleStaging();
//                List<FinacleStaging> existingData = finacleStagingRepository.findByRrn(r.getRrn());
//
//                if (!existingData.isEmpty()) {
//                    FinacleStaging existingRecord = new FinacleStaging();
//                    existingRecord.setRrn(jsonObject.getRrn());
//                    // Check if the amount is a valid number
//                    try {
//                        existingRecord.setAmount(new BigDecimal(String.valueOf(jsonObject.getAmount())));
//                        finacleStagingList.add(existingRecord);
//                    } catch (NumberFormatException ex) {
//                        log.error("Invalid amount value for record with RRN: " + jsonObject.getRrn(), ex);
//                        // Handle the invalid value as needed (e.g., skip the record or set a default value)
//                    }
//                } else {
//                    // If no existing record found, create a new one
//                    r.setRrn(jsonObject.getRrn());
//
//                    // Check if the amount is a valid number
//                    try {
//                        r.setAmount(new BigDecimal(String.valueOf(jsonObject.getAmount())));
//                        finacleStagingList.add(r);
//                    } catch (NumberFormatException ex) {
//                        log.error("Invalid amount value for new record with RRN: " + jsonObject.getRrn(), ex);
//                        // Handle the invalid value as needed (e.g., skip the record or set a default value)
//                    }
//                }
//            }
//            finacleStagingRepository.saveAll(finacleStagingList);
//            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
//            response.setStatusCode(HttpStatus.CREATED.value());
//            response.setEntity(finacleStagingList);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return response;
//    }
//    public EntityResponse createMultiple(List<FinacleStaging> finacleStagingList) {
//
//        EntityResponse response = new EntityResponse();
//
//        try {
//            finacleStagingList = finacleStagingList.stream().map(r -> {
//                List<FinacleStaging> rrn = finacleStagingRepository.findByRrn(r.getRrn());
//                if (!rrn.isEmpty()) {
//                    r.setRrn(r.getRrn());
//                    r.setTransactionRemarks(r.getTransactionRemarks());
//                    r.setAccountNumber(r.getAccountNumber());
//                    r.setAmount(r.getAmount());
//                    r.setCurrencyCode(r.getCurrencyCode());
//                    r.setDebit(r.getDebit());
//                    r.setExceptionReason(r.getExceptionReason());
//                    r.setForAcid(r.getForAcid());
//                    r.setPhoneNumber(r.getPhoneNumber());
//                    r.setRunningBalance(r.getRunningBalance());
//                    r.setSecRef(r.getSecRef());
//                    r.setStatus(r.getStatus());
//                    r.setTransParticular(r.getTransParticular());
//                    r.setTransactionDate(r.getTransactionDate());
//                    r.setTransactionDescription(r.getTransactionDescription());
//                    r.setTransactionId(r.getTransactionId());
//                    r.setValueDate(r.getValueDate());
////                r.setUpdatedSalary('Y');
////                r.setPostedTime(new Date());
////                r.setApprovedBy(UserRequestContext.getCurrentUser());
//
//                } else {
//
//                    r.setRrn(r.getRrn());
//                    r.setTransactionRemarks(r.getTransactionRemarks());
//                    r.setAccountNumber(r.getAccountNumber());
//                    r.setAmount(r.getAmount());
//                    r.setCurrencyCode(r.getCurrencyCode());
//                    r.setDebit(r.getDebit());
//                    r.setExceptionReason(r.getExceptionReason());
//                    r.setForAcid(r.getForAcid());
//                    r.setPhoneNumber(r.getPhoneNumber());
//                    r.setRunningBalance(r.getRunningBalance());
//                    r.setSecRef(r.getSecRef());
//                    r.setStatus(r.getStatus());
//                    r.setTransParticular(r.getTransParticular());
//                    r.setTransactionDate(r.getTransactionDate());
//                    r.setTransactionDescription(r.getTransactionDescription());
//                    r.setTransactionId(r.getTransactionId());
//                    r.setValueDate(r.getValueDate());
//                }
//                return r;
//            }).collect(Collectors.toList());
//            finacleStagingRepository.saveAll(finacleStagingList);
//            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
//            response.setStatusCode(HttpStatus.CREATED.value());
//            response.setEntity(finacleStagingList);
//
//        } catch (Exception e) {
//            log.info(e.toString());
//        }
//
//        return response;
//    }
//}
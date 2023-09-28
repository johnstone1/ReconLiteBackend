package com.emtechhouse.reconmasterLitebackend.Services;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
import com.emtechhouse.reconmasterLitebackend.Repositories.ThirdPartyStagingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThirdPartyStagingService {
    private final ThirdPartyStagingRepository thirdPartyStagingRepository;

    public EntityResponse saveData(List<ThirdPartyStaging> jsonObjects) {
        EntityResponse response = new EntityResponse();
        List<ThirdPartyStaging> thirdPartyStagingList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonObjects.size(); i++) {
                ThirdPartyStaging jsonObject = jsonObjects.get(i);
                ThirdPartyStaging r = new ThirdPartyStaging();
                Optional<ThirdPartyStaging> existingData = thirdPartyStagingRepository.findByRrn(r.getRrn());

                if (existingData.isPresent()) {
                    ThirdPartyStaging existingRecord = existingData.get();
                    existingRecord.setRrn(jsonObject.getRrn());
                    // Check if the amount is a valid number
                    try {
                        existingRecord.setAmount(new BigDecimal(String.valueOf(jsonObject.getAmount())));
                        thirdPartyStagingList.add(existingRecord);
                    } catch (NumberFormatException ex) {
                        log.error("Invalid amount value for record with RRN: " + jsonObject.getRrn(), ex);
                        // Handle the invalid value as needed (e.g., skip the record or set a default value)
                    }
                } else {
                    // If no existing record found, create a new one
                    r.setRrn(jsonObject.getRrn());

                    // Check if the amount is a valid number
                    try {
                        r.setAmount(new BigDecimal(String.valueOf(jsonObject.getAmount())));
                        thirdPartyStagingList.add(r);
                    } catch (NumberFormatException ex) {
                        log.error("Invalid amount value for new record with RRN: " + jsonObject.getRrn(), ex);
                        // Handle the invalid value as needed (e.g., skip the record or set a default value)
                    }
                }
            }
            thirdPartyStagingRepository.saveAll(thirdPartyStagingList);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(thirdPartyStagingList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }
    public EntityResponse createMultipleTxt(List<ThirdPartyStaging> thirdPartyStagingList) {

        EntityResponse response = new EntityResponse();

        try {
            thirdPartyStagingList = thirdPartyStagingList.stream().map(r -> {
                Optional<ThirdPartyStaging> rrn = thirdPartyStagingRepository.findByRrn(r.getRrn());
                if (rrn.isPresent()) {
                    String rrn1 = String.valueOf(rrn.get());
                    r.setRrn(r.getRrn());
                    r.setTransactionRemarks(r.getTransactionRemarks());
                    r.setAccountNumber(r.getAccountNumber());
                    r.setAmount(r.getAmount());
                    r.setCurrencyCode(r.getCurrencyCode());
                    r.setDebit(r.getDebit());
                    r.setExceptionReason(r.getExceptionReason());
                    r.setForAcid(r.getForAcid());
                    r.setPhoneNumber(r.getPhoneNumber());
                    r.setRunningBalance(r.getRunningBalance());
                    r.setSecRef(r.getSecRef());
                    r.setStatus(r.getStatus());
                    r.setTransParticular(r.getTransParticular());
                    r.setTransactionDate(r.getTransactionDate());
                    r.setTransactionDescription(r.getTransactionDescription());
                    r.setTransactionId(r.getTransactionId());
                    r.setValueDate(r.getValueDate());
//                r.setUpdatedSalary('Y');
//                r.setPostedTime(new Date());
//                r.setApprovedBy(UserRequestContext.getCurrentUser());

                } else {

                    r.setRrn(r.getRrn());
                    r.setTransactionRemarks(r.getTransactionRemarks());
                    r.setAccountNumber(r.getAccountNumber());
                    r.setAmount(r.getAmount());
                    r.setCurrencyCode(r.getCurrencyCode());
                    r.setDebit(r.getDebit());
                    r.setExceptionReason(r.getExceptionReason());
                    r.setForAcid(r.getForAcid());
                    r.setPhoneNumber(r.getPhoneNumber());
                    r.setRunningBalance(r.getRunningBalance());
                    r.setSecRef(r.getSecRef());
                    r.setStatus(r.getStatus());
                    r.setTransParticular(r.getTransParticular());
                    r.setTransactionDate(r.getTransactionDate());
                    r.setTransactionDescription(r.getTransactionDescription());
                    r.setTransactionId(r.getTransactionId());
                    r.setValueDate(r.getValueDate());
                }
                return r;
            }).collect(Collectors.toList());
            thirdPartyStagingRepository.saveAll(thirdPartyStagingList);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(thirdPartyStagingList);

        } catch (Exception e) {
            log.info(e.toString());
        }

        return response;
    }
}

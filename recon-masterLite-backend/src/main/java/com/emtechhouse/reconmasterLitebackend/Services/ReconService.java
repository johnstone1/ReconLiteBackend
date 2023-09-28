package com.emtechhouse.reconmasterLitebackend.Services;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
import com.emtechhouse.reconmasterLitebackend.Repositories.ThirdPartyStagingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReconService {
    private final ThirdPartyStagingRepository thirdPartyStagingRepository;
    private final FinacleStagingRepository finacleStagingRepository;

    public List<String> compareData() {
        EntityResponse response=new EntityResponse();
        try{
            //Optional<FinacleStaging>finacleStaging=finacleStagingRepository.findByRrn(String rrn);

        }catch(Exception e){
   log.info(e.getMessage());
        }
        List<FinacleStaging> finacleStagingList = finacleStagingRepository.findAll();
        List<ThirdPartyStaging> thirdPartyStagingList = thirdPartyStagingRepository.findAll();

        List<String> results = new ArrayList<>();

        for (FinacleStaging finacleStaging : finacleStagingList) {
            for (ThirdPartyStaging thirdPartyStaging : thirdPartyStagingList) {
                if (finacleStaging.equals(thirdPartyStaging)) {
                    results.add("Matching: " + finacleStaging.toString());
                } else {
                    results.add("Does not match: " + finacleStaging.toString() + " | " + thirdPartyStaging.toString());
                }
            }
        }

        return results;
    }
}
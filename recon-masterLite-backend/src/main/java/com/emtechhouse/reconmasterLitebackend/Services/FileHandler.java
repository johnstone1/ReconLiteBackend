package com.emtechhouse.reconmasterLitebackend.Services;

import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
import com.emtechhouse.reconmasterLitebackend.Repositories.ThirdPartyStagingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class FileHandler {

    private final FileReader fileReader;
    private final FinacleStagingRepository finacleStagingRepository;
    private final ThirdPartyStagingRepository thirdPartyStagingRepository;



//     reconcile excel and finacle
public void reconHandler(Path ExcPath, Path FinPath) {
    try {
        File ExcFile = new File(ExcPath.toString());
        File FinFile = new File(FinPath.toString());

        if (ExcFile.exists() && FinFile.exists()) {
            Map<String, ThirdPartyStaging> excDataMap = this.fileReader.readPaissaData(ExcPath.toString());
            Map<String, FinacleStaging> FinDataMap = this.fileReader.readFinFile(FinPath.toString());

            log.info("Starting reconciliation process...");



            // Save the updated data
//            finacleStagingRepository.saveAll(excDataMap);
//            thirdPartyStagingRepository.saveAll(FinDataMap.values());

            log.info("Reconciliation process completed.");
        } else {
            log.error("Files do not exist: ExcFile={} FinFile={}", ExcFile.exists(), FinFile.exists());
        }
    } catch (Exception e) {
        log.error("An error occurred during reconciliation: {}", e.getMessage(), e);
    }
}

}

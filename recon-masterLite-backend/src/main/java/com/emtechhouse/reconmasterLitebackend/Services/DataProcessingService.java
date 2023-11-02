//package com.emtechhouse.reconmasterLitebackend.Services;
//import com.emtechhouse.reconmasterLitebackend.Models.*;
//import com.emtechhouse.reconmasterLitebackend.Repositories.FinacleStagingRepository;
//import com.emtechhouse.reconmasterLitebackend.Repositories.PriorityRepository;
//import com.emtechhouse.reconmasterLitebackend.Repositories.ThirdPartyStagingRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class DataProcessingService {
//    private final FinacleStagingRepository finacleStagingRepository;
//    private final ThirdPartyStagingRepository thirdPartyStagingRepository;
//    private final PriorityRepository priorityRepository;
//    public void processJsonData(JsonData1 jsonData) {
//        PriorityTable priorityTable = new PriorityTable();
//        // Set priority and other attributes for the PriorityTable
//
//        for (Columns column : jsonData.getColumns()) {
//            if ("Finacle File".equals(column.getColumnTypeIdentifier())) {
//                FinacleStaging finacleStaging = new FinacleStaging();
//                // Set attributes for FinacleStaging
//                finacleStaging.setPriorityTable(priorityTable); // Associate with the PriorityTable
//                finacleStagingRepository.save(finacleStaging);
//            } else if ("Third Party File".equals(column.getColumnTypeIdentifier())) {
//                ThirdPartyStaging thirdPartyStaging = new ThirdPartyStaging();
//                // Set attributes for ThirdPartyStaging
//                thirdPartyStaging.setPriorityTable(priorityTable); // Associate with the PriorityTable
//                thirdPartyStagingRepository.save(thirdPartyStaging);
//            }
//        }
//
//        priorityRepository.save(priorityTable);
//    }
//}

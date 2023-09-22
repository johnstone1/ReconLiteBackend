package com.emtechhouse.reconmasterLitebackend.Services;


import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileReader {
    public Map<String, ThirdPartyStaging> readPaissaData(String filePath) throws IOException {
        Map<String, ThirdPartyStaging> thirdPartyStagingHashMap = new HashMap<>();

//    String filePath = "/home/konj/Downloads/HP  Daily Report.xlsx";

//    File file = new File(dir + "HP Daily Report"+dateString+".xlsx:")

        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);

        Sheet sheet = workbook.getSheetAt(8);

        for (Row curRow : sheet) {
            // Skip rows 1 to 4
            if (curRow.getRowNum() < 4) {
                continue;
            }

            // Check if cells at index 0, 1, and 2 are empty
            Cell cell0 = curRow.getCell(0);
            Cell cell1 = curRow.getCell(1);
            Cell cell2 = curRow.getCell(2);
            if (cell0 == null || cell0.getCellType() == CellType.BLANK ||
                    cell1 == null || cell1.getCellType() == CellType.BLANK ||
                    cell2 == null || cell2.getCellType() == CellType.BLANK) {
                // Skip the row if any of the cells at index 0, 1, or 2 are empty
                continue;
            }

            ThirdPartyStaging thirdPartyStaging = new ThirdPartyStaging();

            // Set the data
            Cell rrnCell = curRow.getCell(6);
            if (rrnCell != null) {
                String rrnValue;
                if (rrnCell.getCellType() == CellType.NUMERIC) {
                    rrnValue = String.valueOf((long) rrnCell.getNumericCellValue());
                } else {
                    rrnValue = rrnCell.getStringCellValue().trim();
                }
                thirdPartyStaging.setRrn(rrnValue);
            } else {
                // Handles the case when the cell is null
                thirdPartyStaging.setRrn("");
            }
            thirdPartyStaging.setAccountNumber(String.valueOf(curRow.getCell(7)));
            thirdPartyStaging.setTransParticular(String.valueOf(curRow.getCell(8)));
//        paissaData.setAmount(String.valueOf(curRow.getCell(9)));

            Cell amountCell = curRow.getCell(9);
// Initialize the amount BigDecimal to zero
            BigDecimal amount = BigDecimal.ZERO;

// Check if the cell is not null and not blank
            if (amountCell != null && amountCell.getCellType() != CellType.BLANK) {
                // Extract the raw cell value as a string
                String rawAmount = amountCell.toString();

                // Remove any non-numeric characters (except for the decimal point)
                String numericString = rawAmount.replaceAll("[^\\d.]", "");

                // Now, parse the numeric string to a BigDecimal
                try {
                    amount = new BigDecimal(numericString);
                } catch (NumberFormatException e) {
                    // Handle parsing error here if necessary
                    // For example, log the error or set a default value
                }
            }

            thirdPartyStaging.setAmount(amount);

            thirdPartyStagingHashMap.put(thirdPartyStaging.getRrn(), thirdPartyStaging);

            // Console log the data
//        log.info("==============================");
//        log.info("Rrn: {}", paissaData.getRrn());
//        log.info("Sent Date: {}", paissaData.getSendDate());
//        log.info("Paid Date: {}", paissaData.getPaidDate());
//        log.info("Amount: {}", paissaData.getAmount());
//        log.info("==============================");
        }

//    log.info("Total rows processed: {}", paissaDataMap.size());
        return thirdPartyStagingHashMap;
    }


    //    converting to text
    public Map<String, FinacleStaging> readFinFile(String paissaFin) throws IOException {

//        String paissaFin = "/home/konj/Downloads/HP FINACLE 13-15.txt";

        Map<String, FinacleStaging> readtxtData = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(paissaFin));
            lines = lines.subList(1, lines.size());

            for (String line : lines) {
                String[] divLine = line.trim().split("\t");
                if (divLine.length >= 8) {
                    FinacleStaging finacleStaging = new FinacleStaging();
                    finacleStaging.setForAcid(divLine[3].trim());
                    finacleStaging.setAccountNumber(divLine[4].trim());
                    finacleStaging.setRrn(divLine[5].trim());
                    finacleStaging.setForAcid(divLine[7].trim());
                    finacleStaging.setTransactionId(divLine[8].trim());
                    finacleStaging.setTransactionDescription(divLine[9].trim());
                    finacleStaging.setSecRef(divLine[10].trim());

                    // Assuming divLine[8] or divLine[9] contains the amount as a String
                    String amountString;

                    if (divLine[8] != null && !divLine[8].isBlank() && !divLine[8].isEmpty()) {
                        amountString = divLine[8].replaceAll("[^\\d.]", "");
                    } else {
                        amountString = divLine[9].replaceAll("[^\\d.]", "");
                    }

// Convert the amountString to a BigDecimal
                    BigDecimal amount;
                    try {
                        amount = new BigDecimal(amountString);
                    } catch (NumberFormatException e) {
                        amount = BigDecimal.ZERO;                     }

// Set the BigDecimal value to the FinacleStaging object
                    finacleStaging.setAmount(amount);


//                    log.info("==============================");
//
//
//                    log.info("Sol Id: {}", paissaData.getSolId());
//                    log.info("Acc Number: {}", paissaData.getAccNumber());
//                    log.info("Rnn: {}", paissaData.getRrn());
//                    log.info("Trans Date: {}", paissaData.getTranDate());
//                    log.info("Trans Id: {}", paissaData.getTranId());
//                    log.info("Debit: {}", paissaData.getDebit());
//                    log.info("Credit: {}", paissaData.getCredit());
//                    log.info("Gl SubHead Code: {}", paissaData.getGlSubHeadCode());
//                    log.info("==============================");

                    readtxtData.put(finacleStaging.getRrn(), finacleStaging);
                }
            }
        } catch (IOException e) {
            log.error("Could not read the file");
        }

//        log.info("Total rows processed: {}", readPaissaData.size());
        return readtxtData;
    }

}

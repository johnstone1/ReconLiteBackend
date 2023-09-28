package com.emtechhouse.reconmasterLitebackend.Services;

import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;

import com.emtechhouse.reconmasterLitebackend.UtilsAndConstants.FileTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class FileHandler {

    private final FileReader fileReader;
    public EntityResponse<?> fileTypeHandler(List<MultipartFile> files) throws IOException {
        EntityResponse<?> response = new EntityResponse<>();

        for (MultipartFile file : files) {
            // Get the file name and determine its type based on the extension
            String fileName = file.getOriginalFilename();
            FileTypes fileType = getFileType(fileName);

            if (fileType == FileTypes.EXCEL) {
                // Process Excel file
                log.info("Reading Excel file...");

                try {
                    EntityResponse entityResponse =new EntityResponse();
                    boolean success = Boolean.parseBoolean(FileReader.extractDataToJson(file));
                    if (success) {
                        entityResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());
                        log.info("updated successfully...");
                    } else {
                        log.warn("internal server error...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();log.warn("internal server error...");
                }
            } else if (fileType == FileTypes.TEXT) {
                // Process Text file
                log.info("Reading Text file...");
            } else if (fileType == FileTypes.XML){
                log.info("Reading Xml file...");
            } else if (fileType == FileTypes.PDF){
                log.info("Reading PDF file...");
            } else {
                // Handle other file types or report an error for each file
                response.setMessage("Unsupported file type: " + fileName);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return response;
            }
        }

        // If processing is successful for all files, set response data and status
        response.setMessage("All files processed successfully.");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }


    private FileTypes getFileType(String fileName) {
        String fileExtension = getFileExtension(fileName.toLowerCase());

        switch (fileExtension) {
            case "xlsx":
            case "xls":
                return FileTypes.EXCEL;
            case "txt":
                return FileTypes.TEXT;
            case "csv":
                return FileTypes.CSV;
            case "xml":
                return FileTypes.XML;
            case "pdf":
                return FileTypes.PDF;
            default:
                return FileTypes.Other;
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}


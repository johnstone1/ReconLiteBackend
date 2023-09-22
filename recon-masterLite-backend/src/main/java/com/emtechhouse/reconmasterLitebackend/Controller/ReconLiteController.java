package com.emtechhouse.reconmasterLitebackend.Controller;

import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Services.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/hello-paissa")
@Slf4j
@RequiredArgsConstructor
public class ReconLiteController {
    @Value("${spring.application.files.hello-paissa}")
    String filePath;

    private final FileHandler fileHandler;

    @PostMapping("/upload-hp-files")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        log.info(" am here");
        EntityResponse response = new EntityResponse();
        if (files.isEmpty() || files.size() < 2) {
            response.setMessage("Please select two files to upload.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Path ExcPath = null;
        Path FinPath = null;

        for (MultipartFile file : files) {
            byte[] bytes;
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                log.error("Failed to read the uploaded file: {}", file.getOriginalFilename());
                response.setMessage("Failed to read the uploaded file: " + file.getOriginalFilename());
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Path path = Paths.get(filePath + file.getOriginalFilename());
            try {
                Files.write(path, bytes);
            } catch (IOException e) {
                log.error("Failed to save the uploaded file: {}", file.getOriginalFilename());
                response.setMessage("Failed to save the uploaded file: " + file.getOriginalFilename());
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Check file patterns
            String fileName = file.getOriginalFilename();
            if (fileName.contains("HP Daily Report")) {
                ExcPath = Paths.get(filePath + file.getOriginalFilename());
            } else if (fileName.contains("HP FINACLE")) {
               FinPath = Paths.get(filePath + file.getOriginalFilename());
            } else {
                response.setMessage("Invalid file pattern. Please upload files with names containing 'HP Daily Report' and 'HP FINACLE'.");
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        if (ExcPath != null && FinPath != null) {
            try {
                fileHandler.reconHandler(ExcPath, FinPath);

                response.setMessage("Files uploaded and reconciliation is successful.");
                response.setStatusCode(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Failed to reconcile the files: {}", e.getMessage());
                response.setMessage("Failed to reconcile the files. Please try again.");
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setMessage("One or both of the files does not exist.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}

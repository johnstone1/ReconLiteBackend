package com.emtechhouse.reconmasterLitebackend.Controller;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.ConfigurationTable;
import com.emtechhouse.reconmasterLitebackend.Models.PriorityTable;
import com.emtechhouse.reconmasterLitebackend.Services.ConfigServic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("api/v1/config")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ConfigController {
    private final ConfigServic configServic;
    private final RestTemplate restTemplate;

    @PostMapping("/save")
    public EntityResponse create(@RequestBody ConfigurationTable configurationTable) {
        try {
            EntityResponse response = configServic.create1(Collections.singletonList(configurationTable));
            return response;
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @GetMapping("/all")
    public EntityResponse findAll() {
        return configServic.findAll();
    }
    @GetMapping("/findBy/{id}")
    public EntityResponse findById(@PathVariable Long id) {
        return configServic.findById(id);
    }
    @PutMapping("/update/{id}")
    public EntityResponse update(@PathVariable Long id ,@RequestBody ConfigurationTable configurationTable){
        return configServic.update(id,configurationTable);
    }
    @DeleteMapping("/delete/{id}")
    public EntityResponse  delete(@PathVariable Long id){
        return configServic.delete(id);
    }
    @PostMapping("/save/priority")
    public EntityResponse savePriority(@RequestBody PriorityTable priorityTable) {
        try {
            EntityResponse response = configServic.saveP(Collections.singletonList(priorityTable));
            return response;
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @GetMapping("/all/priority")
    public EntityResponse findAllPriority() {
        return configServic.findAllPriority();
    }

    @DeleteMapping("/delete/priority/{id}")
    public EntityResponse  deletePriority(@PathVariable Long id){
        return configServic.deletePriority1(id);
    }
    @PutMapping("/update/priority/{id}")
    public EntityResponse update(@PathVariable Long id ,@RequestBody PriorityTable priorityTable){
        return configServic.updatePriority(id,priorityTable);
    }

    @GetMapping("/findBy/priority/{id}")
    public EntityResponse findPriorityById(@PathVariable Long id) {
        return configServic.findPriorityById(id);
    }


    @GetMapping("/downloadFromServer")
    public ResponseEntity<String> downloadFile(
            @RequestParam String remoteFileUrl,
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);

            HttpEntity<?> httpEntity = new HttpEntity<>(headers);

            byte[] fileBytes = restTemplate.exchange(remoteFileUrl, HttpMethod.GET, httpEntity, byte[].class).getBody();

            String localFilePath = "local/path/to/save/file.ext";
            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                fos.write(fileBytes);
            }

            return ResponseEntity.ok("File downloaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to download the file.");
        }
    }


//    @GetMapping("/downloadFromServer1")
//    public String downloadFile(@RequestParam String hostname, @RequestParam String username, @RequestParam String privateKeyPath, @RequestParam String itemToDownload) {
//        try {
//            sshService.downloadFile(hostname, username, privateKeyPath, itemToDownload);
//            return "File downloaded successfully!";
//        } catch (Exception e) {
//            return "Error: " + e.getMessage();
//        }
//    }

}
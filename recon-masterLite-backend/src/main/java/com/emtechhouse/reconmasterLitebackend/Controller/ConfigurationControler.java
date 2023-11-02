package com.emtechhouse.reconmasterLitebackend.Controller;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.ConfigurationTable;
import com.emtechhouse.reconmasterLitebackend.Repositories.ConfigurationTableRepository;
import com.emtechhouse.reconmasterLitebackend.Services.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/recon/try")
public class ConfigurationControler {
    private final ConfigurationService configurationService;
    private final ConfigurationTableRepository configurationTableRepository;

    @PostMapping("/save-configuration")
    public ResponseEntity<EntityResponse<ConfigurationTable>> saveConfiguration(@RequestBody List<ConfigurationTable> configurationTable) {
        EntityResponse<ConfigurationTable> response = new EntityResponse<>();

        try {
            ConfigurationTable savedConfiguration = configurationService.create1(configurationTable).getEntity();
            response.setMessage("Configuration saved successfully");
            response.setEntity(savedConfiguration);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Failed to save configuration");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get/configurations")
    public EntityResponse getAll() {

        return configurationService.findAll();
    }

//    @PostMapping("/save-configuration/create")
//    public ResponseEntity<EntityResponse<ConfigurationTable>> saveConfiguration(@RequestBody ConfigurationTable configuration) {
//        EntityResponse<ConfigurationTable> response = new EntityResponse<>();
//
//        try {
//            ConfigurationTable savedConfiguration = configurationService.create1(configuration);
//            response.setMessage("Configuration saved successfully");
//            response.setEntity(savedConfiguration);
//            response.setStatusCode(HttpStatus.OK.value());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            response.setMessage("Failed to save configuration");
//            response.setEntity(null);
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
}

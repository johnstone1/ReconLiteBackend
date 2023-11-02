package com.emtechhouse.reconmasterLitebackend.Services;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.ConfigurationTable;
import com.emtechhouse.reconmasterLitebackend.Repositories.ConfigurationTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationService {
    private final ConfigurationTableRepository configurationTableRepository;

    public EntityResponse<ConfigurationTable> create1(List<ConfigurationTable> configurationTableList){
        EntityResponse response = new EntityResponse();
        try{
            configurationTableList = configurationTableList.stream().map(p->{

                Optional<ConfigurationTable> conf = configurationTableRepository.findByFileType(p.getFileType());
                if (conf.isPresent()) {
                    ConfigurationTable configurationTable = conf.get();
                    p.setFileType(configurationTable.getFileType());
                    p.setReconciliationType(configurationTable.getReconciliationType());
                    p.setLocation(configurationTable.getLocation());
                    p.setUsername(configurationTable.getUsername());
                    p.setPassword(configurationTable.getPassword());
                    p.setIpAddress(configurationTable.getIpAddress());
                    p.setPort(configurationTable.getPort());
                    p.setIsDelimited(configurationTable.getIsDelimited());
                    p.setPath(configurationTable.getPath());
                    p.setDateFormat(configurationTable.getDateFormat());
                    p.setFileName(configurationTable.getFileName());
                    p.setFileExtension(configurationTable.getFileExtension());
                    p.setRowSeparator(configurationTable.getRowSeparator());
                    p.setDelimiter(configurationTable.getDelimiter());

                }
                    return p;
                 }).collect(Collectors.toList());
            configurationTableRepository.saveAll(configurationTableList);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(configurationTableList);
        }catch (Exception e){
            log.info(e.toString());
        }
        return response;
    }

    public EntityResponse findAll(){
        EntityResponse response = new EntityResponse();
        try{
            List<ConfigurationTable> configurationTableList
                    = configurationTableRepository.findAll();
            if (configurationTableList.size()>0){
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(configurationTableList);
            }else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

        }catch (Exception e){
            log.info(e.toString());
        }
        return response;
    }

}
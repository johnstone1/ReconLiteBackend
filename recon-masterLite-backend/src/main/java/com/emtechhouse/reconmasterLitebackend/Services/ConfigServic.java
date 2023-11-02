package com.emtechhouse.reconmasterLitebackend.Services;
import com.emtechhouse.reconmasterLitebackend.DTO.EntityResponse;
import com.emtechhouse.reconmasterLitebackend.Models.*;
import com.emtechhouse.reconmasterLitebackend.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class  ConfigServic {

    private final ConfigurationTableRepository configurationTableRepository;
    private final ColuRepository coluRepository;
    private final FixedColumnRangeRepository fixedColumnRangeRepository;
    private final PriorityRepository priorityRepository;
    private final ColumnsRepository columnsRepository;


    @Value("${spring.application.files.c2bTanzania-voda}")
    private String vodaDir;

    public EntityResponse<ConfigurationTable> create1(List<ConfigurationTable> configurationList) {
        EntityResponse<ConfigurationTable> response = new EntityResponse<>();
        try {
            for (ConfigurationTable configuration : configurationList) {
                Optional<ConfigurationTable> existingConfiguration =
                        configurationTableRepository.findByFileType(configuration.getFileType());
                if (existingConfiguration.isPresent()) {
                    ConfigurationTable existing = existingConfiguration.get();
                    configuration = copyConfigs(existing, configuration);
                }

                ConfigurationTable savedConfiguration = configurationTableRepository.save(configuration);

                if (configuration.getFixedSizeRange() != null) {
                    for (FixedColumnRange fixedSizeRange : configuration.getFixedSizeRange()) {
                        fixedSizeRange.setConfigurationTable(savedConfiguration);
                        fixedColumnRangeRepository.save(fixedSizeRange);
                    }
                }

                if (configuration.getColumns() != null) {
                    for (Colu column : configuration.getColumns()) {
                        column.setConfigurationTable(savedConfiguration);
                        coluRepository.save(column);
                    }
                }
            }

            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setStatusCode(HttpStatus.CREATED.value());
            //response.setEntity((ConfigurationTable) configurationList);
        } catch (Exception e) {
            response.setMessage("Failed to save configurations");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            // Log the error
            e.printStackTrace();
        }
        return response;
    }

    private ConfigurationTable copyConfigs(
            ConfigurationTable source, ConfigurationTable target) {
        target.setFileType(source.getFileType());
        target.setReconciliationType(source.getReconciliationType());
        target.setLocation(source.getLocation());
        target.setUsername(source.getUsername());
        target.setPassword(source.getPassword());
        target.setIpAddress(source.getIpAddress());
        target.setPort(source.getPort());
        target.setHeaders(source.getHeaders());
        target.setFullFileName(source.getFileName());
        target.setRunningNumberStartIndex(source.getRunningNumberStartIndex());
        target.setRunningNumberEndIndex(source.getRunningNumberEndIndex());
        target.setIsDelimited(source.getIsDelimited());
        target.setPath(source.getPath());
        target.setDateFormat(source.getDateFormat());
        target.setFileName(source.getFileName());
        target.setFileExtension(source.getFileExtension());
        target.setRowSeparator(source.getRowSeparator());
        target.setDelimiter(source.getDelimiter());
        return target;
    }

    public EntityResponse<List<ConfigurationTable>> findAll() {
        EntityResponse<List<ConfigurationTable>> response = new EntityResponse<>();
        try {
            List<ConfigurationTable> configurationTableList = configurationTableRepository.findByDeletedFlag('N');

            if (!configurationTableList.isEmpty()){

                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(configurationTableList);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.error("Error while finding configurations", e);
            response.setMessage("Failed to retrieve configurations");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    public EntityResponse findById(Long id){

        EntityResponse response=new EntityResponse();
        try{

            Optional<ConfigurationTable> configurationTable=configurationTableRepository.findById(id);
            if(!configurationTable.isEmpty()){
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(configurationTable);
            }else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        }catch(Exception e){
            log.info(e.toString());
        }
        return response;
    }
    public EntityResponse delete(Long id){
        EntityResponse response = new EntityResponse();
        try{
            Optional<ConfigurationTable> configurationTable = configurationTableRepository.findById(id);
            if (configurationTable.isPresent()){
                ConfigurationTable bon = configurationTable.get();
                bon.setDeletedFlag('Y');

                ConfigurationTable SavedConfigurationTable=configurationTableRepository.save(bon);
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setEntity(SavedConfigurationTable);
            }else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            }
        }catch (Exception e){
            log.info(e.toString());
        }
        return response;
    }

    public EntityResponse update(Long id,ConfigurationTable configurationTable) {
        EntityResponse response = new EntityResponse();

        try {
            Optional<ConfigurationTable> configurationTable1 = configurationTableRepository.findById(id);
            if (configurationTable1.isPresent()) {
                ConfigurationTable configurationTable2 = configurationTable1.get();
                configurationTable2.setFileType(configurationTable.getFileType());
                configurationTable2.setReconciliationType(configurationTable.getReconciliationType());
                configurationTable2.setLocation(configurationTable.getLocation());
                configurationTable2.setUsername(configurationTable.getUsername());
                configurationTable2.setPassword(configurationTable.getPassword());
                configurationTable2.setIpAddress(configurationTable.getIpAddress());
                configurationTable2.setRunningNumberStartIndex(configurationTable.getRunningNumberStartIndex());
                configurationTable2.setRunningNumberEndIndex(configurationTable.getRunningNumberEndIndex());
                configurationTable2.setPort(configurationTable.getPort());
                configurationTable2.setIsDelimited(configurationTable.getIsDelimited());
                configurationTable2.setPath(configurationTable.getPath());
                configurationTable2.setHeaders(configurationTable.getHeaders());
                configurationTable2.setDateFormat(configurationTable.getDateFormat());
                configurationTable2.setFileName(configurationTable.getFileName());
                configurationTable2.setFileExtension(configurationTable.getFileExtension());
                configurationTable2.setRowSeparator(configurationTable.getRowSeparator());
                configurationTable2.setDelimiter(configurationTable.getDelimiter());

                ConfigurationTable saved =configurationTableRepository.save(configurationTable2);
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setEntity(saved);

            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Configuration with filetype " + configurationTable.getFileType() + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred ");
        }

        return response;
    }

    @Transactional
    public EntityResponse<PriorityTable> saveP(List<PriorityTable> priorityTableList) {
        EntityResponse<PriorityTable> response = new EntityResponse<>();
        try {
            for (PriorityTable priorityTable : priorityTableList) {
                // Check if the priority name is unique
                if (!isPriorityNameUnique(priorityTable)) {
                    response.setMessage("Priority name should be unique");
                    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    return response;
                }

                Optional<PriorityTable> existingPriority = priorityRepository.findById(priorityTable.getId());
                if (existingPriority.isPresent()) {
                    // If the priority already exists, copy properties from the existing one
                    PriorityTable existing = existingPriority.get();
                    priorityTable = copyConf(existing, priorityTable);
                }

                // Save the PriorityTable
                PriorityTable savedPriority = priorityRepository.save(priorityTable);

                if (priorityTable.getColumns() != null) {
                    for (Columns column : priorityTable.getColumns()) {
                        // Set references for Columns
                        column.setPriorityTable(savedPriority);
                        column.setPriorityName(savedPriority.getPriorityName());
                        // Save the Columns
                        columnsRepository.save(column);
                    }
                }
            }

            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setStatusCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            response.setMessage("Failed to save priorities");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }
        return response;
    }

    private boolean isPriorityNameUnique(PriorityTable priorityTable) {
        Optional<PriorityTable> existingPriority = priorityRepository.findByPriorityName(priorityTable.getPriorityName());
        return existingPriority.isEmpty() || existingPriority.get().getId() == priorityTable.getId();
    }

    private PriorityTable copyConf(PriorityTable source, PriorityTable target) {
        target.setStatus(source.getStatus());
        target.setPriority(source.getPriority());
        target.setDeletedFlag(source.getDeletedFlag());
        return target;
    }

    public EntityResponse<PriorityTable> savePriority(List<PriorityTable> priorityTableList) {
        EntityResponse<PriorityTable> response = new EntityResponse<>();
        try {
            for (PriorityTable priorityTable : priorityTableList) {
                if (priorityTable.getId()>0) {

                    priorityTable = priorityRepository.save(priorityTable);
                } else {

                    PriorityTable savedConfiguration = priorityRepository.save(priorityTable);

                    if (priorityTable.getColumns() != null) {
                        for (Columns columns : priorityTable.getColumns()) {
                            columns.setPriorityTable(savedConfiguration);
                            columnsRepository.save(columns);
                        }
                    }
                }
            }

            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setStatusCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            response.setMessage("Failed to save priorities");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            // Log the error
            e.printStackTrace();
        }
        return response;
    }


    public EntityResponse<List<PriorityTable>> findAllPriority() {
        EntityResponse<List<PriorityTable>> response = new EntityResponse<>();
        try {
            List<PriorityTable> priorityTableList = priorityRepository.findByDeletedFlag('N');

            if (!priorityTableList.isEmpty()) {

                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(priorityTableList);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.error("Error while finding priorities", e);
            response.setMessage("Failed to retrieve priorities");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    public EntityResponse findPriorityById(Long id){

        EntityResponse response=new EntityResponse();
        try{

            Optional<PriorityTable> priorityTable=priorityRepository.findById(id);
            if(!priorityTable.isEmpty()){
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(priorityTable);
            }else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        }catch(Exception e){
            log.info(e.toString());
        }
        return response;
    }

    public EntityResponse deletePriority1(Long id){
        EntityResponse response = new EntityResponse();
        try{
            Optional<PriorityTable> priorityTable = priorityRepository.findById(id);
            if (priorityTable.isPresent()){
                PriorityTable bon = priorityTable.get();
                bon.setDeletedFlag('Y');

                PriorityTable SavedConfigurationTable=priorityRepository.save(bon);
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setEntity(SavedConfigurationTable);
            }else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            }
        }catch (Exception e){
            log.info(e.toString());
        }
        return response;
    }

    public EntityResponse updatePriority(Long id,PriorityTable priorityTable) {
        EntityResponse response = new EntityResponse();

        try {
            Optional<PriorityTable> priorityTable1 = priorityRepository.findById(id);
            if (priorityTable1.isPresent()) {
                PriorityTable priorityTable2 = priorityTable1.get();
                priorityTable2.setPriority(priorityTable.getPriority());
                priorityTable2.setDeletedFlag(priorityTable.getDeletedFlag());
                priorityTable2.setStatus(priorityTable2.getStatus());

                PriorityTable saved =priorityRepository.save(priorityTable2);
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setEntity(saved);

            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("priority " + priorityTable.getPriority() + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred ");
        }

        return response;
    }
//    public void downloadFile(String hostname, String username, String privateKeyPath, String itemToDownload) throws Exception {
//        JSch jsch = new JSch();
//
//        file finacle = remote file
//
//        Session session = jsch.getSession(username, hostname, 22);
//        session.setConfig("PreferredAuthentications", "publickey");
//        jsch.addIdentity(privateKeyPath);
//        session.connect();
//
//        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
//        channelSftp.connect();
//
//        Vector<ChannelSftp.LsEntry> files = channelSftp.ls(itemToDownload);
//        if (!files.isEmpty()) {
//            ChannelSftp.LsEntry file = files.get(0);
//            if (!file.getAttrs().isDir()) {
//                // If it's a file, download it
//                InputStream inputStream = channelSftp.get(file.getFilename());
//                Path localPath = Path.of(file.getFilename());
//                Files.copy(inputStream, localPath, StandardCopyOption.REPLACE_EXISTING);
//                inputStream.close();
//            } else {
//                // If it's a directory, you may choose to zip and download it as in the original script
//                // For simplicity, we are not zipping here, but you can modify it to do so.
//            }
//        } else {
//            throw new Exception("File not found.");
//        }
//
//        channelSftp.disconnect();
//        session.disconnect();
//    }
}
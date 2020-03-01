package com.github.coder229.datahub3.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.coder229.datahub3.storage.FileStorageRepository;
import com.github.coder229.datahub3.upload.Upload;
import com.github.coder229.datahub3.upload.UploadRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;

@Service
@Transactional
public class DataService {
    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Clock clock;

    public void processTemperatureData(Long uploadId) {
        if (uploadId == null) {
            throw new RuntimeException("Upload ID is null");
        }

        uploadRepository.findById(uploadId)
                .map(upload -> upload.getStorage())
                .map(fileStorage -> readMessage(fileStorage.getContents()))
                .ifPresentOrElse(message -> {
                    SensorData tempData = new SensorData();
                    tempData.setCode("tempf");
                    tempData.setSource(message.get("source").asText());
                    tempData.setValue(message.get("tempf").asText());
                    tempData.setEpoch(message.get("epoch").asLong());
                    tempData.setReceived(ZonedDateTime.now(clock));
                    sensorDataRepository.save(tempData);
                }, () -> {
                    throw new RuntimeException("Error processing upload: " + uploadId);
                });
    }

    @SneakyThrows
    private JsonNode readMessage(byte[] contents) {
        return objectMapper.readTree(contents);
    }
}

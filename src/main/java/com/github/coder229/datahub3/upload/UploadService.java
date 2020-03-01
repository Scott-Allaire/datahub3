package com.github.coder229.datahub3.upload;

import com.github.coder229.datahub3.storage.FileStorage;
import com.github.coder229.datahub3.storage.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
@Transactional
public class UploadService {
    @Autowired
    FileStorageRepository storageRepository;

    @Autowired
    UploadRepository uploadRepository;

    @Autowired
    Clock clock;

    public Upload create(String filename, MediaType contentType, byte[] contents, long length) {

        FileStorage storage = new FileStorage();
        storage.setFilename(filename);
        storage.setContentType(contentType.toString());
        storage.setContents(contents);
        storage.setLength(length);
        storage = storageRepository.save(storage);

        Upload upload = new Upload();
        upload.setStorage(storage);
        upload.setUploadStatus(UploadStatus.New);
        upload.setUploaded(ZonedDateTime.now(clock));
        return uploadRepository.save(upload);
    }
}

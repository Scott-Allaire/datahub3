package com.github.coder229.datahub3.upload;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.coder229.datahub3.jobs.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private JobService jobService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(UploadViews.Public.class)
    public Upload acceptUpload(HttpEntity<byte[]> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        String filename = headers.getContentDisposition().getFilename();
        MediaType contentType = requestEntity.getHeaders().getContentType();
        byte[] contents = requestEntity.getBody();
        long length = requestEntity.getHeaders().getContentLength();

        Upload upload = uploadService.create(filename, contentType, contents, length);

        jobService.startJob(upload);

        return upload;
    }
}

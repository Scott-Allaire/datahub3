package com.github.coder229.datahub3.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.batch.operations.NoSuchJobException;
import java.nio.charset.Charset;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@SpringBootTest(classes = {JobConfigTest.BatchTestConfig.class})
class JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

//    @Autowired
//    private UploadService uploadService;

//    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testJob() throws Exception {
        ObjectNode document = objectMapper.createObjectNode();
        document.put("tempf", 68.0);
        document.put("epoch", Instant.now().toEpochMilli());
        String json = document.toString();
        byte[] bytes = json.getBytes(Charset.forName("utf-8"));

//        Upload upload = uploadService.create("test.json", MediaType.APPLICATION_JSON, bytes, bytes.length);

        JobParametersBuilder jobBuilder = new JobParametersBuilder();
        jobBuilder.addLong("uploadId", 123L);
        JobParameters jobParameters = jobBuilder.toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getExitStatus().getExitCode(), equalTo("COMPLETED"));
    }

    @Configuration
    @Import({JobConfig.class})
    static class BatchTestConfig {

        @Autowired
        private Job fileProcessingJob;

        @Bean
        JobLauncherTestUtils jobLauncherTestUtils() throws NoSuchJobException {
            JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
            jobLauncherTestUtils.setJob(fileProcessingJob);
            return jobLauncherTestUtils;
        }
    }
}

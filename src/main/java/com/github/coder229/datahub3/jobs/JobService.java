package com.github.coder229.datahub3.jobs;

import com.github.coder229.datahub3.upload.Upload;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JobService {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job sensorDataJob;

    public void startJob(Upload upload) {
        JobParametersBuilder jobBuilder = new JobParametersBuilder();
        jobBuilder.addLong("uploadId", upload.getId());
        JobParameters jobParameters = jobBuilder.toJobParameters();

        // TODO determine which job to run

        try {
            JobExecution jobExecution = jobLauncher.run(sensorDataJob, jobParameters);
            logger.info("Exit status: " + jobExecution.getExitStatus());
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }
}

package com.github.coder229.datahub3.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.TransactionManager;

@Configuration
@EnableBatchProcessing
@Import(DataSourceAutoConfiguration.class)
public class JobConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer() {

        };
    }

    @Bean
    public Job fileProcessingJob() {
        return jobs.get("fileProcessingJob")
                .start(hashingStep())
                .build();
    }

    @Bean
    protected Step hashingStep() {
        return steps.get("hashing")
                .tasklet(fileHashingTasklet())
                .build();
    }


    @Bean
    public FileHashingTasklet fileHashingTasklet() {
        FileHashingTasklet tasklet = new FileHashingTasklet();
        return tasklet;
    }
}

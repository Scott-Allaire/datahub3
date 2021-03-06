package com.github.coder229.datahub3.jobs;

import com.github.coder229.datahub3.jobs.tasklets.TestTasklet;
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

@Configuration
@EnableBatchProcessing
@Import(DataSourceAutoConfiguration.class)
public class JobConfig {

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer() {};
    }

    // Common steps

    @Bean
    protected Step testStep(TestTasklet tasklet) {
        return steps.get("TestTasklet")
                .tasklet(tasklet)
                .build();
    }
}

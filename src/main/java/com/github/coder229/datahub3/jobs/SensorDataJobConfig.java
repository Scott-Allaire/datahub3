package com.github.coder229.datahub3.jobs;

import com.github.coder229.datahub3.jobs.tasklets.TemperatureTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
public class SensorDataJobConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean("sensorDataJob")
    public Job fileProcessingJob(Step testStep,
                                 Step processSensorDataStep) {
        return jobs.get("sensorDataJob")
                .start(testStep)
                .next(processSensorDataStep)
                .build();
    }

    @Bean
    protected Step processSensorDataStep(TemperatureTasklet tasklet) {
        return steps.get("TemperatureTasklet")
                .tasklet(tasklet)
                .build();
    }
}

package com.github.coder229.datahub3.jobs.tasklets;

import com.github.coder229.datahub3.data.DataService;
import com.github.coder229.datahub3.storage.FileStorage;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemperatureTasklet implements Tasklet {
    @Autowired
    private DataService dataService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Long uploadId = (Long) chunkContext.getStepContext().getJobParameters().get("uploadId");
        dataService.processTemperatureData(uploadId);
        return RepeatStatus.FINISHED;
    }
}

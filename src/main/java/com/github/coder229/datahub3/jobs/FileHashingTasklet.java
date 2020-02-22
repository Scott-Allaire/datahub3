package com.github.coder229.datahub3.jobs;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

public class FileHashingTasklet implements Tasklet, InitializingBean {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Long uploadId = (Long) chunkContext.getStepContext().getJobParameters().get("uploadId");
        System.out.println("FileHashingTasklet.execute(" + uploadId + ")");
        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}

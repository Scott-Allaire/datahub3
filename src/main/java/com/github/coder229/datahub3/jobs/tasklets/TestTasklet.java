package com.github.coder229.datahub3.jobs.tasklets;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TestTasklet implements Tasklet, InitializingBean {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Long uploadId = (Long) chunkContext.getStepContext().getJobParameters().get("uploadId");
        logger.info("Processing upload: " + uploadId);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet");
    }
}

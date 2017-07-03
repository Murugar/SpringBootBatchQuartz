package com.iqmsoft.boot.batch.quartz;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iqmsoft.boot.batch.quartz.springbatch.tasklet.EchoTasklet;

@Configuration
public class TaskletConfiguration {

    @Autowired
    private EchoTasklet echoTasklet;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor 
           = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public Step stepEcho() {
        return stepBuilderFactory.get("stepEcho").tasklet(echoTasklet).build();
    }

    @Bean
    public Job job(Step stepEcho) throws Exception {
        return jobBuilderFactory.get("echoBatchJob")
        		.incrementer(new RunIdIncrementer()).start(stepEcho).build();
    }

}

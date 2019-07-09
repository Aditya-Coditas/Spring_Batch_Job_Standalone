package com.coditas.SpringBatchStandalone;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchStandaloneApplication {

	public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		SpringApplication app=new SpringApplication(SpringBatchStandaloneApplication.class);
		ConfigurableApplicationContext ctx= app.run(args);
		JobLauncher jobLauncher=ctx.getBean(JobLauncher.class);
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		JobExecution jobexe=jobLauncher.run(ctx.getBean(Job.class),jobParameters);
		BatchStatus batchStatus=jobexe.getStatus();
		System.out.println(batchStatus);
	}
}

package com.example.demo.job;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.example.demo.client.HelloWorldClient;
import com.example.demo.client.SoapCountryClientImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@DisallowConcurrentExecution
public class MyJob implements Job {

	private final HelloWorldClient helloWorldClient;

	private final SoapCountryClientImpl soapCountryClientImpl;

	public MyJob(HelloWorldClient helloWorldClient, final SoapCountryClientImpl soapCountryClientImpl) {
		this.helloWorldClient = helloWorldClient;
		this.soapCountryClientImpl = soapCountryClientImpl;

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String jobId = (String) dataMap.get("jobID");
		log.info(MessageFormat.format("Job: {0}", getClass()));
		helloWorldClient.callHelloWorld("jobId");
		soapCountryClientImpl.getCountry("Spain");
		log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(),
				context.getNextFireTime());
		log.info("Job Started-" + jobId + " at:" + LocalDateTime.now());
	}

}

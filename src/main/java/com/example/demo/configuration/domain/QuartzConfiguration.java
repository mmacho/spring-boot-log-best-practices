package com.example.demo.configuration.domain;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.example.demo.job.MyJob;
import com.example.demo.job.QuartzJobListener;

@Configuration
public class QuartzConfiguration {

	// @Bean
	Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
		Scheduler scheduler = factory.getScheduler();
		scheduler.start();
		return scheduler;
	}

	@Bean(name = "quartzJob")
	JobDetail quartzJob() {
		return JobBuilder.newJob(MyJob.class).withIdentity("QuartzJob", "quartzJobs").storeDurably().build();
	}

	@Bean
	Trigger quartzTrigger(@Qualifier("quartzJob") JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity("quartzTrigger", "quartzJobs")
				.withDescription("Quartz trigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(120))
				.build();
	}

	// @Bean
	Trigger cronQuartzTrigger(@Qualifier("quartzJob") JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity("cronQuartzTrigger", "quartzJobs")
				.withDescription("Cron Quartz trigger").withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
				.build();
	}

	@Bean(name = "quartzJobListener")
	JobListener quartzListener() {
		return new QuartzJobListener();
	}

	@Bean
	SchedulerFactoryBeanCustomizer schedulerConfiguration(@Qualifier("quartzJobListener") JobListener listener) {
		return bean -> {
			bean.setGlobalJobListeners(listener);
		};
	}

}

package com.example.demo.configuration;

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

	@Bean(name = "QuartzJob")
	JobDetail quartzJob() {
		return JobBuilder.newJob(MyJob.class).withIdentity("QuartzJob", "QuartzJobs").storeDurably().build();
	}

	@Bean
	Trigger quartzTrigger(@Qualifier("QuartzJob") JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity("QuartzTrigger", "QuartzJobs")
				.withDescription("Quartz trigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(10)).build();
	}

	@Bean
	Trigger cronQuartzTrigger(@Qualifier("QuartzJob") JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity("CronQuartzTrigger", "QuartzJobs")
				.withDescription("Cron Quartz trigger").withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
				.build();
	}

	@Bean(name = "QuartzJobListener")
	JobListener quartzListener() {
		return new QuartzJobListener();
	}

	@Bean
	SchedulerFactoryBeanCustomizer schedulerConfiguration(@Qualifier("QuartzJobListener") JobListener listener) {
		return bean -> {
			bean.setGlobalJobListeners(listener);
		};
	}

}

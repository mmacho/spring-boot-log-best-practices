package com.example.demo.configuration.domain;

import javax.validation.constraints.Min;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;

import lombok.Setter;

@Configuration
@ConditionalOnProperty(name = "async.enabled", havingValue = "true", matchIfMissing = false)
@ConfigurationProperties("async.executor")
@EnableAsync
@Validated
@Setter
public class AsyncConfiguration {

	@Min(0)
	private int corePoolSize = 2;
	@Min(2)
	private int maxPoolSize = 10;
	@Min(0)
	private int queueCapacity = Integer.MAX_VALUE;
	private boolean waitForTasksToCompleteOnShutdown = true;
	private String threadNamePrefix = "Async-";

	@Bean
	TaskExecutor taskExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
		executor.setThreadNamePrefix(threadNamePrefix);
		return executor;
	}
}

package com.example.demo.configuration.domain;

import java.util.concurrent.Executor;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;

import lombok.Setter;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "async.enabled", havingValue = "true", matchIfMissing = false)
@ConfigurationProperties("async.executor")
@EnableAsync
@Validated
@Setter
//add the infrastructure role to ensure that the bean gets auto-proxied
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AsyncConfiguration extends AsyncConfigurerSupport {

	@Min(0)
	private int corePoolSize = 2;
	@Min(2)
	private int maxPoolSize = 10;
	@Min(0)
	private int queueCapacity = Integer.MAX_VALUE;
	private boolean waitForTasksToCompleteOnShutdown = true;
	private String threadNamePrefix = "Async-";

	BeanFactory beanFactory;

	public AsyncConfiguration(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	@Bean("AsyncTaskExecutor")
	public Executor getAsyncExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return new LazyTraceExecutor(this.beanFactory, executor);
	}

}

package com.example.demo.configuration.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.log.CodePathInterceptor;
import com.example.demo.interceptor.log.UserInterceptor;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CodePathInterceptor());
		registry.addInterceptor(new UserInterceptor());
	}
}

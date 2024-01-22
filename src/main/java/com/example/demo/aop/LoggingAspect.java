package com.example.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

	private static final String ALL_LOGGING_SPRING_BEANS = "within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.stereotype.Service *)"
			+ " || within(@org.springframework.stereotype.Component *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)";

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut(ALL_LOGGING_SPRING_BEANS)
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
	}

	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
	@Pointcut("within(com.example.demo.repository..*)" + " || within(com.example.demo.service..*)"
			+ " || within(com.example.demo.controller..*)")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("applicationPackagePointcut() && springBeanPointcut()")
	@SneakyThrows
	public Object logAround(ProceedingJoinPoint joinPoint) {
		if (log.isInfoEnabled()) {
			log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		try {
			final StopWatch stopWatch = new StopWatch();
			// Calculate method execution time
			stopWatch.start();
			final Object result = joinPoint.proceed();
			stopWatch.stop();
			if (log.isInfoEnabled()) {
				log.info("Exit: {}.{}() with result = {} executed in {} ms",
						joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), result,
						stopWatch.getTotalTimeMillis());
			}
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			throw e;
		}
	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param e         exception
	 */
	@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
	}
}

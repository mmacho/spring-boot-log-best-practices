package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAop {

	@Pointcut("within(com.example.demo.repository..*)" + " || within(com.example.demo.service..*)"
			+ " || within(com.example.demo.controller..*)")
	public void logAllMethodsStartAndEnd() {

	}

	@Around("logAllMethodsStartAndEnd()")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		log.info(pjp.getTarget().getClass().getSimpleName() + "-" + pjp.getSignature().getName() + "() start");
		Object returnValue = pjp.proceed();
		log.info(pjp.getTarget().getClass().getSimpleName() + "-" + pjp.getSignature().getName() + "() end");

		return returnValue;
	}
}

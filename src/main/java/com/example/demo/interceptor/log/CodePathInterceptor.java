package com.example.demo.interceptor.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CodePathInterceptor implements HandlerInterceptor {

	private static final String CODE_PATH = "codePath";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getHeader("X-CUSTOM-HEADER") != null) {
			MDC.put(CODE_PATH, "3rdParty");
		} else {
			MDC.put(CODE_PATH, "user");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		MDC.remove(CODE_PATH);
	}

}
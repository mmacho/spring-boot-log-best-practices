package com.example.demo.interceptor.soap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

@Component
public class GlobalEndpointInterceptor implements EndpointInterceptor {

	private static final Log LOG = LogFactory.getLog(GlobalEndpointInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		LOG.info("Handle CountryEndpoint Request");
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		LOG.info("Handle CountryEndpoint Response");
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		LOG.info("Handle CountryEndpoint Exceptions");
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
		LOG.info("Handle CountryEndpoint After Completion");
	}
}

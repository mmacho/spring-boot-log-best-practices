package com.example.demo.endpoint;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{" + CountryNotFoundException.NAMESPACE_URI
		+ "}custom_fault", faultStringOrReason = "@faultString")
public class CountryNotFoundException extends Exception {

	private static final long serialVersionUID = -2503148248466162014L;

	public static final String NAMESPACE_URI = "http://memorynotfound.com/exception";

	public CountryNotFoundException(String message) {
		super(message);
	}

}

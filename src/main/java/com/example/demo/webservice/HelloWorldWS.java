package com.example.demo.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorldWS {
	@WebMethod
	String createMessage(@WebParam(name = "createMessageRequest", mode = WebParam.Mode.IN) String name);
}

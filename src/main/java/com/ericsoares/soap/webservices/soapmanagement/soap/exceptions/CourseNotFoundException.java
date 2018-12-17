package com.ericsoares.soap.webservices.soapmanagement.soap.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode=FaultCode.CUSTOM, customFaultCode="{http://ericsoares.com/courses}001_COURSE_NOT_FOUND") 
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8426436680613203141L;

	public CourseNotFoundException(String message) {
		super(message);
	}
	
}

package com.ericsoares.soap.webservices.soapmanagement.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ericsoares.courses.CourseDetails;
import com.ericsoares.courses.GetCourseDetailsRequest;
import com.ericsoares.courses.GetCourseDetailsResponse;
import com.ericsoares.soap.webservices.soapmanagement.soap.bean.Course;
import com.ericsoares.soap.webservices.soapmanagement.soap.services.CourseDetailsService;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;
	
	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	@PayloadRoot(namespace="http://ericsoares.com/courses", localPart="GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		
		Course course = service.findById(request.getId());
		
		return mapCourse(request, course);
	}

	private GetCourseDetailsResponse mapCourse(GetCourseDetailsRequest request, Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();

		CourseDetails courseDetails = new CourseDetails();
		
		courseDetails.setId(request.getId());
		
		courseDetails.setName(course.getName());
		
		courseDetails.setDescription(course.getDescription());
		
		response.setCourseDetails(courseDetails);
		
		return response;
	}
}

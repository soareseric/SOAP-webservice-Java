package com.ericsoares.soap.webservices.soapmanagement.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ericsoares.courses.CourseDetails;
import com.ericsoares.courses.DeleteCourseDetailsRequest;
import com.ericsoares.courses.DeleteCourseDetailsResponse;
import com.ericsoares.courses.GetAllCourseDetailsRequest;
import com.ericsoares.courses.GetAllCourseDetailsResponse;
import com.ericsoares.courses.GetCourseDetailsRequest;
import com.ericsoares.courses.GetCourseDetailsResponse;
import com.ericsoares.soap.webservices.soapmanagement.soap.bean.Course;
import com.ericsoares.soap.webservices.soapmanagement.soap.exceptions.CourseNotFoundException;
import com.ericsoares.soap.webservices.soapmanagement.soap.services.CourseDetailsService;
import com.ericsoares.soap.webservices.soapmanagement.soap.services.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	@PayloadRoot(namespace = "http://ericsoares.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Course course = service.findById(request.getId());

		if(course == null)
			throw new CourseNotFoundException("Invalid Course Id" + request.getId());
		
		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();

		response.setCourseDetails(mapCourse(course));

		return response;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();

		courseDetails.setId(course.getId());

		courseDetails.setName(course.getName());

		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

	@PayloadRoot(namespace = "http://ericsoares.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {

		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);	
	}
	
	@PayloadRoot(namespace = "http://ericsoares.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public 	DeleteCourseDetailsResponse deleteCourseDetailsRequest(
			@RequestPayload DeleteCourseDetailsRequest request) {

		Status status = service.deleteById(request.getId());

		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
	}

	private com.ericsoares.courses.Status mapStatus(Status status) {
		if(status == Status.FAILURE)
			return com.ericsoares.courses.Status.FAILURE;
		return com.ericsoares.courses.Status.SUCCESS;
	}
}

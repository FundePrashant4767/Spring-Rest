package com.jspiders.springrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jspiders.springrest.pojo.StudentPOJO;
import com.jspiders.springrest.responce.StudentResponce;
import com.jspiders.springrest.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService service;
	@PostMapping(path = "/add",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
			
			public ResponseEntity<StudentResponce> 
	                   addStudents(@RequestBody StudentPOJO pojo){
		StudentPOJO student = service.addStudent(pojo);
		StudentResponce responce = new StudentResponce();
		//success flow
		if(student != null) {
			responce.setMessage("Data Inserted Succesfully.");
			responce.setData(student);
			return new ResponseEntity<StudentResponce>(responce,HttpStatus.ACCEPTED);
			
		}
		//Failure flow
		responce.setMessage("Data Not Inserted.");
		return new ResponseEntity<StudentResponce>(responce,HttpStatus.BAD_REQUEST);
	}
	@GetMapping(path = "/search",
			produces =  MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<StudentResponce>search(@RequestBody StudentPOJO pojo){
		
		StudentPOJO student = service.searchStudent(pojo.getId());
		
		//success flow
		
		if (student != null) {
			return new ResponseEntity<StudentResponce>
			          (new StudentResponce("Data Found Successfully.",student,null),HttpStatus.FOUND);
		}
		//failure flow
		return new ResponseEntity<StudentResponce>
		           (new StudentResponce("Data Does not exist", null, null),
		        		   HttpStatus.NOT_FOUND);
	}
	@GetMapping(path = "/searchAll",
			produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<StudentResponce> searchAllStudents() {
	List<StudentPOJO> students = service.searchAllStudents();
	if (students != null) {
		return new ResponseEntity<StudentResponce>
				(new StudentResponce("Data Does exist", null, students),HttpStatus.FOUND);
	}
	return new ResponseEntity<StudentResponce>
	(new StudentResponce("No student data found. ",
					null, null),
			HttpStatus.NOT_FOUND);
}

	
	
}

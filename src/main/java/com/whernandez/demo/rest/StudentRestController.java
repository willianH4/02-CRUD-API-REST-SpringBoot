package com.whernandez.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whernandez.demo.entity.Student;
import com.whernandez.demo.exception.StudentErrorResponse;
import com.whernandez.demo.exception.StudentNotFoundException;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	
	private List<Student> theStudents;
	
	// define @PostConstruct to load the student data ... only once!
	
	@PostConstruct
	public void loadData() {
		theStudents = new ArrayList<>();
		theStudents.add(new Student("Maria", "Palilla"));
		theStudents.add(new Student("Cassandra", "Palilla"));
		theStudents.add(new Student("Miguel", "Palilla"));
	}
	
	// define endpoint for "/students" - return a list of students
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		return theStudents;
	}
	
	// define endpoint or "/students/{studentId} - return student at index"
	
	@GetMapping("students/{studentId}")
	public Student getStudent(@PathVariable int studentId) {
		
		// just index into the list ... keep it simple for now
		
		// check the studentId again list size
		if( (studentId >= theStudents.size()) || (studentId < 0)) {
			throw new StudentNotFoundException("Student id not found - " + studentId);
		}
		
		return theStudents.get(studentId);
	}
	
	// Add an exception handler using @ExceptionHandler
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handlerException(StudentNotFoundException exc) {
		
		// create a StudentErrorResponse
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	// Add another exception handler ... to catch any exception (catch all)
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
		// create a StudentErrorResponse
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage()); // replace for custom message
		error.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}

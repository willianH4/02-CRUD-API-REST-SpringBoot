package com.whernandez.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whernandez.demo.entity.Student;

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
		
		return theStudents.get(studentId);
	}
	
	
}

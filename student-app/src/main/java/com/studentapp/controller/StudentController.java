package com.studentapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentapp.dto.StudentDto;
import com.studentapp.entity.StudentEntity;
import com.studentapp.pdf.StudentPDF;
import com.studentapp.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="student controller provider")
@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;

	@ApiOperation(value = "demo Api")
	@GetMapping("/ping")
	public String getMessage() {
		return "hello web ...........";
	}

	@ApiOperation(value = "API to add student details")
	@PostMapping("/addStudentDetails")
	public String addStudent(@RequestBody StudentDto dto) {
		studentService.addStudentDetails(dto);
		return "Student details added successfully...";
	}

	@ApiOperation(value = "API to get all student details")
	@GetMapping("/getAllStudentDetails")
	public List<StudentEntity> getAllStudent(){
		return studentService.getAllStudent();
	}

	@ApiOperation(value = "API to get student details by Id")
	@GetMapping("/getStudentDetails/{id}")
	public Optional<StudentEntity> getStudent(@PathVariable int id) {
		return studentService.getStudentById(id);
	}

	@ApiOperation(value = "API to update student details")
	@PutMapping("/updateStudentDetails/{id}")
	public String updateDetails(@PathVariable int id, @RequestBody StudentDto dto) {
		studentService.updateDetails(id, dto);
		return "Student Details updated successfully...";
	}
	
	@ApiOperation(value = "API to delete student details by Id")
	@DeleteMapping("/deleteStudentDetails/{id}")
	public String deleteDetails(@PathVariable int id) {
		studentService.deleteDetails(id);
		return "Student Details deleted succesfully...";
	}
	
	@ApiOperation(value = "Api to get pdf of students details")
	@GetMapping("/student/create/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException, com.itextpdf.text.DocumentException {
		response.setContentType("student/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=studentsDetails" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		List<StudentEntity> studentlist = studentService.getAllStudent();
		StudentPDF exporter = new StudentPDF(studentlist);
		exporter.export(response);

	}
}

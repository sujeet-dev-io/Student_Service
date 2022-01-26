package com.studentapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentapp.constant.Message;
import com.studentapp.dto.Status;
import com.studentapp.dto.StudentRequestDto;
import com.studentapp.pdf.StudentPDF;
import com.studentapp.response.BaseResponse;
import com.studentapp.response.StudentResponse;
import com.studentapp.service.IStudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags= Message.STUDENT_CONTROLLER)
@RequestMapping("/api")
@RestController
public class StudentController {
	
	@Autowired
	private IStudentService studentService;

	@ApiOperation(value = "Ping Api")
	@GetMapping("/ping")
	public String getMessage() {
		return Message.PING;
	}

	@ApiOperation(value = Message.ADD_STUDENT)
	@PostMapping("/addStudentDetails")
	public ResponseEntity<BaseResponse<String, Integer>> addStudent(
			@RequestBody StudentRequestDto dto) {
		BaseResponse<String, Integer> response = new BaseResponse<>();
		response.setMessage("Student details added successfully");
		response.setStatus(Status.SUCCESS);
		studentService.addStudentDetails(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "API to get all student details")
	@GetMapping("/getAllStudentDetails")
	public ResponseEntity<BaseResponse<List<StudentResponse>, Integer>> getAllStudent(){
		BaseResponse<List<StudentResponse>, Integer> response = new BaseResponse<>();
		response.setMessage("Student details list fetched successfully");
		response.setStatus(Status.SUCCESS);
		response.setData(studentService.getAllStudent());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "API to get student details by Id")
	@GetMapping("/getStudentDetails/{id}")
	public ResponseEntity<BaseResponse<StudentResponse, Integer>>  getStudent(
			@PathVariable int id) {
		BaseResponse<StudentResponse, Integer> response = new BaseResponse<>();
		response.setMessage("Student details fetched successfully");
		response.setStatus(Status.SUCCESS);
		response.setData(studentService.getStudentById(id));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "API to update student details")
	@PutMapping("/updateStudentDetails/{id}")
	public ResponseEntity<BaseResponse<String, Integer>> updateDetails(
			@PathVariable int id,
			@RequestBody StudentRequestDto dto) {
		BaseResponse<String, Integer> response = new BaseResponse<>();
		studentService.updateDetails(id, dto);
		response.setMessage("Student Details updated successfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "API to delete student details by Id")
	@DeleteMapping("/deleteStudentDetails/{id}")
	public ResponseEntity<BaseResponse<String, Integer>> deleteDetails(
			@PathVariable int id) {
		BaseResponse<String, Integer> response = new BaseResponse<>();
		studentService.deleteDetails(id);
		response.setMessage("Student Details deleted succesfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Api to get pdf of students details")
	@GetMapping("/student/create/pdf")
	public void exportToPDF(
			HttpServletResponse response
			) throws DocumentException,
	IOException,
	com.itextpdf.text.DocumentException {
		response.setContentType("student/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=studentsDetails" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		List<StudentResponse> studentList = studentService.getAllStudent();
		StudentPDF exporter = new StudentPDF(studentList);
		exporter.export(response);
	}
	
}

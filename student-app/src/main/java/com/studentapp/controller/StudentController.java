package com.studentapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.studentapp.enums.Status;
import com.studentapp.dto.AddStudentRequest;
import com.studentapp.pdf.StudentPDF;
import com.studentapp.response.BaseResponse;
import com.studentapp.response.StudentResponse;
import com.studentapp.service.IStudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags= Message.STUDENT_CONTROLLER)
@RequestMapping("/api/student")
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
	@PostMapping("/add")
	public ResponseEntity<BaseResponse<Boolean, Integer>> addStudent(
			@Valid @RequestBody AddStudentRequest request) {
		BaseResponse<Boolean, Integer> response = new BaseResponse<>();
		response.setData(studentService.addStudentDetails(request));
		response.setMessage("Student details added successfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = Message.GET_ALL_STUDENT)
	@GetMapping("/getAll")
	public ResponseEntity<BaseResponse<List<StudentResponse>, Integer>> getAllStudent(){
		BaseResponse<List<StudentResponse>, Integer> response = new BaseResponse<>();
		response.setData(studentService.getAllStudent());
		response.setMessage("Student details list fetched successfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = Message.GET_STUDENT)
	@GetMapping("/get/{id}")
	public ResponseEntity<BaseResponse<StudentResponse, Integer>> getStudent(
			@PathVariable Integer id) {
		BaseResponse<StudentResponse, Integer> response = new BaseResponse<>();
		response.setData(studentService.getStudentById(id));
		response.setMessage("Student details fetched successfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = Message.UPDATE_STUDENT)
	@PutMapping("/update/{id}")
	public ResponseEntity<BaseResponse<Boolean, Integer>> updateDetails(
			@PathVariable Integer id,
			@Valid @RequestBody AddStudentRequest request) {
		BaseResponse<Boolean, Integer> response = new BaseResponse<>();
		response.setData(studentService.updateDetails(id, request));
		response.setMessage("Student Details updated successfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = Message.DELETE_STUDENT)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BaseResponse<Boolean, Integer>> deleteDetails(
			@PathVariable Integer id) {
		BaseResponse<Boolean, Integer> response = new BaseResponse<>();
		response.setData(studentService.deleteDetails(id));
		response.setMessage("Student Details deleted successfully");
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = Message.GET_ALL_STUDENT_PDF)
	@GetMapping("/getAll/pdf")
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

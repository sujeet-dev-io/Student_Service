package com.studentapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@AllArgsConstructor
public class StudentController {

	private final IStudentService studentService;

	@ApiOperation(value = "Ping Api")
	@GetMapping("/ping")
	public String getMessage() {
		return Message.PING;
	}

	@ApiOperation(value = Message.ADD_STUDENT)
	@PostMapping("/add")
	public ResponseEntity<BaseResponse<Object>> addStudent(@Valid @RequestBody AddStudentRequest request) {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("Student Added")
				.data(studentService.addStudentDetails(request))
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = Message.GET_ALL_STUDENT)
	@GetMapping("/getAll")
	public ResponseEntity<BaseResponse<Object>> getAllStudent() throws JsonProcessingException {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("Student List Fetched")
				.data(studentService.getAllStudent())
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = Message.GET_STUDENT)
	@GetMapping("/get/{id}")
	public ResponseEntity<BaseResponse<Object>> getStudent(@PathVariable Integer id) {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("Student Fetched")
				.data(studentService.getStudentById(id))
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = Message.UPDATE_STUDENT)
	@PutMapping("/update/{id}")
	public ResponseEntity<BaseResponse<Object>> updateDetails(@PathVariable Integer id, @Valid @RequestBody AddStudentRequest request) {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("Student Updated")
				.data(studentService.updateDetails(id, request))
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = Message.DELETE_STUDENT)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BaseResponse<Object>> deleteDetails(@PathVariable Integer id) {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("Student Deleted")
				.data(studentService.deleteDetails(id))
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = Message.GET_ALL_STUDENT_PDF)
	@GetMapping("/getAll/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException, com.itextpdf.text.DocumentException {
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

package com.studentapp.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddStudentRequest {

	@NotBlank(message = "student first name must not be blank")
	private String firstName;
	@NotBlank(message = "student last name must not be blank")
	private String lastName;
	@NotBlank(message = "student age must not be blank")
	private String age;
	@NotBlank(message = "student branch must not be blank")
	private String branch;
	@NotBlank(message = "student email must not be blank")
	private String email;
	private List<Subject> subjects;
	@NotBlank(message = "student mobile number must not be blank")
	private String mobileNumber;
	@NotBlank(message = "student Address number must not be blank")
	private Address address;

}

package com.studentapp.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class StudentRequestDto {

	@NotBlank(message = "student name must not be blank")
	private String studentName;
	@NotBlank(message = "student age must not be blank")
	private String studentAge;
	@NotBlank(message = "student branch must not be blank")
	private String studentBranch;

}

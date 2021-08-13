package com.studentapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Student details")
@Entity
@Data
@Table(name="student_table")
public class StudentEntity {

	@ApiModelProperty(notes="auto generated")
	@Id
	@GeneratedValue
	private int studentId;

	@ApiModelProperty(required=true, notes="enter name")
	@Size(
			min=6,
			max=18,
			message="property '${validatedValue}' should be between {min} and {max} characters")
	private String studentName;

	@ApiModelProperty(required=true, notes="enter age")
	private String studentAge;

	@ApiModelProperty(required=true, notes="enter branch")
	private String studentBranch;

}

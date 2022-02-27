package com.studentapp.entity;

import javax.persistence.Column;
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
public class StudentEntity extends BaseEntity {

	@ApiModelProperty(notes="auto generated")
	@Id
	@GeneratedValue
	@Column(name = "student_id")
	private int studentId;

	@ApiModelProperty(required=true, notes="enter name")
	@Column(name = "student_name")
	@Size(
			min=6,
			max=18,
			message="property '${validatedValue}' should be between {min} and {max} characters")
	private String studentName;

	@ApiModelProperty(required=true, notes="enter age")
	@Column(name = "student_age")
	private String studentAge;

	@ApiModelProperty(required=true, notes="enter branch")
	@Column(name = "student_branch")
	private String studentBranch;
	
	@Column(name="deleted")
	private Boolean deleted = Boolean.FALSE;

}

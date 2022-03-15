package com.studentapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("Student sequence details")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="student_sequence")
public class Sequence extends BaseEntity {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, length=10)
    private long id;
	
	@ApiModelProperty(notes="Student Sequence No")
	@Column(name = "sequence_no")  
	private String studentSequenceNo;
	@ApiModelProperty(notes="Student Id")
	@Column(name = "student_id")
	private Integer studentId;

}

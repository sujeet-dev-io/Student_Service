package com.studentapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("Student details")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "student_table")
public class StudentEntity extends BaseEntity {

    @ApiModelProperty(notes = "auto generated")
    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Integer studentId;

    @ApiModelProperty(required = true, notes = "enter name")
    @Column(name = "name")
    @Size(
            min = 6,
            max = 18,
            message = "property '${validatedValue}' should be between {min} and {max} characters")
    private String name;

    @ApiModelProperty(required = true, notes = "enter age")
    @Column(name = "age")
    private String age;

    @ApiModelProperty(required = true, notes = "enter branch")
    @Column(name = "branch")
    private String branch;

    @ApiModelProperty(required = true, notes = "enter email")
    @Column(name = "email")
    private String email;

    @ApiModelProperty(required = true, notes = "enter email")
    @Column(name = "mobile_no")
    private String mobileNumber;

    @ApiModelProperty(required = true)
    @Column(name = "student_no")
    private String studentNo;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

}

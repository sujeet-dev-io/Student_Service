package com.studentapp.service;

import java.util.List;

import com.studentapp.dto.AddStudentRequest;
import com.studentapp.response.StudentResponse;

public interface IStudentService {

	Boolean addStudentDetails(AddStudentRequest dto);

	List<StudentResponse>  getAllStudent();

	StudentResponse getStudentById(int id);

	Boolean updateDetails(int id, AddStudentRequest dto);

	Boolean deleteDetails(int id);

}

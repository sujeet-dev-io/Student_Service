package com.studentapp.service;

import java.util.List;

import com.studentapp.dto.StudentRequestDto;
import com.studentapp.response.StudentResponse;

public interface IStudentService {

	Boolean addStudentDetails(StudentRequestDto dto);

	List<StudentResponse>  getAllStudent();

	StudentResponse getStudentById(int id);

	Boolean updateDetails(int id, StudentRequestDto dto);

	Boolean deleteDetails(int id);

}

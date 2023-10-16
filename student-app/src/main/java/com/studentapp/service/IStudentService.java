package com.studentapp.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studentapp.dto.AddStudentRequest;
import com.studentapp.response.StudentResponse;

public interface IStudentService {

	Boolean addStudentDetails(AddStudentRequest dto);

	List<StudentResponse>  getAllStudent() throws JsonProcessingException;

	StudentResponse getStudentById(Integer id);

	Boolean updateDetails(Integer id, AddStudentRequest dto);

	Boolean deleteDetails(Integer id);

}

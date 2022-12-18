package com.studentapp.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.studentapp.dao.StudentDao;
import com.studentapp.dto.AddStudentRequest;
import com.studentapp.entity.StudentEntity;
import com.studentapp.response.StudentResponse;
import com.studentapp.service.IStudentService;

import javax.validation.constraints.NotNull;

@Service
public class StudentService implements IStudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private ModelMapper mapper;

	public StudentService() {
	}

	@Override
	public Boolean addStudentDetails(AddStudentRequest request) {
		StudentEntity entity = mapper.map(request, StudentEntity.class);
		entity.setCreatedAt(getCurrentTimestamp());
		entity.setCreatedBy(request.getEmail());
		StudentEntity savedEntity = studentDao.save(entity);
		savedEntity.setStudentNo(generateStudentNo(savedEntity.getStudentId()));
		studentDao.save(savedEntity);
		return true;
	}

	@Override
	public List<StudentResponse> getAllStudent() {
		List<StudentResponse> responseList = new ArrayList<>();
		List<StudentEntity> listOfStudent = studentDao.findAll();
		if(!CollectionUtils.isEmpty(listOfStudent)) {
			listOfStudent.forEach(std -> {
				StudentResponse response = mapper.map(std, StudentResponse.class);
				responseList.add(response);
			});
		}

		return responseList;
	}

	@Override
	public StudentResponse getStudentById(Integer id) {
		StudentResponse response = null;
		Optional<StudentEntity> entityOptional = studentDao.findById(id);
		if(entityOptional.isPresent()) {
			StudentEntity entity = entityOptional.get();
			response = mapper.map(entity, StudentResponse.class);
		}

		return response;
	}

	@Override
	public Boolean updateDetails(Integer id, AddStudentRequest dto) {
		Optional<StudentEntity> entityOptional = studentDao.findById(id);
		if(!entityOptional.isPresent()) 
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Record doesn't exist to update");
		StudentEntity entity = entityOptional.get();
		entity = mapper.map(dto, StudentEntity.class);
		entity.setStudentId(id);
		studentDao.save(entity);
		return true;
	}

	@Override
	public Boolean deleteDetails(Integer id) {
		Optional<StudentEntity> entityOptional = studentDao.findById(id);
		if(!entityOptional.isPresent()) 
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Record doesn't exist to delete");
		
		StudentEntity studentEntity = entityOptional.get();
		studentEntity.setDeleted(Boolean.TRUE);

		studentDao.save(studentEntity);
		return true;
	}

	private String generateStudentNo(@NotNull Integer studentId) {
		LocalDate now = LocalDate.now();
		String date = now.toString().replace("-","");
		return (date + studentId.toString());
	}
	
	public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

} 


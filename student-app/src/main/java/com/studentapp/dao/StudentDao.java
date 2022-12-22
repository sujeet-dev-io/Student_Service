package com.studentapp.dao;

import java.util.List;
import java.util.Optional;

import com.studentapp.entity.StudentEntity;

public interface StudentDao {
	
	StudentEntity save(StudentEntity entity);
	
	void delete(StudentEntity entity);
	
	Optional<StudentEntity> findById(int id);

	Optional<StudentEntity> findByMobileNumber(String mobNo);

	List<StudentEntity> findAll();

}

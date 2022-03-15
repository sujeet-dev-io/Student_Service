package com.studentapp.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.studentapp.dao.StudentDao;
import com.studentapp.entity.StudentEntity;
import com.studentapp.repository.StudentRepository;

@Component
public class StudentDaoImpl implements StudentDao {
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public StudentEntity save(StudentEntity entity) {
		return studentRepository.save(entity);
	}

	@Override
	public void delete(StudentEntity entity) {
		studentRepository.delete(entity);
	}

	@Override
	public Optional<StudentEntity> findById(int id) {
		return studentRepository.findById(id);
	}

	@Override
	public List<StudentEntity> findAll() {
		return studentRepository.findAll();
	}

}

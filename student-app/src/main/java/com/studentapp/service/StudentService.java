package com.studentapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentapp.dto.StudentDto;
import com.studentapp.entity.StudentEntity;
import com.studentapp.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public StudentEntity addStudentDetails(StudentDto dto) {
		StudentEntity entity = new StudentEntity();
		entity.setStudentName(dto.getStudentName());
		entity.setStudentAge(dto.getStudentAge());
		entity.setStudentBranch(dto.getStudentBranch());
		return studentRepository.save(entity);
	}

	public List<StudentEntity> getAllStudent(){
		return studentRepository.findAll();
	}

	public Optional<StudentEntity> getStudentById(int id) {
		return studentRepository.findById(id);
	}

	public boolean updateDetails(int id,StudentDto dto) {
		if(studentRepository.findById(id).isPresent()) {
			StudentEntity entity=studentRepository.findById(id).get();
			entity.setStudentName(dto.getStudentName());
			entity.setStudentAge(dto.getStudentAge());
			entity.setStudentBranch(dto.getStudentBranch());
			studentRepository.save(entity);
			return true;
		}
		else {
			return false;
		}

	}

	public void deleteDetails(int id) {
		StudentEntity entity=studentRepository.getOne(id);
		studentRepository.delete(entity);
	}

} 


package com.studentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentapp.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

	StudentEntity save(StudentEntity entity);
}

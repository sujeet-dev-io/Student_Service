package com.studentapp.repository;

import com.studentapp.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    StudentEntity save(StudentEntity entity);

    Optional<StudentEntity> findByMobileNumber(String mobNo);
}

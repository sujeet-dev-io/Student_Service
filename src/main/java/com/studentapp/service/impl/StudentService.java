package com.studentapp.service.impl;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentapp.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.studentapp.dao.StudentDao;
import com.studentapp.dto.AddStudentRequest;
import com.studentapp.entity.StudentEntity;
import com.studentapp.response.StudentResponse;
import com.studentapp.service.IStudentService;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
public class StudentService implements IStudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    public StudentService() {
    }

    @Override
    public Boolean addStudentDetails(AddStudentRequest request) {
        log.info("Request received to add student details, req :: {}", request);
        Optional<StudentEntity> studentOpt = studentDao.findByMobileNumber(request.getMobileNumber());
        if (studentOpt.isPresent())
            throw new BadRequestException("Student Already exist with this mobile no.");

        StudentEntity entity = modelMapper.map(request, StudentEntity.class);
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
        if (!CollectionUtils.isEmpty(listOfStudent)) {
            listOfStudent.forEach(std -> {
                StudentResponse response = modelMapper.map(std, StudentResponse.class);
                responseList.add(response);
            });
        }

        return responseList;
    }

    @Override
    public StudentResponse getStudentById(Integer id) {
        log.info("Request received to fetch student for ID :: {}", id);
        StudentResponse response = null;
        Optional<StudentEntity> entityOptional = studentDao.findById(id);
        if (!entityOptional.isPresent()) {
            throw new BadRequestException("Student not found with the given Id");
        }
        StudentEntity entity = entityOptional.get();
        return modelMapper.map(entity, StudentResponse.class);
    }

    @Override
    public Boolean updateDetails(Integer id, AddStudentRequest updateRequest) {
        log.info("Request received to update student for ID :: {} and updateReq :: {}", id, updateRequest);
        Optional<StudentEntity> entityOptional = studentDao.findById(id);
        if (!entityOptional.isPresent())
            throw new BadRequestException("Record doesn't exist to update");
        StudentEntity entity = entityOptional.get();
        entity = modelMapper.map(updateRequest, StudentEntity.class);
        entity.setStudentId(id);
        studentDao.save(entity);
        return true;
    }

    @Override
    public Boolean SoftDeleteDetails(Integer id) {
        log.info("Request received to Soft delete student for ID :: {}", id);
        Optional<StudentEntity> entityOptional = studentDao.findById(id);
        if (!entityOptional.isPresent())
            throw new BadRequestException("Record doesn't exist to delete");

        StudentEntity studentEntity = entityOptional.get();
        studentEntity.setDeleted(Boolean.TRUE);

        studentDao.save(studentEntity);
        return true;
    }

    @Override
    public Boolean HardDeleteDetails(Integer id) {
        log.info("Request received to hard delete student for ID :: {}", id);

        Optional<StudentEntity> entityOptional = studentDao.findById(id);
        if (!entityOptional.isPresent()) {
            throw new BadRequestException("Record doesn't exist to delete");
        }
        studentDao.delete(entityOptional.get());
        log.info("Student with ID :: {} has been hard deleted", id);
        return true;
    }


    private String generateStudentNo(@NotNull Integer studentId) {
        LocalDate now = LocalDate.now();
        String date = now.toString().replace("-", "");
        return (date + studentId.toString());
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

} 


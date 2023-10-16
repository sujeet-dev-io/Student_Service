package com.studentapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentapp.dao.StudentDao;
import com.studentapp.dto.AddStudentRequest;
import com.studentapp.entity.StudentEntity;
import com.studentapp.exception.BadRequestException;
import com.studentapp.response.StudentResponse;
import com.studentapp.service.impl.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String STUDENT_ENTITY = "src/test/java/resources/student_entity.json";
    private static final String STUDENT_REQUEST = "src/test/java/resources/student_request.json";

    private static StudentEntity studentEntity() throws IOException {
//      InputStream = new FileInputStream("src/test/java/resources/student_entity.json");
        return objectMapper.readValue(new File(STUDENT_ENTITY), StudentEntity.class);

    }

    private static AddStudentRequest studentAddRequest() throws IOException {
        return objectMapper.readValue(new File(STUDENT_REQUEST), AddStudentRequest.class);
    }

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentDao studentDao;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void addStudentDetails() throws IOException {
        // Arrange
        StudentEntity student = studentEntity();
        AddStudentRequest request = studentAddRequest();
        Mockito.when(studentDao.findByMobileNumber(request.getMobileNumber())).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(request, StudentEntity.class)).thenReturn(student);
        Mockito.when(studentDao.save(student)).thenReturn(student);

        // Act
        Boolean result = studentService.addStudentDetails(request);

        // Assert
        assertNotNull(result);
        assertEquals(result, true);
    }

    @Test
    public void shouldThrowExceptionIfStudentAlreadyExistInAddStudentDetails() throws IOException {
        // Arrange
        AddStudentRequest request = studentAddRequest();
        StudentEntity student = studentEntity();
        Mockito.when(studentDao.findByMobileNumber(request.getMobileNumber())).thenReturn(Optional.of(student));

        // Act
        Exception exception = assertThrows(BadRequestException.class, ()-> studentService.addStudentDetails(request));

        // Assert
        assertEquals("Student Already exist with this mobile no.", exception.getMessage());
    }

    @Test
    public void getStudent() throws IOException {
        // Arrange
        StudentEntity student = studentEntity();
        Mockito.when(studentDao.findById(123)).thenReturn(Optional.of(student));
        Mockito.when(modelMapper.map(student, StudentResponse.class)).thenReturn(getStudentResponse(student));

        // Act
        StudentResponse result = studentService.getStudentById(123);

        // Assert
        assertNotNull(result);
        assertEquals(result.getName(), "Abhishek");
    }

    @Test
    public void getAllStudent() throws IOException {
        // Arrange
        StudentEntity student = studentEntity();
        Mockito.when(studentDao.findAll()).thenReturn(Collections.singletonList(student));
        Mockito.when(modelMapper.map(student, StudentResponse.class)).thenReturn(getStudentResponse(student));

        // Act
        List<StudentResponse> result = studentService.getAllStudent();

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getStudentId(), "123");
    }

    @Test
    public void shouldThrowExceptionIfStudentNotFound() {
        // Act
        Exception exception = assertThrows(
                BadRequestException.class,
                ()-> studentService.getStudentById(123)
        );

        // Assert
        assertEquals("Student not found with the given Id", exception.getMessage());
    }

    private StudentResponse getStudentResponse(StudentEntity student) {
        return new StudentResponse(
                String.valueOf(student.getStudentId()),
                student.getName(),
                student.getAge(),
                student.getBranch(),
                student.getEmail(),
                student.getMobileNumber(),
                student.getStudentNo()
        );
    }
}

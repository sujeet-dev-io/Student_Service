package com.studentapp.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentapp.entity.StudentEntity;
import com.studentapp.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.studentapp.TestUtil.addStudentRequest;
import static com.studentapp.TestUtil.getListOfStudents;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentControllerIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentRepository studentRepository;
    @Value("${test.api.key}")
    String apiKey;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void finish() {
        studentRepository.deleteAll();
    }

    @Order(1)
    @Test
    public void addStudentDetails_shouldSucceedWith200() throws Exception {
        mockMvc.perform(post("/api/student/add")
                        .content(objectMapper.writeValueAsString(addStudentRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void fetchAllStudentDetails_shouldSucceedWith200() throws Exception {
        addDummyDataIntoDB();
        mockMvc.perform(get("/api/student/getAll")
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Order(3)
    @Test
    public void fetchStudentById_shouldSucceedWith200() throws Exception {
        List<StudentEntity> data = addDummyDataIntoDB();
        mockMvc.perform(get("/api/student/get/{id}", data.get(0).getStudentId())
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verify content type is JSON
                .andExpect(jsonPath("$.data.name").value("test_student1"));
    }

    @Order(4)
    @Test
    public void updateStudentById_shouldSucceedWith200() throws Exception {
        List<StudentEntity> data = addDummyDataIntoDB();
        mockMvc.perform(put("/api/student/update/{id}", data.get(0).getStudentId())
                        .content(objectMapper.writeValueAsString(addStudentRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("true"));
    }

    @Order(5)
    @Test
    public void deleteStudentById_shouldSucceedWith200() throws Exception {
        List<StudentEntity> data = addDummyDataIntoDB();
        mockMvc.perform(delete("/api/student/delete/{id}",data.get(0).getStudentId())
                        .header("x-api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("true"));
    }

    private List<StudentEntity> addDummyDataIntoDB() {
        List<StudentEntity> students = getListOfStudents();
        students = studentRepository.saveAll(students);
        return students;
    }

}

package com.studentapp;

import com.studentapp.dto.AddStudentRequest;
import com.studentapp.entity.StudentEntity;

import java.util.Arrays;
import java.util.List;

public interface TestUtil {

    static AddStudentRequest addStudentRequest() {
        return new AddStudentRequest(
                "Test",
                "23",
                "IT",
                "test@Info.com",
                "9877654321"
        );
    }

    static List<StudentEntity> getListOfStudents() {
        StudentEntity std1 = new StudentEntity(1, "test_student1", "21", "IT",
                "test1@gmail.com", "123456789", "101", false);
        StudentEntity std2 = new StudentEntity(2, "test_student2", "22", "IT",
                "test2@gmail.com", "123456788", "102", false);
        StudentEntity std3 = new StudentEntity(3, "test_student3", "23", "IT",
                "test3@gmail.com", "123456787", "103", false);
        StudentEntity std4 = new StudentEntity(4, "test_student4", "24", "IT",
                "test4@gmail.com", "123456786", "104", false);
        return Arrays.asList(std1, std2, std3, std4);
    }
}

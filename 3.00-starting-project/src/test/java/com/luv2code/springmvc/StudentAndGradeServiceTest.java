package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeServiceTest {

    @Test
    void createStudentService() {
        String email = "chad.darby@luv2code_school.com";
        studentService.createStudent("Chad", "Darby", email);
        CollegeStudent student = studentDao.findByEmailAddress(email);

        assertEquals(email, student.getEmailAddress(), "Email should match");
    }
}

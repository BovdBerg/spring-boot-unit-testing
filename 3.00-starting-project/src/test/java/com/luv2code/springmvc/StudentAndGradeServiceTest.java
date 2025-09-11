package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    void setupDataBase() {
        jdbc.execute("INSERT INTO student(firstname, lastname, email_address) " +
                "VALUES ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute("DELETE FROM student");
        jdbc.execute("ALTER TABLE student ALTER COLUMN id RESTART WITH 1");  // Reset auto-incrementing ID
    }

    @Test
    void createStudentService() {
        String email = "chad.darby@luv2code_school.com";
        studentService.createStudent("Chad", "Darby", email);
        CollegeStudent student = studentDao.findByEmailAddress(email);

        assertEquals(email, student.getEmailAddress(), "Email should match");
    }

    @Test
    void isStudentNullCheck() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void isStudentNullCheck2() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void deleteStudentService() {
        Optional<CollegeStudent> student = studentDao.findById(1);

        assertTrue(student.isPresent(), "Student should be present");

        studentService.deleteStudent(1);

        student = studentDao.findById(1);

        assertFalse(student.isPresent(), "Student should not be present");
    }

    @Test
    void getGradeBookService() {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        List<CollegeStudent> studentList = new ArrayList <>();

        for (CollegeStudent student : collegeStudents) {
            studentList.add(student);
        }

        assertEquals(1, studentList.size(), "Should be 1 student in the grade book");
    }

    @Test
    @Sql("/insertData.sql")
    void getGradeBookServiceWithSql() {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        List<CollegeStudent> studentList = new ArrayList <>();

        for (CollegeStudent student : collegeStudents) {
            studentList.add(student);
        }

        assertEquals(5, studentList.size(), "Should be 5 students in the grade book");
    }
}

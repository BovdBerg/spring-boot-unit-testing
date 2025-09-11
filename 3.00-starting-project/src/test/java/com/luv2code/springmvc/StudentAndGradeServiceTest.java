package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
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
import java.util.Collection;
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

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @BeforeEach
    void setupDataBase() {
        jdbc.execute("INSERT INTO student(firstname, lastname, email_address) " +
                "VALUES ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");

        jdbc.execute("INSERT INTO math_grade(student_id, grade) VALUES (1, 100.00)");
        jdbc.execute("INSERT INTO science_grade(student_id, grade) VALUES (1, 100.00)");
        jdbc.execute("INSERT INTO history_grade(student_id, grade) VALUES (1, 100.00)");
    }

    @AfterEach
    void setupAfterTransaction() {
        // Clean up DB schemas
        jdbc.execute("DELETE FROM student");
        jdbc.execute("DELETE FROM math_grade");
        jdbc.execute("DELETE FROM science_grade");
        jdbc.execute("DELETE FROM history_grade");

        // Reset auto-incrementing ID
        jdbc.execute("ALTER TABLE student ALTER COLUMN id RESTART WITH 1");
        jdbc.execute("ALTER TABLE math_grade ALTER COLUMN id RESTART WITH 1");
        jdbc.execute("ALTER TABLE science_grade ALTER COLUMN id RESTART WITH 1");
        jdbc.execute("ALTER TABLE history_grade ALTER COLUMN id RESTART WITH 1");
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
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);
        Optional<ScienceGrade> scienceGrade = scienceGradesDao.findById(1);
        Optional<HistoryGrade> historyGrade = historyGradesDao.findById(1);

        assertTrue(student.isPresent(), "Student should be present");
        assertTrue(mathGrade.isPresent(), "Math grade should be present");
        assertTrue(scienceGrade.isPresent(), "Science grade should be present");
        assertTrue(historyGrade.isPresent(), "History grade should be present");

        studentService.deleteStudent(1);

        student = studentDao.findById(1);
        mathGrade = mathGradesDao.findById(1);
        scienceGrade = scienceGradesDao.findById(1);
        historyGrade = historyGradesDao.findById(1);

        assertFalse(student.isPresent(), "Student should not be present");
        assertFalse(mathGrade.isPresent(), "Math grade should not be present");
        assertFalse(scienceGrade.isPresent(), "Science grade should not be present");
        assertFalse(historyGrade.isPresent(), "History grade should not be present");
    }

    @Test
    void getGradeBookService() {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        List<CollegeStudent> studentList = new ArrayList<>();

        for (CollegeStudent student : collegeStudents) {
            studentList.add(student);
        }

        assertEquals(1, studentList.size(), "Should be 1 student in the grade book");
    }

    @Test
    @Sql("/insertData.sql")
    void getGradeBookServiceWithSql() {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        List<CollegeStudent> studentList = new ArrayList<>();

        for (CollegeStudent student : collegeStudents) {
            studentList.add(student);
        }

        assertEquals(5, studentList.size(), "Should be 5 students in the grade book");
    }

    @Test
    void createGradeService() {
        // Create the grade
        assertTrue(studentService.createGrade(80.5, 1, "math"));
        assertTrue(studentService.createGrade(80.5, 1, "science"));
        assertTrue(studentService.createGrade(80.5, 1, "history"));

        // Get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

        // Verify grade exists
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size(),
                "Student should have 2 math grades");
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size(),
                "Student should have 2 science grades");
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size(),
                "Student should have 2 history grades");
    }

    @Test
    void createGradeServiceReturnFalse() {
        assertFalse(studentService.createGrade(105.0, 1, "math"),
                "Grade is out of range");
        assertFalse(studentService.createGrade(-5.0, 1, "math"),
                "Grade is out of range");
        assertFalse(studentService.createGrade(50.0, 0, "history"),
                "Invalid student ID");
        assertFalse(studentService.createGrade(50.0, 1, "literature"),
                "Invalid grade type");
    }

    @Test
    void deleteGradeService() {
        assertEquals(1, studentService.deleteGrade(1, "math"),
                "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "science"),
                "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "history"),
                "Returns student id after delete");
    }

    @Test
    void deleteGradeServiceReturnZero() {
        assertEquals(0, studentService.deleteGrade(0, "math"),
                "Returns 0 with invalid student id");
        assertEquals(0, studentService.deleteGrade(1, "literature"),
                "Returns 0 with invalid grade type");
    }

    @Test
    void studentInformation() {
        GradebookCollegeStudent student = studentService.studentInformation(1);

        assertNotNull(student);
        assertEquals(1, student.getId(), "Student ID should be 1");
        assertEquals("Eric", student.getFirstname(), "First name should be Eric");
        assertEquals("Roby", student.getLastname(), "Last name should be Roby");
        assertEquals("eric.roby@luv2code_school.com", student.getEmailAddress());
        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());
        assertEquals(1, student.getStudentGrades().getScienceGradeResults().size());
        assertEquals(1, student.getStudentGrades().getHistoryGradeResults().size());
    }

    @Test
    void studentInformationReturnNull() {
        GradebookCollegeStudent student = studentService.studentInformation(0);

        assertNull(student);
    }
}

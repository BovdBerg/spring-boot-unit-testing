package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
class GradebookControllerTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

    @BeforeEach
    void beforeEach() {
        jdbc.execute("INSERT INTO student(firstname, lastname, email_address) " +
                "VALUES ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");
    }

    @AfterEach
    void afterEach() {
        jdbc.execute("DELETE FROM student");
        jdbc.execute("ALTER TABLE student ALTER COLUMN id RESTART WITH 1");  // Reset auto-incrementing ID
    }

    @Test
    void getStudentsHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby", "eric.roby@luv2code_school.com");
        CollegeStudent studentTwo = new GradebookCollegeStudent("Chad", "Darby", "chad.darby@luv2code_school.com");
        List<CollegeStudent> studentsList = List.of(studentOne, studentTwo);

        when(studentAndGradeServiceMock.getGradeBook()).thenReturn(studentsList);

        assertIterableEquals(studentsList, studentAndGradeServiceMock.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "index");
    }
}

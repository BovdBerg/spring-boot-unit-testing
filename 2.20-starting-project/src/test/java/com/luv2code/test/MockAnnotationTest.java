package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @MockitoBean  // instead of @Mock
    private ApplicationDao applicationDao;

    @Autowired  // instead of @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void beforeEach() {
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Roby");
        studentOne.setEmailAddress("eric.roby@luv2code_school.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("when & verify")
    @Test
    void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()))
                .thenReturn(100.00);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults());
        verify(applicationDao, times(1)).addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults());
    }

    @DisplayName("Find GPA")
    @Test
    void assertEqualsTestFindGPA() {
        when(applicationDao.findGradePointAverage(
                studentOne.getStudentGrades().getMathGradeResults()))
                .thenReturn(88.31);

        assertEquals(88.31, applicationService.findGradePointAverage(
                studentOne.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Not Null")
    @Test
    void testNotNull() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenReturn(true);

        assertNotNull(applicationService.checkNull(
                studentOne.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Throw runtime exception")
    @Test
    void testThrowRuntimeException() {
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));

        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple Stubbing")
    @Test
    void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

        String returnMsg = "Do not throw an exception for consecutive calls";
        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn(returnMsg);

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));
        assertEquals(returnMsg, applicationService.checkNull(nullStudent));
        assertEquals(returnMsg, applicationService.checkNull(nullStudent));

        verify(applicationDao, times(3)).checkNull(nullStudent);
    }
}

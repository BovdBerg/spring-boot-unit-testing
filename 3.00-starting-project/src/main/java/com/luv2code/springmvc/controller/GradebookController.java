package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    private static final String ERROR_VIEW = "error";
    private static final String INDEX_VIEW = "index";
    private static final String STUDENTS_VIEW = "students";
    private static final String STUDENT_INFORMATION_VIEW = "studentInformation";

    private final StudentAndGradeService studentAndGradeService;

    public GradebookController(StudentAndGradeService studentAndGradeService) {
        this.studentAndGradeService = studentAndGradeService;
    }

    @GetMapping(value = "/")
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        m.addAttribute(STUDENTS_VIEW, collegeStudents);
        return INDEX_VIEW;
    }

    @PostMapping("/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentAndGradeService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        m.addAttribute(STUDENTS_VIEW, collegeStudents);
        return INDEX_VIEW;
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model m) {
        if (!studentAndGradeService.checkIfStudentIsNull(id)) {
            return ERROR_VIEW;
        }

        studentAndGradeService.deleteStudent(id);
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        m.addAttribute(STUDENTS_VIEW, collegeStudents);
        return INDEX_VIEW;
    }

    @GetMapping("/studentInformation/{studentId}")
    public String studentInformation(@PathVariable int studentId, Model m) {
        if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
            return ERROR_VIEW;
        }

        studentAndGradeService.configureStudentInformationModel(studentId, m);

        return STUDENT_INFORMATION_VIEW;
    }

    @PostMapping("/grades")
    public String createGrade(@RequestParam("grade") double grade,
                              @RequestParam("gradeType") String gradeType,
                              @RequestParam("studentId") int studentId,
                              Model m) {

        if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
            return ERROR_VIEW;
        }

        boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);

        if (!success) {
            return ERROR_VIEW;
        }

        studentAndGradeService.configureStudentInformationModel(studentId, m);

        return STUDENT_INFORMATION_VIEW;
    }

    @DeleteMapping("/grades/{id}/{gradeType}")
    public String deleteGrade(@PathVariable int id, @PathVariable String gradeType, Model m) {
        int studentId = studentAndGradeService.deleteGrade(id, gradeType);

        if (studentId == 0) {
            return ERROR_VIEW;
        }

        studentAndGradeService.configureStudentInformationModel(studentId, m);

        return STUDENT_INFORMATION_VIEW;
    }
}

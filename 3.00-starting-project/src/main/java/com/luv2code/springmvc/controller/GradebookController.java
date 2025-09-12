package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

	private final Gradebook gradebook;

	private final StudentAndGradeService studentAndGradeService;

	public GradebookController(Gradebook gradebook, StudentAndGradeService studentAndGradeService) {
		this.gradebook = gradebook;
		this.studentAndGradeService = studentAndGradeService;
	}

	@GetMapping(value = "/")
	public String getStudents(Model m) {
		Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@PostMapping("/")
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
		studentAndGradeService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
		Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@GetMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable int id, Model m) {
		if (!studentAndGradeService.checkIfStudentIsNull(id)) {
			return "error";
		}

		studentAndGradeService.deleteStudent(id);
		Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@GetMapping("/studentInformation/{studentId}")
	public String studentInformation(@PathVariable int studentId, Model m) {
		if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
			return "error";
		}

		studentAndGradeService.configureStudentInformationModel(studentId, m);

		return "studentInformation";
	}

	@PostMapping("/grades")
	public String createGrade(@RequestParam("grade") double grade,
							  @RequestParam("gradeType") String gradeType,
							  @RequestParam("studentId") int studentId,
							  Model m) {

		if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
			return "error";
		}

		boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);

		if (!success) {
			return "error";
		}

		studentAndGradeService.configureStudentInformationModel(studentId, m);

		return "studentInformation";
	}
}

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

	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		if (!studentAndGradeService.checkIfStudentIsNull(id)) {
			return "error";
		}

		GradebookCollegeStudent student = studentAndGradeService.studentInformation(id);
		m.addAttribute("student", student);

		// Add grade averages to the model
		if (!student.getStudentGrades().getMathGradeResults().isEmpty()) {
			m.addAttribute("mathAverage", student.getStudentGrades().findGradePointAverage(
					student.getStudentGrades().getMathGradeResults())
			);
		} else {
			m.addAttribute("mathAverage", "N/A");
		}
		if (!student.getStudentGrades().getScienceGradeResults().isEmpty()) {
			m.addAttribute("scienceAverage", student.getStudentGrades().findGradePointAverage(
					student.getStudentGrades().getScienceGradeResults())
			);
		} else {
			m.addAttribute("scienceAverage", "N/A");
		}
		if (!student.getStudentGrades().getHistoryGradeResults().isEmpty()) {
			m.addAttribute("historyAverage", student.getStudentGrades().findGradePointAverage(
					student.getStudentGrades().getHistoryGradeResults())
			);
		} else {
			m.addAttribute("historyAverage", "N/A");
		}

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

		GradebookCollegeStudent student = studentAndGradeService.studentInformation(studentId);
		m.addAttribute("student", student);

		// Add grade averages to the model
		if (!student.getStudentGrades().getMathGradeResults().isEmpty()) {
			m.addAttribute("mathAverage", student.getStudentGrades().findGradePointAverage(
					student.getStudentGrades().getMathGradeResults())
			);
		} else {
			m.addAttribute("mathAverage", "N/A");
		}
		if (!student.getStudentGrades().getScienceGradeResults().isEmpty()) {
			m.addAttribute("scienceAverage", student.getStudentGrades().findGradePointAverage(
					student.getStudentGrades().getScienceGradeResults())
			);
		} else {
			m.addAttribute("scienceAverage", "N/A");
		}
		if (!student.getStudentGrades().getHistoryGradeResults().isEmpty()) {
			m.addAttribute("historyAverage", student.getStudentGrades().findGradePointAverage(
					student.getStudentGrades().getHistoryGradeResults())
			);
		} else {
			m.addAttribute("historyAverage", "N/A");
		}

		return "studentInformation";
	}
}

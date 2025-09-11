package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    private final StudentDao studentDao;

    private final MathGrade mathGrade;

    private final MathGradesDao mathGradesDao;

    public StudentAndGradeService(StudentDao studentDao, @Qualifier("mathGrades") MathGrade mathGrade, MathGradesDao mathGradesDao) {
        this.studentDao = studentDao;
        this.mathGrade = mathGrade;
        this.mathGradesDao = mathGradesDao;
    }

    public void createStudent(String firstName, String lastName, String email) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, email);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        return student.isPresent();
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        if (grade >= 0 && grade <= 100 && gradeType.equals("math")) {
            mathGrade.setId(0);
            mathGrade.setGrade(grade);
            mathGrade.setStudentId(studentId);
            mathGradesDao.save(mathGrade);
            return true;
        }

        return false;
    }
}

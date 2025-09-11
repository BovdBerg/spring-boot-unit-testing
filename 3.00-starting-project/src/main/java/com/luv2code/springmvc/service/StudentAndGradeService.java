package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
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

    private final ScienceGrade scienceGrade;

    private final ScienceGradesDao scienceGradesDao;

    private final HistoryGrade historyGrade;

    private final HistoryGradesDao historyGradesDao;

    public StudentAndGradeService(
            StudentDao studentDao,
            @Qualifier("mathGrades") MathGrade mathGrade, MathGradesDao mathGradesDao,
            @Qualifier("scienceGrades") ScienceGrade scienceGrade, ScienceGradesDao scienceGradesDao,
            @Qualifier("historyGrades") HistoryGrade historyGrade, HistoryGradesDao historyGradesDao
    ) {
        this.studentDao = studentDao;
        this.mathGrade = mathGrade;
        this.mathGradesDao = mathGradesDao;
        this.scienceGradesDao = scienceGradesDao;
        this.scienceGrade = scienceGrade;
        this.historyGrade = historyGrade;
        this.historyGradesDao = historyGradesDao;
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
            mathGradesDao.deleteByStudentId(id);
            scienceGradesDao.deleteByStudentId(id);
            historyGradesDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        if (grade >= 0 && grade <= 100) {
            switch (gradeType) {
                case "math" -> {
                    mathGrade.setId(0);
                    mathGrade.setGrade(grade);
                    mathGrade.setStudentId(studentId);
                    mathGradesDao.save(mathGrade);
                    return true;
                }
                case "science" -> {
                    scienceGrade.setId(0);
                    scienceGrade.setGrade(grade);
                    scienceGrade.setStudentId(studentId);
                    scienceGradesDao.save(scienceGrade);
                    return true;
                }
                case "history" -> {
                    historyGrade.setId(0);
                    historyGrade.setGrade(grade);
                    historyGrade.setStudentId(studentId);
                    historyGradesDao.save(historyGrade);
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }

        return false;
    }

    public int deleteGrade(int gradeId, String gradeType) {
        int studentId = 0;

        switch (gradeType) {
            case "math" -> {
                Optional<MathGrade> grade = mathGradesDao.findById(gradeId);
                if (grade.isEmpty()) {
                    return studentId;
                }

                studentId = grade.get().getStudentId();
                mathGradesDao.deleteById(gradeId);
            }
            case "science" -> {
                Optional<ScienceGrade> grade = scienceGradesDao.findById(gradeId);
                if (grade.isEmpty()) {
                    return studentId;
                }

                studentId = grade.get().getStudentId();
                scienceGradesDao.deleteById(gradeId);
            }
            case "history" -> {
                Optional<HistoryGrade> grade = historyGradesDao.findById(gradeId);
                if (grade.isEmpty()) {
                    return studentId;
                }

                studentId = grade.get().getStudentId();
                historyGradesDao.deleteById(gradeId);
            }
            default -> {
                // do nothing
            }
        }

        return studentId;
    }
}

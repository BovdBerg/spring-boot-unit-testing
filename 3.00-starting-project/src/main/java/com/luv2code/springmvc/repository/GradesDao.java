package com.luv2code.springmvc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GradesDao<T, I> extends CrudRepository<T, I> {
    Iterable<T> findGradeByStudentId(int studentId);

    void deleteByStudentId(int studentId);
}

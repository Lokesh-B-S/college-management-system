package com.ras.cms.service;

import com.ras.cms.domain.Student;

import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
public interface StudentService {
    List<Student> findAll();

    Student findOne(Long id);

    Student saveStudent(Student student);

    void deleteStudent(Long id);
}

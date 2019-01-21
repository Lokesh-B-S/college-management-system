package com.ras.cms.service.subject;

import com.ras.cms.domain.Subject;

import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
public interface SubjectService {
    List<Subject> findAll();

    Subject findOne(Long id);

    Subject saveSubject(Subject student);

    void deleteSubject(Long id);
}

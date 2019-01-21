package com.ras.cms.service.subject;

import com.ras.cms.dao.SubjectRepository;
import com.ras.cms.domain.Student;
import com.ras.cms.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Surya on 13-Jun-18.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findOne(Long id) {
        return subjectRepository.findById(id).get();
    }

    @Override
    public Subject saveSubject(Subject student) {
        return subjectRepository.save(student);
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}

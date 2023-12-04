package com.ras.cms.service.subjecttype;

import com.ras.cms.repository.SubjectTypeRepo;
import com.ras.cms.domain.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {

    @Autowired
    SubjectTypeRepo subjectTypeRepo;

    @Override
    public List<SubjectType> findAll() {
        return subjectTypeRepo.findAll();
    }

    @Override
    public SubjectType findOne(Long id) {
        return subjectTypeRepo.findById(id).get();
    }

    @Override
    public SubjectType saveSubjectType(SubjectType address) {
        return subjectTypeRepo.save(address);
    }

    @Override
    public void deleteSubjectType(Long id) {
        subjectTypeRepo.deleteById(id);
    }
}
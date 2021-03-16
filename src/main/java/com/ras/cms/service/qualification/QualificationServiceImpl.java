package com.ras.cms.service.qualification;

import com.ras.cms.dao.QualificationRepository;
import com.ras.cms.domain.Qualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualificationServiceImpl implements QualificationService {

    @Autowired
    QualificationRepository qualificationRepository;

    @Override
    public List<Qualification> findAll() {
        return qualificationRepository.findAll();
    }

    @Override
    public Qualification findOne(Long id) {
        return qualificationRepository.findById(id).get();
    }

    @Override
    public Qualification saveQualification(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    @Override
    public void deleteQualification(Long id) {
        qualificationRepository.deleteById(id);
    }

    @Override
    public void deleteQualifications(List<Long> ids) {
        ids.forEach(this::deleteQualification);
    }
}
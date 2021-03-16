package com.ras.cms.service.qualification;

import com.ras.cms.domain.Qualification;

import java.util.List;

public interface QualificationService {
    List<Qualification> findAll();
    Qualification findOne(Long id);
    Qualification saveQualification(Qualification collage);
    void deleteQualification(Long id);
    void deleteQualifications(List<Long> ids);
}

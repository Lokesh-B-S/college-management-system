package com.ras.cms.service.subjecttype;

import com.ras.cms.domain.SubjectType;

import java.util.List;

public interface SubjectTypeService {
    List<SubjectType> findAll();
    SubjectType findOne(Long subjectTypeId);
    SubjectType saveSubjectType(SubjectType subjectType);
    void deleteSubjectType(Long subjectTypeId);
}

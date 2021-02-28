package com.ras.cms.service.college;

import com.ras.cms.domain.College;

import java.util.List;

public interface CollegeService {
    List<College> findAll();
    College findOne(Long id);
    College saveCollege(College College);
    void deleteCollege(Long id);
}

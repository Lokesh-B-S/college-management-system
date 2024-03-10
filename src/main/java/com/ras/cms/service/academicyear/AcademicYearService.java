package com.ras.cms.service.academicyear;

import com.ras.cms.domain.AcademicYear;
import com.ras.cms.domain.Batch;
import com.ras.cms.domain.Department;

import java.util.List;

public interface AcademicYearService {

    List<AcademicYear> findAll();

    AcademicYear findOne(Long id);
    void saveAcademicYear(AcademicYear academicYear);

    void deleteAcademicYear(Long id);


   // List<AcademicYear> getAcademicYearsByDepartment(Department department);


}

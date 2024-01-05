package com.ras.cms.service.semester;

import com.ras.cms.domain.Semester;

import java.util.List;

public interface SemesterService {

    List<Semester> findAll();

    Semester findOne(Long semId);

    Semester saveSemester(Semester semester);


}

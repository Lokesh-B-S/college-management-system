package com.ras.cms.service.coursefaculty;

import com.ras.cms.domain.*;

import java.util.List;

public interface CourseFacultyService {


    List<CourseFaculty> findAll();

    CourseFaculty findOne(Long id);
    CourseFaculty saveCourseFaculty(CourseFaculty courseFaculty);

    void deleteCourseFaculty(Long id);

    List<CourseFaculty> getAllotmentsByDepartmentAndSemesterAndSection(Department department, Semester semester, Section section);
}

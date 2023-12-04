package com.ras.cms.repository;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.CourseFaculty;
import com.ras.cms.domain.Section;
import com.ras.cms.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseFacultyRepository extends JpaRepository<CourseFaculty, Long> {

    List<CourseFaculty> findAllByDepartmentAndSemesterAndSection(Department department, Semester semester, Section section);
}

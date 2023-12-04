package com.ras.cms.repository;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentSemSec;
import com.ras.cms.domain.Section;
import com.ras.cms.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentSemSecRepository extends JpaRepository<DepartmentSemSec, Long> {

    boolean existsByDepartmentAndSemesterAndSection(Department department, Semester semester, Section section);

    Long findIdByDepartmentAndSemesterAndSection(Department department, Semester semester, Section section);

    DepartmentSemSec findByDepartmentAndSemesterAndSection(Department department, Semester semester, Section section);

    Department findDepartmentByDepartmentSemSecId(Long departmentSemSecId);

}

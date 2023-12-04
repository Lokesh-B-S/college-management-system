package com.ras.cms.repository;

import com.ras.cms.domain.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {

   // List<AcademicYear> findAllByDepartment(Department department);

   // AcademicYear findByAcademicYear(String academicYear);

}

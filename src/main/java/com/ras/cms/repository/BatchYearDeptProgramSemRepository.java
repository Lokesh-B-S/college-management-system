package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchYearDeptProgramSemRepository extends JpaRepository<BatchYearDeptProgramSem, Long> {

    boolean existsByBatchAndAcademicYearAndDepartmentAndProgramAndSemester(Batch batch, AcademicYear academicYear, Department department, Program program, Semester semester);

    Long findIdByBatchAndAcademicYearAndDepartmentAndProgramAndSemester(Batch batch, AcademicYear academicYear, Department department, Program program, Semester semester);

    BatchYearDeptProgramSem findByBatchAndAcademicYearAndDepartmentAndProgramAndSemester(Batch batch, AcademicYear academicYear, Department department, Program program, Semester semester);

    //Department findDepartmentByDepartmentSemSecId(Long departmentSemSecId);
}

package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchYearSemTermRepository extends JpaRepository<BatchYearSemTerm, Long> {


    boolean existsByBatchAndAcademicYearAndSemesterAndTerm(Batch batch, AcademicYear academicYear, Semester semester, Term term);

    Long findIdByBatchAndAcademicYearAndSemesterAndTerm(Batch batch, AcademicYear academicYear, Semester semester, Term term);

    BatchYearSemTerm findByBatchAndAcademicYearAndSemesterAndTerm(Batch batch, AcademicYear academicYear, Semester semester, Term term);

    BatchYearSemTerm findByAcademicYearAndSemesterAndTerm(AcademicYear academicYear, Semester semester, Term term);
}

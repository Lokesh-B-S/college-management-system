package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecBatchRepository extends JpaRepository<SecBatch, Long> {

    List<SecBatch> findAllByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term);
    List<SecBatch> findAllByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);
    boolean existsBySecBatchNameAndAcademicYearAndProgramAndSemesterAndTermAndSection(String batchName, AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

   SecBatch findBySecBatchNameAndAcademicYearAndProgramAndSemesterAndTermAndSection(String batchName, AcademicYear academicYear, Program program, Semester semester, Term term, Section section);
}
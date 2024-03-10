package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SecBatchCountRepository extends JpaRepository<secBatchCount, Long> {


  List<secBatchCount> findAllByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program,Semester semester, Term term);

 secBatchCount findByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program,Semester semester, Term term, Section section);

//    Integer findBatchCountByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

}

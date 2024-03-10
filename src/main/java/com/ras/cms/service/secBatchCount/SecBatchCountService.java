package com.ras.cms.service.secBatchCount;

import com.ras.cms.domain.*;

import java.util.List;

public interface SecBatchCountService {


  List<secBatchCount> getBatchCountRowsByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term);

//  Integer getCountByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

   secBatchCount getSecBatchCountRowByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

  void updateOrSaveBatchCount(AcademicYear academicYear, Program program, Semester semester, Term term,Section section,Integer batchCount);

}

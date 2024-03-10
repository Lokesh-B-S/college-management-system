package com.ras.cms.service.secbatch;

import com.ras.cms.domain.*;

import java.util.List;

public interface SecBatchService {

    SecBatch findOne(Long secBatchId);

    List<SecBatch> findAll();

    SecBatch saveSecBatch(SecBatch secBatch);

    void createSecBatch(String batchName, AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

    List<SecBatch> getBatchesByAcademicYearAndProgramAndSemesterAndTermAndSection( AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

    List<SecBatch> getBatchesByAcademicYearAndProgramAndSemesterAndTerm( AcademicYear academicYear, Program program, Semester semester, Term term);

    SecBatch findOrCreateDefaultBatch(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

    }

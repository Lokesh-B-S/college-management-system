package com.ras.cms.service.newTimeTable;

import com.ras.cms.domain.*;

import java.util.List;

public interface NewTimeTableService {

    List<NewTimeTableEntry> getTimeTableEntriesByAcademicYearAndProgramAndSemesterAndSectionAndTermAndDay(AcademicYear academicYear, Program program, Semester semester, Section section, Term term, Day day);

}

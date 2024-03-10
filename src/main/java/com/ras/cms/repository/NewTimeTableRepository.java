package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewTimeTableRepository extends JpaRepository<NewTimeTableEntry, Long> {

    List<NewTimeTableEntry> findAllByAcademicYearAndProgramAndSemesterAndSectionAndTermAndDay(AcademicYear academicYear, Program program, Semester semester, Section section, Term term, Day day);

}

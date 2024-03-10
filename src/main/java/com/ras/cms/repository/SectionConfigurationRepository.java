package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionConfigurationRepository extends JpaRepository<SectionConfiguration, Long> {

  // SectionConfiguration findByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term);

   Optional<SectionConfiguration> findByAcademicYearAndProgramAndSemesterAndTerm(
           AcademicYear academicYear,
           Program program,
           Semester semester,
           Term term
   );
}

package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectTeamConfigurationRepository extends JpaRepository<ProjectTeamConfiguration, Long> {

    Optional<ProjectTeamConfiguration> findByAcademicYearAndProgramAndSemesterAndTerm(
            AcademicYear academicYear,
            Program program,
            Semester semester,
            Term term
    );


    Optional<ProjectTeamConfiguration> findByAcademicYearAndSemesterAndTerm(
            AcademicYear academicYear,
            Semester semester,
            Term term
    );
}

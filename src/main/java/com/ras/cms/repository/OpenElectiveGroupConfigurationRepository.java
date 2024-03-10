package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpenElectiveGroupConfigurationRepository extends JpaRepository<OpenElectiveGroupConfiguration, Long> {

    Optional<OpenElectiveGroupConfiguration> findByAcademicYearAndProgramAndSemesterAndTerm(
            AcademicYear academicYear,
            Program program,
            Semester semester,
            Term term
    );
}

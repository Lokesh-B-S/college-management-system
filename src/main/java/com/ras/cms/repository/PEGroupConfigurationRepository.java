package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PEGroupConfigurationRepository extends JpaRepository<PEGroupConfiguration, Long> {


    //for forming same programs elective group
    Optional<PEGroupConfiguration> findByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(
            AcademicYear academicYear,
            Program program,
            Semester semester,
            Term term,
            Course professionalElective
    );


    //for displaying group numbers irrespective of programs
    Optional<PEGroupConfiguration> findByAcademicYearAndSemesterAndTermAndProfessionalElective(
            AcademicYear academicYear,
            Semester semester,
            Term term,
            Course professionalElective
    );
}

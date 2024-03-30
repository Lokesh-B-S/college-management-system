package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupConfigurationRepository extends JpaRepository<GroupConfiguration, Long> {

    Optional<GroupConfiguration> findByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(
            AcademicYear academicYear,
            Program program,
            Semester semester,
            Term term,
            OpenElective openElective
    );

}

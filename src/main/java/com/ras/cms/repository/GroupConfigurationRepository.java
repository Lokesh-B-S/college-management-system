package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupConfigurationRepository extends JpaRepository<GroupConfiguration, Long> {

    //for forming same programs open elective group
    Optional<GroupConfiguration> findByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(
            AcademicYear academicYear,
            Program program,
            Semester semester,
            Term term,
            OpenElective openElective
    );


    //for displaying group numbers irrespective of programs
    Optional<GroupConfiguration> findByAcademicYearAndSemesterAndTermAndOpenElective(
            AcademicYear academicYear,
            Semester semester,
            Term term,
            OpenElective openElective
    );

}

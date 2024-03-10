package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.apache.el.lang.ELArithmetic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EligibleStudentRepository extends JpaRepository<EligibleStudent, Long> {

    boolean existsByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear,Program program,Semester semester,Term term);

    List<EligibleStudent> findAllByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term);

    boolean existsByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear,Program program,Semester semester,Term term, Section section);

    List<EligibleStudent> findAllByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

    List<EligibleStudent> findAllByAcademicYearAndProgramAndSemesterAndTermAndSectionAndSecBatch(AcademicYear academicYear, Program program, Semester semester, Term term, Section section, SecBatch secBatch);
    List<EligibleStudent> findAllBySection(Section section);

    EligibleStudent findByUsn(String usn);

    @Query("SELECT es.eligibleStudentId FROM EligibleStudent es " +
            "WHERE es.academicYear = :academicYear " +
            "AND es.program = :program " +
            "AND es.semester = :semester " +
            "AND es.term = :term " +
            "AND es.section IS NOT NULL " +
            "AND es.section.id > CAST(:noOfSections AS java.lang.Long)")
    List<Long> findStudentsToUnassign(
            @Param("academicYear") AcademicYear academicYear,
            @Param("program") Program program,
            @Param("semester") Semester semester,
            @Param("term") Term term,
            @Param("noOfSections") Integer noOfSections
    );



    @Query("SELECT e FROM EligibleStudent e " +
            "WHERE e.academicYear = ?1 " +
            "AND e.program = ?2 " +
            "AND e.semester = ?3 " +
            "AND e.term = ?4 " +
            "AND (e.professionalElective1 = ?5 OR e.professionalElective2 = ?5 OR e.professionalElective3 = ?5 OR e.professionalElective4 = ?5)")
    List<EligibleStudent> findStudentsByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(
            AcademicYear academicYear,
            Program program,
            Semester semester,
            Term term,
            Course PEcourse
    );
   List<EligibleStudent> findAllByAcademicYearAndProgramAndSemesterAndTermAndSectionAndSecBatchIsNull(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

   List<EligibleStudent> findAllByAcademicYearAndSemesterAndTermAndOpenElective(AcademicYear academicYear, Semester semester, Term term, OpenElective openElective);

    List<EligibleStudent> findAllByUsn(String usn);

//    List<AcademicYear> findAcademicYearsByStudentDetails

}

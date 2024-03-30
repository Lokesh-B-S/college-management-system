package com.ras.cms.service.eligibleStudents;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface EligibleStudentService{

    List<EligibleStudent> findAll();

    EligibleStudent findOne(Long id);

    EligibleStudent saveEligibleStudent(EligibleStudent eligibleStudent);

    void deleteEligibleStudent(Long id);

    EligibleStudent getStudentByUsn(String usn);

    boolean existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term);

    boolean existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

    List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term);

    List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);

    List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndAndSectionAndSecBatch(AcademicYear academicYear, Program program, Semester semester, Term term, Section section, SecBatch secBatch);

    List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndGroupOfOpenElective(AcademicYear academicYear, Program program, Semester semester, Term term, OEGroup groupOfOpenElective);

//   Course getProfessionalElective1ByEligibleStudentId(Long eligibleStudentId);
//    Course getProfessionalElective2ByEligibleStudentId(Long eligibleStudentId);
//    OpenElective getOpenElectiveByEligibleStudentId(Long eligibleStudentId);

    void assignSectionToEligibleStudent(Long studentId, Long sectionId);

    void assignSecBatchToEligibleStudent(Long studentId, Long secBatchId);

    void assignOEGroupToEligibleStudent(Long studentId, Long groupId);

    void assignOpenElectiveToEligibleStudent(Long studentId, Long openElectiveId);

    void assignProfessionalElective1ToEligibleStudent(Long studentId, Long courseId);

    void assignProfessionalElective2ToEligibleStudent(Long studentId, Long courseId);

   List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(AcademicYear academicYear, Program program, Semester semester, Term term, Course PEcourse);

   List<EligibleStudent> getStudentsByAcademicYearAndAndSemesterAndTermAndOpenElective(AcademicYear academicYear, Semester semester, Term term, OpenElective openElective);
    List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(AcademicYear academicYear, Program program, Semester semester, Term term, OpenElective openElective);


    List<EligibleStudent> getStudentsBySection(Section section);

//    void unassignSectionFromEligibleStudent(Long studentId, Long sectionId);

    List<Long> findStudentsToUnassign(AcademicYear academicYear, Program program, Semester semester, Term term, Integer noOfSections);

    @Transactional
    void unassignSectionFromEligibleStudent(Long studentId);


//    List<AcademicYear> findAcademicYearsByStudentDetails(String usn, String name);

    List<EligibleStudent> findAllIdsByUsn(String usn);

    List<EligibleStudent> getUnassignedStudents(AcademicYear academicYear, Program program, Semester semester, Term term, Section section);


    }

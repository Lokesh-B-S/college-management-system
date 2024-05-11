package com.ras.cms.service.eligibleStudents;

import com.ras.cms.domain.*;
import com.ras.cms.repository.*;
import com.ras.cms.service.secbatch.SecBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EligibleStudentServiceImpl implements EligibleStudentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EligibleStudentRepository eligibleStudentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SecBatchRepository secBatchRepository;

    @Autowired
    private OEGroupRepository groupRepository;

    @Autowired
    private OpenElectiveRepository openElectiveRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProjectTeamRepository projectTeamRepository;

    @Autowired
    private SecBatchService secBatchService;

    @Override
    public List<EligibleStudent> findAll() {
        return eligibleStudentRepository.findAll();
    }

    @Override
    public EligibleStudent findOne(Long id) {
        return eligibleStudentRepository.findById(id).get();
    }

    @Override
    public EligibleStudent getStudentByUsn(String usn){
        return eligibleStudentRepository.findByUsn(usn);
    }
    @Override
    public EligibleStudent saveEligibleStudent(EligibleStudent eligibleStudent) {
        return eligibleStudentRepository.save(eligibleStudent);
    }

    @Override
    public void deleteEligibleStudent(Long id) {
        eligibleStudentRepository.deleteById(id);
    }

    @Override
    public boolean existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term) {
        return eligibleStudentRepository.existsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
    }

    @Override
    public boolean existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section) {
        return eligibleStudentRepository.existsByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term){
        return eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section){
        return eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndProjectTeam(AcademicYear academicYear, Program program, Semester semester, Term term, ProjectTeam projectTeam){
        return eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndProjectTeam(academicYear, program, semester, term, projectTeam);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndAndSectionAndSecBatch(AcademicYear academicYear, Program program, Semester semester, Term term, Section section, SecBatch secBatch){
        return eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndSectionAndSecBatch(academicYear, program, semester, term, section, secBatch);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndGroupOfOpenElective(AcademicYear academicYear, Program program, Semester semester, Term term, OEGroup GroupOfOpenElective){
        return eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndGroupOfOpenElective(academicYear, program, semester, term, GroupOfOpenElective);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(AcademicYear academicYear, Program program, Semester semester, Term term, Course PEcourse){
        return eligibleStudentRepository.findStudentsByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(academicYear, program, semester, term, PEcourse);
    }
    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndAndSemesterAndTermAndOpenElective(AcademicYear academicYear, Semester semester, Term term, OpenElective openElective){
        return eligibleStudentRepository.findAllByAcademicYearAndSemesterAndTermAndOpenElective(academicYear, semester, term, openElective);
    }

    @Override
    public List<EligibleStudent> getStudentsByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(AcademicYear academicYear, Program program, Semester semester, Term term, OpenElective openElective){
        return eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(academicYear, program, semester, term, openElective);
    }
    @Override
    public List<EligibleStudent> getStudentsBySection(Section section){
        return eligibleStudentRepository.findAllBySection(section);
    };

//    @Override
//    public List<AcademicYear> findAcademicYearsByStudentDetails(String usn, String name) {
//        return eligibleStudentRepository.findAcademicYearsByStudentDetails(usn, name);
//    }


    @Override
    public List<EligibleStudent> findAllIdsByUsn(String usn) {
        return eligibleStudentRepository.findAllByUsn(usn);
    }





    public void assignSectionToEligibleStudent(Long studentId, Long sectionId) {
        // Fetch the student and section from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section ID: " + sectionId));

        // Assign the section to the student
        student.setSection(section);
        eligibleStudentRepository.save(student);
    }

    public void assignProjectTeamToEligibleStudent(Long studentId, Long teamId) {
        // Fetch the student and team from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        ProjectTeam projectTeam = projectTeamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project team ID: " + teamId));

        // Assign the section to the student
        student.setProjectTeam(projectTeam);
        eligibleStudentRepository.save(student);
    }

    public void assignOEGroupToEligibleStudent(Long studentId, Long groupId) {
        // Fetch the student and section from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        OEGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section ID: " + groupId));

        // Assign the section to the student
        student.setGroupOfOpenElective(group);
        eligibleStudentRepository.save(student);
    }

    public List<Long> findStudentsToUnassign(AcademicYear academicYear, Program program, Semester semester, Term term, Integer noOfSections) {
        // Query to find students assigned to sections beyond the new noOfSections
        // Adjust the entity and repository names based on your actual setup
        List<Long> studentIdsToUnassign = eligibleStudentRepository.findStudentsToUnassign(academicYear, program, semester, term, noOfSections);
        return studentIdsToUnassign;
    }

    @Transactional
    public void unassignSectionFromEligibleStudent(Long studentId) {
        // Retrieve the EligibleStudent entity by studentId
        EligibleStudent eligibleStudent = eligibleStudentRepository.findById(studentId).orElse(null);

        // Check if the EligibleStudent entity exists
        if (eligibleStudent != null) {
            System.out.println("Before unassigning: " + eligibleStudent);

            try {
                // Unassign the section from the EligibleStudent
                eligibleStudent.setSection(null);

                // Save the changes to the database
                eligibleStudentRepository.save(eligibleStudent);
            }
            catch(Exception e){
                System.out.println("the error is"+ e);
            }

            // Explicitly fetch the entity again for verification
            EligibleStudent updatedStudent = eligibleStudentRepository.findById(eligibleStudent.getEligibleStudentId()).orElse(null);

            System.out.println("After unassigning: " + updatedStudent);
        }
        else{
            System.out.println("null for : " + eligibleStudent);

        }
    }


//    public void unassignSectionFromEligibleStudent(Long studentId, Long sectionId) {
//        Optional<EligibleStudent> optionalStudent = eligibleStudentRepository.findById(studentId);
//
//        if (optionalStudent.isPresent()) {
//            EligibleStudent student = optionalStudent.get();
//
//            // Check if the student is currently assigned to the specified section
//            if (Objects.equals(student.getSection().getSectionId(), sectionId)) {
//                // Unassign the student from the section
//                student.setSection(null);  // Assuming 'section' is the field representing the assigned section
//
//                // Save the updated student entity to the database
//                eligibleStudentRepository.save(student);
//            }
//            // If the student is not assigned to the specified section, you might want to log a message or handle it accordingly
//        }
//        // If the student with the given ID is not found, you might want to log a message or handle it accordingly
//    }
    public void assignSecBatchToEligibleStudent(Long studentId, Long secBatchId) {
        // Fetch the student and section from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        SecBatch secBatch = secBatchRepository.findById(secBatchId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid secBatch ID: " + secBatchId));

        // Assign the section to the student
        student.setSecBatch(secBatch);
        eligibleStudentRepository.save(student);
    }


    public List<EligibleStudent> getUnassignedStudents(AcademicYear academicYear, Program program, Semester semester, Term term, Section section) {
        // Retrieve a list of students who are not assigned to any secBatch in the specified academicYear, program, semester, term, and section
        List<EligibleStudent> unassignedStudents = eligibleStudentRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndSectionAndSecBatchIsNull(
                academicYear, program, semester, term, section);

//        // Assign the unassigned students to the default batch
//        SecBatch defaultBatch = secBatchService.findOrCreateDefaultBatch(academicYear, program, semester, term, section);
//
//        for (EligibleStudent student : unassignedStudents) {
//            student.setSecBatch(defaultBatch);
//            // Update the student in the database
//            eligibleStudentRepository.save(student);
//        }

        return unassignedStudents;
    }


    public void assignOpenElectiveToEligibleStudent(Long studentId, Long openElectiveId) {
        // Fetch the student and section from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        OpenElective openElective = openElectiveRepository.findById(openElectiveId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid openElective ID: " + openElectiveId));

        // Assign the section to the student
        student.setOpenElective(openElective);
        eligibleStudentRepository.save(student);
    }

    public void assignProfessionalElective1ToEligibleStudent(Long studentId, Long courseId) {
        // Fetch the student and section from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        Course professionalElective1 = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid professional elective 1 course ID: " + courseId));

        // Assign the section to the student
        student.setProfessionalElective1(professionalElective1);
        eligibleStudentRepository.save(student);
    }

    public void assignProfessionalElective2ToEligibleStudent(Long studentId, Long courseId) {
        // Fetch the student and section from the database
        EligibleStudent student = eligibleStudentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        Course professionalElective2 = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid professional elective 2 course ID: " + courseId));

        // Assign the section to the student
        student.setProfessionalElective2(professionalElective2);
        eligibleStudentRepository.save(student);
    }

}

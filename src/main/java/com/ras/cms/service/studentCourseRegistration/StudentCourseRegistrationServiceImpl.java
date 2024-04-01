package com.ras.cms.service.studentCourseRegistration;

import com.ras.cms.domain.*;
import com.ras.cms.repository.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;


import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentCourseRegistrationServiceImpl implements StudentCourseRegistrationService{


    @Autowired
    private EntityManager entityManager;

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentCourseRegistrationRepository studentCourseRegistrationRepository;


    @Override
    public StudentCourseRegistration findOne(Long id) {
        return studentCourseRegistrationRepository.findById(id).get();
    }

    @Override
    public StudentCourseRegistration findRegistrationByStudentAndCourse(EligibleStudent eligibleStudent, Course course){
        return studentCourseRegistrationRepository.findByEligibleStudentAndCourse(eligibleStudent, course);
    }


    @Override
    public StudentCourseRegistration findRegistrationByEligibleStudentAndOpenElectiveAndCurrentAcademicYearAndCurrentProgramAndCurrentSemester(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear academicYear, Program program, Semester semester){
        return studentCourseRegistrationRepository.findByEligibleStudentAndOpenElectiveAndCurrentAcademicYearAndCurrentProgramAndCurrentSemester(eligibleStudent, openElective, academicYear, program, semester);
    }

    @Override
    public StudentCourseRegistration findRegistrationByEligibleStudentAndOpenElectiveAndCurrentAcademicYearAndCurrentSemester(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear academicYear, Semester semester){
        return studentCourseRegistrationRepository.findByEligibleStudentAndOpenElectiveAndCurrentAcademicYearAndCurrentSemester(eligibleStudent, openElective, academicYear, semester);
    }


    @Override
    public List<StudentCourseRegistration> findAllRegistrationsByCourse(Course course, AcademicYear academicYear, Semester semester){
        return studentCourseRegistrationRepository.findAllByCourseAndCurrentAcademicYearAndCurrentSemester(course, academicYear, semester);
    }


    @Override
    public List<StudentCourseRegistration> findAllRegistrationsByOpenElective(OpenElective openElective, AcademicYear academicYear, Semester semester){
        return studentCourseRegistrationRepository.findAllByOpenElectiveAndCurrentAcademicYearAndCurrentSemester(openElective, academicYear, semester);
    }

    @Override
    public List<StudentCourseRegistration> findAllRegistrationsByOpenElectiveAndAcademicYearAndSemesterAndGroupOfOpenElective(OpenElective openElective, AcademicYear academicYear, Semester semester, OEGroup groupOfOpenElective){
        return studentCourseRegistrationRepository.findAllByOpenElectiveAndCurrentAcademicYearAndCurrentSemesterAndGroupOfOpenElective(openElective, academicYear, semester, groupOfOpenElective);
    }

    @Override
    public List<StudentCourseRegistration> findAllRegistrationsByProgramAndAcademicYearAndSemester(Program program, AcademicYear academicYear, Semester semester){
        return studentCourseRegistrationRepository.findAllByCurrentProgramAndCurrentAcademicYearAndCurrentSemester(program, academicYear, semester);
    }


    @Override
    public StudentCourseRegistration findRegistrationByStudentAndOpenElective(EligibleStudent eligibleStudent, OpenElective openElective){
        return studentCourseRegistrationRepository.findByEligibleStudentAndOpenElective(eligibleStudent, openElective);
    }


    @Override
    public void deleteRegisteredCourseFromList(Long id) {
        studentCourseRegistrationRepository.deleteById(id);
    }


    public void registerRegularCourseToStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm) {
        System.out.println("Registering course for student: " + eligibleStudent.getEligibleStudentId() + ", Course ID: " + course.getCourseId());


        // Check if the student is already registered for the course
        StudentCourseRegistration existingRegistration = studentCourseRegistrationRepository.findByEligibleStudentAndCourse(eligibleStudent, course);
        System.out.println("Existing registration: " + existingRegistration);

        if (existingRegistration == null) {
            // Create a new registration only if it doesn't exist
            StudentCourseRegistration registration = new StudentCourseRegistration();
            registration.setEligibleStudent(eligibleStudent);
            registration.setCourse(course);
            registration.setCourseType("regular");
            registration.setCourseYear(courseYear);
            registration.setCourseSemester(courseSemester);

            registration.setCurrentProgram(currentProgram);
            registration.setCurrentAcademicYear(currentAcademicYear);
            registration.setCurrentSemester(currentSemester);
            registration.setCurrentTerm(currentTerm);
//            entityManager.persist(registration);

            studentCourseRegistrationRepository.save(registration);
            System.out.println("New registration created.");

        } else {
            System.out.println("Student is already registered for the course.");


        }
    }

    @Transactional
    public void removeCourseFromStudent(EligibleStudent eligibleStudent, Course course) {
        // Find the existing registration for the student and course
        StudentCourseRegistration existingRegistration = studentCourseRegistrationRepository.findByEligibleStudentAndCourse(eligibleStudent, course);

        // If the registration exists, delete it
        if (existingRegistration != null) {
            studentCourseRegistrationRepository.delete(existingRegistration);
        }
    }

    public List<StudentCourseRegistration> findRegistrationsByStudent(EligibleStudent eligibleStudent){
        return studentCourseRegistrationRepository.findAllByEligibleStudent(eligibleStudent);
    }

    public List<StudentCourseRegistration> findRegistrationsByStudentAndCourseType(EligibleStudent eligibleStudent, String courseType){
        return studentCourseRegistrationRepository.findAllByEligibleStudentAndCourseType(eligibleStudent, courseType);
    }



    public void assignProfessionalElective1ToEligibleStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm) {

        StudentCourseRegistration existingRegistration = studentCourseRegistrationRepository.findByEligibleStudentAndCourseTypeAndCourseYearAndCourseSemester(eligibleStudent, "pe1", courseYear, courseSemester);
        if(existingRegistration != null){
            existingRegistration.setCourse(course);
            studentCourseRegistrationRepository.save(existingRegistration);
        }
        else{
            StudentCourseRegistration registration = new StudentCourseRegistration();
            registration.setEligibleStudent(eligibleStudent);
            registration.setCourse(course);
            registration.setCourseType("pe1");
            registration.setCourseYear(courseYear);
            registration.setCourseSemester(courseSemester);
            registration.setCurrentProgram(currentProgram);
            registration.setCurrentAcademicYear(currentAcademicYear);
            registration.setCurrentSemester(currentSemester);
            registration.setCurrentTerm(currentTerm);

            studentCourseRegistrationRepository.save(registration);
        }
    }

    public void assignProfessionalElective2ToEligibleStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm) {

        StudentCourseRegistration existingRegistration = studentCourseRegistrationRepository.findByEligibleStudentAndCourseTypeAndCourseYearAndCourseSemester(eligibleStudent, "pe2", courseYear, courseSemester);
        if(existingRegistration != null){
            existingRegistration.setCourse(course);
            studentCourseRegistrationRepository.save(existingRegistration);
        }
        else{
            StudentCourseRegistration registration = new StudentCourseRegistration();
            registration.setEligibleStudent(eligibleStudent);
            registration.setCourse(course);
            registration.setCourseType("pe2");
            registration.setCourseYear(courseYear);
            registration.setCourseSemester(courseSemester);
            registration.setCurrentProgram(currentProgram);
            registration.setCurrentAcademicYear(currentAcademicYear);
            registration.setCurrentSemester(currentSemester);
            registration.setCurrentTerm(currentTerm);
//            entityManager.persist(registration);
            studentCourseRegistrationRepository.save(registration);
        }
    }

    public void assignOpenElectiveToEligibleStudent(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm) {


        StudentCourseRegistration existingRegistration = studentCourseRegistrationRepository.findByEligibleStudentAndCourseTypeAndCourseYearAndCourseSemester(eligibleStudent, "oe", courseYear, courseSemester);
        if(existingRegistration != null){
            existingRegistration.setOpenElective(openElective);
            studentCourseRegistrationRepository.save(existingRegistration);
        }
        else{
            StudentCourseRegistration registration = new StudentCourseRegistration();
            registration.setEligibleStudent(eligibleStudent);
            registration.setOpenElective(openElective);
            registration.setCourseType("oe");
            registration.setCourseYear(courseYear);
            registration.setCourseSemester(courseSemester);
            registration.setCurrentProgram(currentProgram);
            registration.setCurrentAcademicYear(currentAcademicYear);
            registration.setCurrentSemester(currentSemester);
            registration.setCurrentTerm(currentTerm);
//            entityManager.persist(registration);
            studentCourseRegistrationRepository.save(registration);
        }
    }


    //earlier was in eligiblestudent, but since subjectwise the group id should be alloted , it must be here
    public void assignOEGroupToStudent(Long studentCourseRegistrationId, Long groupId) {
        // Fetch the student and section from the database
        StudentCourseRegistration studentCourseRegistration = studentCourseRegistrationRepository.findById(studentCourseRegistrationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid registration ID: " + studentCourseRegistrationId));
        OEGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group ID: " + groupId));

//        if (studentCourseRegistration.getGroupOfOpenElective() == null) {
//            studentCourseRegistration.setGroupOfOpenElective(new OEGroup());
//        }
        // Assign the section to the student
        studentCourseRegistration.setGroupOfOpenElective(group);
        studentCourseRegistrationRepository.save(studentCourseRegistration);
    }


}

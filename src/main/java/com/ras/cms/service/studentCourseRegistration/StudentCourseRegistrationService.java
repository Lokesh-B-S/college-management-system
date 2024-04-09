package com.ras.cms.service.studentCourseRegistration;

import com.ras.cms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface StudentCourseRegistrationService {

    StudentCourseRegistration findOne(Long id);

    void deleteRegisteredCourseFromList(Long id);
    void registerRegularCourseToStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm);


    StudentCourseRegistration findRegistrationByStudentAndCourse(EligibleStudent eligibleStudent, Course course);

    StudentCourseRegistration findRegistrationByStudentAndOpenElective(EligibleStudent eligibleStudent, OpenElective openElective);

    //this is to filter students of same program which may be useful in future
    StudentCourseRegistration findRegistrationByEligibleStudentAndOpenElectiveAndCurrentAcademicYearAndCurrentProgramAndCurrentSemester(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear academicYear, Program program, Semester semester);
    StudentCourseRegistration findRegistrationByEligibleStudentAndOpenElectiveAndCurrentAcademicYearAndCurrentSemester(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear academicYear, Semester semester);

    StudentCourseRegistration findRegistrationByEligibleStudentAndProfessionalElectiveAndCurrentAcademicYearAndCurrentSemester(EligibleStudent eligibleStudent, Course professionalElective, AcademicYear academicYear, Semester semester);


    void removeCourseFromStudent(EligibleStudent eligibleStudent, Course course);

    List<StudentCourseRegistration> findRegistrationsByStudent(EligibleStudent eligibleStudent);

    List<StudentCourseRegistration> findAllRegistrationsByCourse(Course course, AcademicYear academicYear, Semester semester);

    List<StudentCourseRegistration> findAllRegistrationsByOpenElective(OpenElective openElective, AcademicYear academicYear, Semester semester);
    List<StudentCourseRegistration> findAllRegistrationsByProfessionalElective(Course professionalElective, AcademicYear academicYear, Semester semester);
    List<StudentCourseRegistration> findAllRegistrationsByOpenElectiveAndAcademicYearAndSemesterAndGroupOfOpenElective(OpenElective openElective, AcademicYear academicYear, Semester semester, OEGroup groupOfOpenElective);

    List<StudentCourseRegistration> findAllRegistrationsByProfessionalElectiveAndAcademicYearAndSemesterAndGroupOfProfessionalElective(Course professionalElective, AcademicYear academicYear, Semester semester, PEGroup groupOfProfessionalElective);

    List<StudentCourseRegistration> findAllRegistrationsByProgramAndAcademicYearAndSemester(Program program, AcademicYear academicYear, Semester semester);

    List<StudentCourseRegistration> findRegistrationsByStudentAndCourseType(EligibleStudent eligibleStudent, String courseType);

//    boolean isCourseRegistered(EligibleStudent eligibleStudent, Course course);

//     Set<Long> getRegisteredCourseIdsForStudent(EligibleStudent eligibleStudent);

    void assignProfessionalElective1ToEligibleStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm);

    void assignProfessionalElective2ToEligibleStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm);

    void assignOpenElectiveToEligibleStudent(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear courseYear, Semester courseSemester, Program currentProgram, AcademicYear currentAcademicYear, Semester currentSemester, Term currentTerm);

    public void assignOEGroupToStudent(Long studentCourseRegistrationId, Long oegroupId);

    public void assignPEGroupToStudent(Long studentCourseRegistrationId, Long pegroupId);

//    public void assignPE1GroupToStudent(Long studentCourseRegistrationId, Long pegroupId);
//    public void assignPE2GroupToStudent(Long studentCourseRegistrationId, Long pegroupId);

}

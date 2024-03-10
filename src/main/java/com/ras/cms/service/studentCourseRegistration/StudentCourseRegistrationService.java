package com.ras.cms.service.studentCourseRegistration;

import com.ras.cms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface StudentCourseRegistrationService {

    StudentCourseRegistration findOne(Long id);

    void deleteRegisteredCourseFromList(Long id);
    void registerRegularCourseToStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester);


    StudentCourseRegistration findRegistrationByStudentAndCourse(EligibleStudent eligibleStudent, Course course);

    StudentCourseRegistration findRegistrationByStudentAndOpenElective(EligibleStudent eligibleStudent, OpenElective openElective);



    void removeCourseFromStudent(EligibleStudent eligibleStudent, Course course);

    List<StudentCourseRegistration> findRegistrationsByStudent(EligibleStudent eligibleStudent);

    List<StudentCourseRegistration> findRegistrationsByStudentAndCourseType(EligibleStudent eligibleStudent, String courseType);

//    boolean isCourseRegistered(EligibleStudent eligibleStudent, Course course);

//     Set<Long> getRegisteredCourseIdsForStudent(EligibleStudent eligibleStudent);

    void assignProfessionalElective1ToEligibleStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester);

    void assignProfessionalElective2ToEligibleStudent(EligibleStudent eligibleStudent, Course course, AcademicYear courseYear, Semester courseSemester);

    void assignOpenElectiveToEligibleStudent(EligibleStudent eligibleStudent, OpenElective openElective, AcademicYear courseYear, Semester courseSemester);

}

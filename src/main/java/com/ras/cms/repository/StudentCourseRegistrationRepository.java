package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.apache.el.lang.ELArithmetic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRegistrationRepository extends JpaRepository<StudentCourseRegistration, Long> {


//    Iterable<StudentCourseRegistration> findByEligibleStudent(EligibleStudent eligibleStudent);

    List<StudentCourseRegistration> findAllByEligibleStudent(EligibleStudent eligibleStudent);

    List<StudentCourseRegistration> findAllByEligibleStudentAndCourseType(EligibleStudent eligibleStudent, String courseType);

    StudentCourseRegistration findByEligibleStudentAndCourse(EligibleStudent eligibleStudent, Course course);

    StudentCourseRegistration findByEligibleStudentAndOpenElective(EligibleStudent eligibleStudent, OpenElective openElective);

    boolean existsByEligibleStudentAndCourse(EligibleStudent eligibleStudent, Course course);

    StudentCourseRegistration findByEligibleStudentAndCourseTypeAndCourseYearAndCourseSemester(EligibleStudent eligibleStudent, String courseType, AcademicYear courseYear, Semester courseSemester);


}

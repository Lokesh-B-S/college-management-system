package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

  //  List<Course> findByBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId);

//    boolean existsByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
//            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    boolean existsByBatchYearSemTermIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
            Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    List<Course> findAllByBatchYearSemTermIdAndCourseType(Long batchYearSemTermId, CourseType courseType);

    List<Course> findAllByBatchYearSemTermIdAndProgramAndCourseType(Long batchYearSemTermId, Program program, CourseType courseType);


//    Course findByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
//            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    List<Course> findAllByDepartmentAndSemester(Department department, Semester semester);

  //  Long findBatchYearDeptProgramSemIdByCourseId(Long courseId);

    List<Course> findAllByBatchYearSemTermId(Long batchYearSemTermId);

    List<Course> findAllByBatchYearSemTermIdAndDepartmentAndProgram(Long batchYearSemTermId, Department department, Program program);


    //Course findByCourseId(Long courseId);

    @Query("SELECT c FROM Course c WHERE c.batchYearSemTermId = ?1 AND c.department = ?2 AND c.courseType.typeOfCourse LIKE 'Professional Elective%'")
    List<Course> findAllProfessionalElectiveCoursesByBatchYearSemTermIdAndDepartment(Long batchYearSemTermId, Department department);


    @Query("SELECT c FROM Course c WHERE c.batchYearSemTermId = ?1 AND c.department = ?2 AND c.courseType.typeOfCourse NOT LIKE 'Professional Elective%'")
    List<Course> findAllRegularCoursesByBatchYearSemTermIdAndDepartment(Long batchYearSemTermId, Department department);

}

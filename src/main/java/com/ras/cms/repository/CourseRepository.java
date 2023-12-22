package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

  //  List<Course> findByBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId);

//    boolean existsByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
//            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    boolean existsByBatchYearSemTermIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
            Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    List<Course> findAllByBatchYearSemTermIdAndCourseType(Long batchYearSemTermId, CourseType courseType);

//    Course findByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
//            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    List<Course> findAllByDepartmentAndSemester(Department department, Semester semester);

  //  Long findBatchYearDeptProgramSemIdByCourseId(Long courseId);

    List<Course> findAllByBatchYearSemTermId(Long batchYearSemTermId);

    List<Course> findAllByBatchYearSemTermIdAndDepartment(Long batchYearSemTermId, Department department);


    //Course findByCourseId(Long courseId);

}

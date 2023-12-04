package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId);

    boolean existsByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    Course findByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    List<Course> findAllByDepartmentAndSemester(Department department, Semester semester);

    Long findBatchYearDeptProgramSemIdByCourseId(Long courseId);

    //Course findByCourseId(Long courseId);

}

package com.ras.cms.service.course;

import com.ras.cms.domain.*;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findOne(Long id);
    Course saveCourse(Course course);

    List<Course> getCoursesByBatchYearSemTermIdAndCourseType(Long batchYearSemTermId, CourseType courseType);
    List<Course> getCoursesByBatchYearSemTermId(Long batchYearSemTermId);

//    List<Course> getEntriesByBatchYearDeptProgramSemId(Long BatchYearDeptProgramSemId);

  //  Long getBatchYearDeptProgramSemIdByCourseId(Long courseId);

//    Long getBatchYearDeptProgramSemId(Long courseId);

    boolean doesEntryExist(Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    //boolean doesEntryExist(Long batchYearDeptProgramSemId, Long contactHours, Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

  //  Course getEntry(Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, CourseType courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    List<Course> getCoursesByBatchYearSemTermIdAndDepartmentAndProgram(Long batchYearSemTermId, Department department, Program program);

    void deleteCourse(Long id);

    List<Course> getCoursesByDepartmentAndSemester(Department department, Semester semester);
}

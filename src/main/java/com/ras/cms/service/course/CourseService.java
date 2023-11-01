package com.ras.cms.service.course;

import com.ras.cms.domain.*;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findOne(Long id);
    Course saveCourse(Course course);

    List<Course> getEntriesByBatchYearDeptProgramSemId(Long BatchYearDeptProgramSemId);

    Long getBatchYearDeptProgramSemIdByCourseId(Long courseId);

//    Long getBatchYearDeptProgramSemId(Long courseId);


    boolean doesEntryExist(Long batchYearDeptProgramSemId, Long contactHours, Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);
    //boolean doesEntryExist(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot, Course course);

    Course getEntry(Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    void deleteCourse(Long id);

    List<Course> getCoursesByDepartmentAndSemester(Department department, Semester semester);
}

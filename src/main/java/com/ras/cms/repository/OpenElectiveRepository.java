package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpenElectiveRepository extends JpaRepository<OpenElective, Long> {


   // List<OpenElective> findByBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId);

    boolean existsByBatchYearSemTermIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndOpenElectiveTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
            Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, OpenElectiveType openElectiveType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);



//    OpenElective findByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(
//            Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

//    List<OpenElective> findAllByDepartmentAndSemester(Department department, Semester semester);

//    Long findBatchYearDeptProgramSemIdByOpenElectiveId(Long openElectiveId);

    List<OpenElective> findAllByBatchYearSemTermIdAndOpenElectiveType(Long batchYearSemTermId, OpenElectiveType electiveType);
    List<OpenElective> findAllByBatchYearSemTermId(Long batchYearSemTermId);

}

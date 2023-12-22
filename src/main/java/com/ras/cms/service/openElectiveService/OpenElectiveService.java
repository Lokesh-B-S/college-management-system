package com.ras.cms.service.openElectiveService;

import com.ras.cms.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OpenElectiveService {

    List<OpenElective> findAll();
    OpenElective findOne(Long id);

    List<OpenElective> getOpenElectivesByBatchYearSemTermIdAndOpenElectiveType(Long batchYearSemTermId, OpenElectiveType electiveType);
    List<OpenElective> getOpenElectivesByBatchYearSemTermId(Long batchYearSemTermId);

    List<OpenElective> getOpenElectivesByBatchYearSemTermIdAndDepartment(Long batchYearSemTermId, Department department);

    OpenElective saveOpenElective(OpenElective openElective);

   // List<OpenElective> getEntriesByBatchYearDeptProgramSemId(Long BatchYearDeptProgramSemId);

    //Long getBatchYearDeptProgramSemIdByOpenElectiveId(Long openElectiveId);

//    Long getBatchYearDeptProgramSemId(Long courseId);


    boolean doesEntryExist(Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, OpenElectiveType openElectiveType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);
    //boolean doesEntryExist(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot, Course course);

    //OpenElective getEntry(Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits);

    void deleteOpenElective(Long id);

   // String processRequest(HttpServletRequest request);

   // List<OpenElective> getCoursesByDepartmentAndSemester(Department department, Semester semester);
}

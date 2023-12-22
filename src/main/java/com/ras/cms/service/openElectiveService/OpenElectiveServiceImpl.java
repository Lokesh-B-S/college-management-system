package com.ras.cms.service.openElectiveService;

import com.ras.cms.repository.OpenElectiveRepository;
import com.ras.cms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OneToOne;
import java.util.List;

@Service
public class OpenElectiveServiceImpl implements OpenElectiveService{

    @Autowired
    OpenElectiveRepository openElectiveRepository;


    @Override
    public List<OpenElective> findAll() {
        return openElectiveRepository.findAll();
    }

    @Override
    public OpenElective findOne(Long id) {
        return openElectiveRepository.findById(id).get();
    }

    @Override
    public List<OpenElective> getOpenElectivesByBatchYearSemTermIdAndOpenElectiveType(Long batchYearSemTermId, OpenElectiveType electiveType){
        return openElectiveRepository.findAllByBatchYearSemTermIdAndOpenElectiveType(batchYearSemTermId, electiveType);
    }
        @Override
    public List<OpenElective> getOpenElectivesByBatchYearSemTermId(Long batchYearSemTermId){
        return openElectiveRepository.findAllByBatchYearSemTermId(batchYearSemTermId);
    }

    @Override
    public List<OpenElective> getOpenElectivesByBatchYearSemTermIdAndDepartment(Long batchYearSemTermId, Department department){
        return openElectiveRepository.findAllByBatchYearSemTermIdAndDepartment(batchYearSemTermId, department);
    }


//    @Override
//    public Long getBatchYearSemTermIdByOpenElectiveId(Long openElectiveId){return openElectiveRepository.findBatchYearSemTermIdByOpenElectiveId(openElectiveId);}

//    @Override
//    public Long getBatchYearDeptProgramSemId(Long courseId){return openElectiveRepository.findBatchYearDeptProgramSemId(courseId);}


    @Override
    public OpenElective saveOpenElective(OpenElective openElective) {
        return openElectiveRepository.save(openElective);
    }

//    @Override
//    public List<OpenElective> getEntriesByBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId){
//        return openElectiveRepository.findByBatchYearDeptProgramSemId(batchYearDeptProgramSemId);
//    }

    public boolean doesEntryExist(Long batchYearSemTermId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, OpenElectiveType openElectiveType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits) {
        return openElectiveRepository.existsByBatchYearSemTermIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndOpenElectiveTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(batchYearSemTermId, contactHours,courseBatchesCount, courseCode, courseName,teachingDepartmentName, openElectiveType, lectureCredits, tutorialCredits, practicalCredits, totalCredits);
    }

    //fetching the row based on below parameters
//    public OpenElective getEntry(Long batchYearDeptProgramSemId, Long contactHours, Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits) {
//        return openElectiveRepository.findByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(batchYearDeptProgramSemId, contactHours,courseBatchesCount, courseCode, courseName, teachingDepartmentName, courseType, lectureCredits, tutorialCredits, practicalCredits, totalCredits);
//    }

    @Override
    public void deleteOpenElective(Long id) {
        openElectiveRepository.deleteById(id);
    }

//
//    public String processRequest(HttpServletRequest request) {
//
//        // Perform operations related to the request
//        String userName = request.getUserPrincipal().getName();
//        String department = getDepartmentFromUserName(userName);
//        // Use userName and department as needed
//        return department;
//    }
//
//    private String getDepartmentFromUserName(String userName) {
//        String department = "";
//        if ("hodcse".equals(userName)) {
//            department = "Computer Science";
//        } else if ("hodise".equals(userName)) {
//            department = "Information Science";
//        } else if ("hodmech".equals(userName)) {
//            department = "Mechanical";
//        } else if ("hodiem".equals(userName)) {
//            department = "Industrial Engineering and Management";
//        }
//        return department;
//    }
   // @Override
   // public List<OpenElective> getCoursesByDepartmentAndSemester(Department department, Semester semester){return openElectiveRepository.findAllByDepartmentAndSemester(department,semester);}
}


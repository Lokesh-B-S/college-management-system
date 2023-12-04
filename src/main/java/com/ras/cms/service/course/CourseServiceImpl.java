package com.ras.cms.service.course;

import com.ras.cms.repository.CourseRepository;
import com.ras.cms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findOne(Long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public Long getBatchYearDeptProgramSemIdByCourseId(Long courseId){return courseRepository.findBatchYearDeptProgramSemIdByCourseId(courseId);}

//    @Override
//    public Long getBatchYearDeptProgramSemId(Long courseId){return courseRepository.findBatchYearDeptProgramSemId(courseId);}


    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getEntriesByBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId){
        return courseRepository.findByBatchYearDeptProgramSemId(batchYearDeptProgramSemId);
    }

    public boolean doesEntryExist(Long batchYearDeptProgramSemId, Long contactHours,Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits) {
        return courseRepository.existsByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(batchYearDeptProgramSemId, contactHours,courseBatchesCount, courseCode, courseName,teachingDepartmentName, courseType, lectureCredits, tutorialCredits, practicalCredits, totalCredits);
    }

    //fetching the row based on below parameters
    public Course getEntry(Long batchYearDeptProgramSemId, Long contactHours, Long courseBatchesCount, String courseCode, String courseName, TeachingDepartment teachingDepartmentName, String courseType, Long lectureCredits, Long tutorialCredits, Long practicalCredits, Long totalCredits) {
        return courseRepository.findByBatchYearDeptProgramSemIdAndContactHoursAndCourseBatchesCountAndCourseCodeAndCourseNameAndTeachingDepartmentAndCourseTypeAndLectureCreditsAndTutorialCreditsAndPracticalCreditsAndTotalCredits(batchYearDeptProgramSemId, contactHours,courseBatchesCount, courseCode, courseName, teachingDepartmentName, courseType, lectureCredits, tutorialCredits, practicalCredits, totalCredits);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCoursesByDepartmentAndSemester(Department department, Semester semester){return courseRepository.findAllByDepartmentAndSemester(department,semester);}
}

package com.ras.cms.controller;

import com.ras.cms.dao.CourseUpdateDAO;
import com.ras.cms.dao.OpenElectiveUpdateDAO;
import com.ras.cms.domain.Course;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentAndProgramFetch;
import com.ras.cms.domain.OpenElective;
import com.ras.cms.repository.CourseRepository;
import com.ras.cms.repository.OpenElectiveRepository;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseRestController {

        @Autowired
        CourseController courseController;

        @Autowired
        CourseRepository courseRepo;

        @Autowired
    CourseService courseService;

        @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

        @GetMapping(value = {"/hod/courses", "/hod/courses/{batchYearSemTermId}"})
        public List<Course> coursesList(Model model,
                                              HttpServletRequest request,
                                              @PathVariable(required = false, name="batchYearSemTermId") Long batchYearSemTermId) {

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            Department dep = departmentAndProgramFetch.getDepartment();

            //  List<Course> openElectives = openElectiveService.findAll();
            //List<Course> courses = courseService.getCoursesByBatchYearSemTermId(batchYearSemTermId);  //returns all department! hence below line of code
            List<Course> courses = courseService.getCoursesByBatchYearSemTermIdAndDepartment(batchYearSemTermId, dep);

            model.addAttribute("courses", courses);

            return courses;
        }


        @PostMapping(value = "/hod/courses", consumes = "application/json", produces = "application/json")
        public List<Course> updateCourses(final @RequestBody List<CourseUpdateDAO> list) {
            List<Course> toDelete = list.stream().filter(o -> o.getAction() == CourseUpdateDAO.Action.DELETE)
                    .map(CourseUpdateDAO::getData).collect(Collectors.toList());
            List<Course> toUpdate = list.stream().filter(o -> o.getAction() == CourseUpdateDAO.Action.UPDATE)
                    .map(CourseUpdateDAO::getData).collect(Collectors.toList());

            List<Course> result = new ArrayList<>();

            if (!toDelete.isEmpty()) {
                courseRepo.deleteInBatch(toDelete);
            }
            if (!toUpdate.isEmpty()) {
                result = courseRepo.saveAll(toUpdate);
            }

            return result;
        }

}

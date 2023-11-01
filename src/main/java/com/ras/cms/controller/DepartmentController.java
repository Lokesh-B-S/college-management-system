package com.ras.cms.controller;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.Program;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.college.CollegeService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
@RequestMapping("/admin")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CollegeService CollegeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProgramService programService;

    @GetMapping(value="/listDepartment")
    public String departmentList(Model model) {

        //old code by sir
//        List<Department> departmentList = departmentService.findAll();
//        for (Department department : departmentList ) {
//            department.setCollegeName(CollegeService.findOne(department.getCollegeId()).getCollegeName());
//            //department.setCourseName(courseService.findOne(department.getCourseId()).getCourseName());
//        }
//        model.addAttribute("departmentList",  departmentList);

        List<Department> departments = departmentService.findAll();
        model.addAttribute("departmentList", departments);
        return "/departmentList";
    }

    @GetMapping(value={"/departmentEdit","/departmentEdit/{id}"})
    public String departmentEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("department", departmentService.findOne(id));
        } else {
            model.addAttribute("department", new Department());
        }
        model.addAttribute("Colleges", getColleges());
        List<Program> programs = programService.findAll();
        model.addAttribute("programs",programs);
        model.addAttribute("courses", getCourses());
        return "/departmentEdit";
    }

    private Object getColleges() {
        return  CollegeService.findAll();
    }

    private Object getCourses() {
        return  courseService.findAll();
    }

    @PostMapping(value="/departmentEdit")
    public String departmentEdit(Model model, Department department) {
        departmentService.saveDepartment(department);

        List<Program> programs = programService.findAll();
        model.addAttribute("programs",programs);
        model.addAttribute("departmentList", departmentService.findAll());
        return "/departmentList";
    }

    @GetMapping(value="/departmentDelete/{id}")
    public String  departmentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        departmentService.deleteDepartment(id);
        model.addAttribute("departmentList", departmentService.findAll());
        return "/departmentList";
    }
}
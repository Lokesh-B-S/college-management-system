package com.ras.cms.controller;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentAndProgramFetch;
import com.ras.cms.domain.Program;
import com.ras.cms.service.college.CollegeService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
//@RequestMapping("/admin")
public class DepartmentViewController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ProgramService programService;

    @Autowired
    CollegeService CollegeService;

    @Autowired
    CourseService courseService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

//    @GetMapping(value="/hod/listDepartment")
//    public String departmentListForHOD(Model model, HttpServletRequest request) {
//
//        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
//        Department dep = departmentAndProgramFetch.getDepartment();
//
//        model.addAttribute("programList", programService.getProgramsByDepartment(dep));
//        return "/programList";
//    }

    @GetMapping(value = {"/admin/listDepartment", "/hod/listDepartment"})
    public String departmentList(Model model) {

        List<Department> departments = departmentService.findAll();
        model.addAttribute("departmentList", departments);
        return "/departmentList";
    }

//
//    @GetMapping("/departments")
//    public List<Department> getDepartments(Model model){
//        return departmentRepo.findAll();
//    }
//
//    @GetMapping(value="/listDepartment")
//    public String departmentList(Model model) {
//
//        //old code by sir
////        List<Department> departmentList = departmentService.findAll();
////        for (Department department : departmentList ) {
////            department.setCollegeName(CollegeService.findOne(department.getCollegeId()).getCollegeName());
////            //department.setCourseName(courseService.findOne(department.getCourseId()).getCourseName());
////        }
////        model.addAttribute("departmentList",  departmentList);
//
//        List<Department> departments = departmentService.findAll();
//        model.addAttribute("departmentList", departments);
//        return "/departmentList";
//    }

    @GetMapping(value={"/admin/departmentEdit","/admin/departmentEdit/{id}"})
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

    @PostMapping(value="/admin/departmentEdit")
    public String departmentEdit(Model model, Department department) {
        departmentService.saveDepartment(department);

        List<Program> programs = programService.findAll();
        model.addAttribute("programs",programs);
        model.addAttribute("departmentList", departmentService.findAll());
        return "/departmentList";
    }

    @GetMapping(value="/admin/departmentDelete/{id}")
    public String  departmentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        departmentService.deleteDepartment(id);
        model.addAttribute("departmentList", departmentService.findAll());
        return "/departmentList";
    }


}
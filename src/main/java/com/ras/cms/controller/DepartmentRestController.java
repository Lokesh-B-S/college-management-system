package com.ras.cms.controller;

import com.ras.cms.dao.DepartmentUpdateDAO;
import com.ras.cms.domain.Department;
import com.ras.cms.repository.DepartmentRepository;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.college.CollegeService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Surya on 12-Jun-18.
 */
@RestController
//@RequestMapping("/admin/departments")
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CollegeService CollegeService;

    @Autowired
    private DepartmentRepository departmentRepo;


    @GetMapping(value = "/admin/departments")
    public List<Department> departmentsList(Model model) {

        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return departmentRepo.findAll();
    }


    @PostMapping(value = "/admin/departments", consumes = "application/json", produces = "application/json")
    public List<Department> updateDepartments(final @RequestBody List<DepartmentUpdateDAO> list) {
        List<Department> toDelete = list.stream().filter(o -> o.getAction() == DepartmentUpdateDAO.Action.DELETE)
                .map(DepartmentUpdateDAO::getData).collect(Collectors.toList());
        List<Department> toUpdate = list.stream().filter(o -> o.getAction() == DepartmentUpdateDAO.Action.UPDATE)
                .map(DepartmentUpdateDAO::getData).collect(Collectors.toList());

        List<Department> result = new ArrayList<>();

        if (!toDelete.isEmpty()) {
            departmentRepo.deleteInBatch(toDelete);
        }
        if (!toUpdate.isEmpty()) {
            result = departmentRepo.saveAll(toUpdate);
        }

        return result;
    }
}




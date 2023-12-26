package com.ras.cms.controller;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentAndProgramFetch;
import com.ras.cms.domain.Program;
import com.ras.cms.repository.ProgramRepository;
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

@Controller
//@RequestMapping("/admin")
public class ProgramViewController {

    @Autowired
    ProgramService programService;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @GetMapping(value="/hod/listPrograms")
    public String programListForHOD(Model model, HttpServletRequest request) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        model.addAttribute("programList", programService.getProgramsByDepartment(dep));
        return "/programList";
    }

    @GetMapping(value="/admin/listPrograms")
    public String programListForPrincipal(Model model) {
        model.addAttribute("programList", programService.findAll());
        return "/programList";
    }


    @GetMapping(value={"/admin/editProgram","/admin/editProgram/{id}"})
    public String programEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("program", programService.findOne(id));
            model.addAttribute("departments",departmentService.findAll());
        } else {
            model.addAttribute("program", new Program());
            model.addAttribute("departments",departmentService.findAll());
        }
        return "/programEdit";
    }

    @PostMapping(value="/admin/editProgram")
    public String programEdit(Model model, Program program) {
        programService.saveProgram(program);
        model.addAttribute("programList", programService.findAll());
        return "/programList";
    }

    @GetMapping(value="/admin/deleteProgram/{id}")
    public String programDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        programService.deleteProgram(id);
        model.addAttribute("programList", programService.findAll());
        return "/programList";
    }

}

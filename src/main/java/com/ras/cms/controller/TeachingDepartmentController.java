package com.ras.cms.controller;

import com.ras.cms.domain.TeachingDepartment;
import com.ras.cms.domain.TeachingDepartment;
import com.ras.cms.service.teachingdepartment.TeachingDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hod")
public class TeachingDepartmentController {

    @Autowired
    TeachingDepartmentService teachingDepartmentService;


    @GetMapping(value="/listTeachingDepartments")
    public String TeachingDepartmentList(Model model) {
        model.addAttribute("teachingDepartmentList", teachingDepartmentService.findAll());
        return "/teachingDepartmentList";
    }

    @GetMapping(value={"/editTeachingDepartment","/editTeachingDepartment/{id}"})
    public String teachingDepartmentEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("teachingDepartment", teachingDepartmentService.findOne(id));
        } else {
            model.addAttribute("teachingDepartment", new TeachingDepartment());
        }
      //  model.addAttribute("departments",departmentService.findAll());
      //  model.addAttribute("programs",programService.findAll());
        return "/teachingDepartmentEdit";
    }

    @PostMapping(value="/editTeachingDepartment")
    public String teachingDepartmentEdit(Model model, TeachingDepartment teachingDepartment) {

        teachingDepartmentService.saveTeachingDepartment(teachingDepartment);
        model.addAttribute("teachingDepartmentList", teachingDepartmentService.findAll());
        return "/teachingDepartmentList";
    }

    @GetMapping(value="/deleteTeachingDepartment/{id}")
    public String teachingDepartmentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        teachingDepartmentService.deleteTeachingDepartment(id);
        model.addAttribute("teachingDepartmentList", teachingDepartmentService.findAll());
        return "/teachingDepartmentList";
    }

}

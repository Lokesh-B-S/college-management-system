package com.ras.cms.controller;

import com.ras.cms.domain.Course;
import com.ras.cms.domain.Faculty;
import com.ras.cms.service.faculty.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/admin")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @GetMapping(value={"/admin/listFaculty","/hod/listFaculty","/faculty/listFaculty"})
    public String facultyList(Model model) {
        model.addAttribute("facultyList", facultyService.findAll());
        return "/facultyList";
    }

    @GetMapping(value={"/admin/editFaculty","/admin/editFaculty/{id}"})
    public String facultyEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("faculty", facultyService.findOne(id));
        } else {
            model.addAttribute("faculty", new Faculty());
        }
        return "/facultyEdit";
    }

    @PostMapping("/admin/editFaculty")
    public String facultyEdit(Model model, Faculty faculty){
        facultyService.saveFaculty(faculty);
       // List<Faculty> facultyList = facultyService.findAll();
        model.addAttribute("facultyList",facultyService.findAll());
        return "/facultyList";
    }

    @GetMapping(value="/admin/deleteFaculty/{id}")
    public String facultyDelete(Model model,@PathVariable(required = true, name = "id") Long id){
        facultyService.deleteFaculty(id);
        model.addAttribute("facultyList",facultyService.findAll());
        return "/facultyList";

    }
}

package com.ras.cms.controller;

import com.ras.cms.domain.AcademicYear;
import com.ras.cms.domain.Batch;
import com.ras.cms.domain.Department;
import com.ras.cms.service.academicyear.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hod")
public class AcademicYearController {

@Autowired
    AcademicYearService academicYearService;
    @GetMapping(value="/listAcademicYears")
    public String academicYearList(Model model) {
        model.addAttribute("academicYearList", academicYearService.findAll());
        return "/academicYearList";
    }

    @GetMapping(value={"/editAcademicYear","/editAcademicYear/{id}"})
    public String academicYearEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("academicYear", academicYearService.findOne(id));
        } else {
            model.addAttribute("academicYear", new AcademicYear());
        }
       // model.addAttribute("departments",academicYearService.findAll());
       // model.addAttribute("programs",academicYearService.findAll());
        return "/academicYearEdit";
    }

    @PostMapping(value="/editAcademicYear")
    public String academicYearEdit(Model model, AcademicYear academicYear) {

        academicYearService.saveAcademicYear(academicYear);
        model.addAttribute("academicYearList", academicYearService.findAll());
        return "/academicYearList";
    }

    @GetMapping(value="/deleteAcademicYear/{id}")
    public String academicYearDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        academicYearService.deleteAcademicYear(id);
        model.addAttribute("academicYearList", academicYearService.findAll());
        return "/academicYearList";
    }
}

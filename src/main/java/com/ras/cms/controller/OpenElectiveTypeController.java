package com.ras.cms.controller;

import com.ras.cms.domain.AcademicYear;
import com.ras.cms.domain.OpenElectiveType;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.openelectivetype.OpenElectiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Access;

@Controller
@RequestMapping("/hod")
public class OpenElectiveTypeController {

    @Autowired
    OpenElectiveTypeService openElectiveTypeService;


    @GetMapping(value="/listOpenElectiveTypes")
    public String OpenElectiveTypeList(Model model) {
        model.addAttribute("openElectiveTypeList", openElectiveTypeService.findAll());
        return "/openElectiveTypeList";
    }

    @GetMapping(value={"/editOpenElectiveType","/editOpenElectiveType/{id}"})
    public String OpenElectiveTypeEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("openElectiveType", openElectiveTypeService.findOne(id));
        } else {
            model.addAttribute("openElectiveType", new OpenElectiveType());
        }
        return "/openElectiveTypeEdit";
    }

    @PostMapping(value="/editOpenElectiveType")
    public String openElectiveTypeEdit(Model model, OpenElectiveType openElectiveType) {

        openElectiveTypeService.saveOpenElectiveType(openElectiveType);
        model.addAttribute("openElectiveTypeList", openElectiveTypeService.findAll());
        return "/openElectiveTypeList";
    }

    @GetMapping(value="/deleteOpenElectiveType/{id}")
    public String openElectiveTypeDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        openElectiveTypeService.deleteOpenElectiveType(id);
        model.addAttribute("openElectiveTypeList", openElectiveTypeService.findAll());
        return "/openElectiveTypeList";
    }

}

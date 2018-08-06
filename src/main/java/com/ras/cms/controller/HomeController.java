package com.ras.cms.controller;

import com.ras.cms.dao.RoleRepository;
import com.ras.cms.domain.SubjectType;
import com.ras.cms.service.role.RoleService;
import com.ras.cms.service.subjecttype.SubjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
@Controller
public class HomeController {
    @GetMapping("/")
    String index(Principal principal, Model model){
        model.addAttribute("springVersion", SpringVersion.getVersion());
//        return principal != null ? "/homeSignedIn" : "/homeNotSignedIn";
        return "/home";
    }

    /* Code for SubjectType - Start */
    @Autowired
    SubjectTypeService subjectTypeService;

    @GetMapping(value={"/subjectTypeEdit","/subjectTypeEdit/{subjectTypeId}"})
    public String studentEditForm(Model model, @PathVariable(required = false, name = "subjectTypeId") Long id) {
        if (null != id) {
            model.addAttribute("subjectType", subjectTypeService.findOne(id));
        } else {
            model.addAttribute("subjectType", new SubjectType());
        }
        return "/subjectTypeEdit";
    }

    @PostMapping("/subjectTypeEdit")
    String subjectTypeEdit(Model model, SubjectType subjectType){
        subjectTypeService.saveSubjectType(subjectType);
        model.addAttribute("subjectTypeList", subjectTypeService.findAll());
        return "/subjectTypeList";
    }
    /* Code for SubjectType - End */

    /* Code for Role -Start */
    @Autowired
    RoleService roleService;

    @GetMapping(value="/listRole")
    public String roleList(Model model) {
        model.addAttribute("roleList", roleService.findAll());
        return "/roleList";
    }

    @GetMapping(value="/deleteRole")
    public String deleteRole(Model model) {
        roleService.deleteRoles();//DELETE All Roles
        model.addAttribute("roleList", roleService.findAll());
        return "/roleList";
    }

    /* Code for Role -End */
}
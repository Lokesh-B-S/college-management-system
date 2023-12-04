package com.ras.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//to fetch all the dynamic pages
@Controller
public class MainController {


@GetMapping("/admin/programs/addnew")
    public String programs(Model model){return "ProgramD";}

    @GetMapping("/admin/departments/addnew")
    public String departments(Model model){return "departmentD";}
}

package com.ras.cms.controller;

import com.ras.cms.domain.College;
import com.ras.cms.service.college.CollegeService;
import com.ras.cms.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CollegeRestController {

    @Autowired
    CollegeService CollegeService;

    @GetMapping(value = "/Colleges")
    public List<College> getColleges(Model model) {
        return CollegeService.findAll();
    }
}

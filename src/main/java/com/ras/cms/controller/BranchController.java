package com.ras.cms.controller;

import com.ras.cms.domain.Branch;
import com.ras.cms.service.branch.BranchService;
import com.ras.cms.service.collage.CollageService;
import com.ras.cms.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
@RequestMapping("/admin")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private CollageService collageService;

    @Autowired
    private CourseService courseService;

    @GetMapping(value="/listBranch")
    public String branchList(Model model) {
        List<Branch> branchList = branchService.findAll();
        for (Branch branch : branchList ) {
            branch.setCollageName(collageService.findOne(branch.getCollageId()).getCollageName());
            branch.setCourseName(courseService.findOne(branch.getCourseId()).getCourseName());
        }
        model.addAttribute("branchList",  branchList);
        return "/branchList";
    }

    @GetMapping(value={"/branchEdit","/branchEdit/{id}"})
    public String branchEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("branch", branchService.findOne(id));
        } else {
            model.addAttribute("branch", new Branch());
        }
        model.addAttribute("collages", getCollages());
        model.addAttribute("courses", getCourses());
        return "/branchEdit";
    }

    private Object getCollages() {
        return  collageService.findAll();
    }

    private Object getCourses() {
        return  courseService.findAll();
    }

    @PostMapping(value="/branchEdit")
    public String branchEdit(Model model, Branch branch) {
        branchService.saveBranch(branch);
        model.addAttribute("branchList", branchService.findAll());
        return "/branchList";
    }

    @GetMapping(value="/branchDelete/{id}")
    public String  branchDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        branchService.deleteBranch(id);
        model.addAttribute("branchList", branchService.findAll());
        return "/branchList";
    }
}
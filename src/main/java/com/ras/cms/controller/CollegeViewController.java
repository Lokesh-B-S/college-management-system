package com.ras.cms.controller;

import com.ras.cms.domain.College;
import com.ras.cms.domain.Course;
import com.ras.cms.service.college.CollegeService;
import com.ras.cms.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
@RequestMapping("/admin")
public class CollegeViewController {

    @Autowired
    CollegeService CollegeService;

    @Autowired
    CourseService courseService;

    @GetMapping(value="/listCollege")
    public String CollegeList(Model model) {
        model.addAttribute("CollegeList", CollegeService.findAll());
        return "/CollegeList";
    }

    @GetMapping(value={"/CollegeEdit","/CollegeEdit/{id}"})
    public String CollegeEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("College", CollegeService.findOne(id));
            if(CollegeService.findOne(id).getCourses()==null){
                CollegeService.findOne(id).setCourses(new ArrayList<Course>());
            }
            model.addAttribute("courses",CollegeService.findOne(id).getCourses());
        } else {
            model.addAttribute("College", new College());
            model.addAttribute("courses", new Course());
        }
        return "/CollegeEdit";
    }

    @GetMapping(value="/addCourse/{id}")
    public String addCourse(Model model, @PathVariable(required = true, name = "id") Long CollegeId){
        if(CollegeService.findOne(CollegeId).getCourses()==null){
            CollegeService.findOne(CollegeId).setCourses(new ArrayList<Course>());
        }
        model.addAttribute("CollegeList",CollegeService.findOne(CollegeId));
        model.addAttribute("course",CollegeService.findOne(CollegeId).getCourses());
        model.addAttribute("courses",courseService.findAll());
        return "/addCourse";
    }

    @PostMapping(value="/addCourse")
    public String addCourses(Model model, @RequestParam String selectedCourse,@RequestParam Long CollegeId) {
        College College = CollegeService.findOne(CollegeId);
        for(String courseId : selectedCourse.split(",")){
            Course c = courseService.findOne(Long.parseLong(courseId));
            if(!College.hasCourse(c)){
                College.getCourses().add(c);
            }
        }
        CollegeService.saveCollege(College);
        model.addAttribute("College", College);
        model.addAttribute("courses",College.getCourses());
        return "/CollegeEdit";
    }

    @PostMapping(value="/CollegeEdit")
    public String CollegeEdit(Model model, College College) {
        CollegeService.saveCollege(College);
        model.addAttribute("CollegeList", CollegeService.findAll());
        return "/CollegeList";
    }

    @GetMapping(value="/CollegeDelete/{id}")
    public String CollegeDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        CollegeService.deleteCollege(id);
        model.addAttribute("CollegeList", CollegeService.findAll());
        return "/CollegeList";
    }
}
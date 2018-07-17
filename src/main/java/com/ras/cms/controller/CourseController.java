package com.ras.cms.controller;

import com.ras.cms.domain.Course;
import com.ras.cms.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping(value="/listCourse")
    public String courseList(Model model) {
        model.addAttribute("courseList", courseService.findAll());
        return "/courseList";
    }

    @GetMapping(value={"/courseEdit","/courseEdit/{id}"})
    public String courseEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("course", courseService.findOne(id));
        } else {
            model.addAttribute("course", new Course());
        }
        return "/courseEdit";
    }

    @PostMapping(value="/courseEdit")
    public String courseEdit(Model model, Course course) {
        courseService.saveCourse(course);
        model.addAttribute("courseList", courseService.findAll());
        return "/courseList";
    }

    @GetMapping(value="/courseDelete/{id}")
    public String courseDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        courseService.deleteCourse(id);
        model.addAttribute("courseList", courseService.findAll());
        return "/courseList";
    }
}
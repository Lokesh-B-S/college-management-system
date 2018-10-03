package com.ras.cms.controller;

import com.ras.cms.domain.Collage;
import com.ras.cms.domain.Course;
import com.ras.cms.service.collage.CollageService;
import com.ras.cms.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
@RequestMapping("/admin")
public class CollageController {

    @Autowired
    CollageService collageService;

    @Autowired
    CourseService courseService;

    @GetMapping(value="/listCollage")
    public String collageList(Model model) {
        model.addAttribute("collageList", collageService.findAll());
        return "/collageList";
    }

    @GetMapping(value={"/collageEdit","/collageEdit/{id}"})
    public String collageEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("collage", collageService.findOne(id));
            if(collageService.findOne(id).getCourses()==null){
                collageService.findOne(id).setCourses(new ArrayList<Course>());
            }
            model.addAttribute("courses",collageService.findOne(id).getCourses());
        } else {
            model.addAttribute("collage", new Collage());
            model.addAttribute("courses", new Course());
        }
        return "/collageEdit";
    }

    @GetMapping(value="/addCourse/{id}")
    public String addCourse(Model model, @PathVariable(required = true, name = "id") Long collageId){
        if(collageService.findOne(collageId).getCourses()==null){
            collageService.findOne(collageId).setCourses(new ArrayList<Course>());
        }
        model.addAttribute("collageList",collageService.findOne(collageId));
        model.addAttribute("course",collageService.findOne(collageId).getCourses());
        model.addAttribute("courses",courseService.findAll());
        return "/addCourse";
    }

    @PostMapping(value="/addCourse")
    public String addCourses(Model model, @RequestParam String selectedCourse,@RequestParam Long collageId) {
        Collage collage = collageService.findOne(collageId);
        for(String courseId : selectedCourse.split(",")){
            Course c = courseService.findOne(Long.parseLong(courseId));
            if(!collage.hasCourse(c)){
                collage.getCourses().add(c);
            }
        }
        collageService.saveCollage(collage);
        model.addAttribute("collage", collage);
        model.addAttribute("courses",collage.getCourses());
        return "/collageEdit";
    }

    @PostMapping(value="/collageEdit")
    public String collageEdit(Model model, Collage collage) {
        collageService.saveCollage(collage);
        model.addAttribute("collageList", collageService.findAll());
        return "/collageList";
    }

    @GetMapping(value="/collageDelete/{id}")
    public String collageDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        collageService.deleteCollage(id);
        model.addAttribute("collageList", collageService.findAll());
        return "/collageList";
    }
}
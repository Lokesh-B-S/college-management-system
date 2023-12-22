package com.ras.cms.controller;

import com.ras.cms.domain.CourseType;
import com.ras.cms.service.coursetype.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hod")
public class CourseTypeController {

        @Autowired
        CourseTypeService courseTypeService;


        @GetMapping(value="/listCourseTypes")
        public String CourseTypeList(Model model) {
            model.addAttribute("courseTypeList", courseTypeService.findAll());
            return "/courseTypeList";
        }

        @GetMapping(value={"/editCourseType","/editCourseType/{id}"})
        public String CourseTypeEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
            if (null != id) {
                model.addAttribute("courseType", courseTypeService.findOne(id));
            } else {
                model.addAttribute("courseType", new CourseType());
            }
            return "/courseTypeEdit";
        }

        @PostMapping(value="/editCourseType")
        public String CourseTypeEdit(Model model, CourseType courseType) {

            courseTypeService.saveCourseType(courseType);
            model.addAttribute("courseTypeList", courseTypeService.findAll());
            return "/courseTypeList";
        }

        @GetMapping(value="/deleteCourseType/{id}")
        public String courseTypeDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
            courseTypeService.deleteCourseType(id);
            model.addAttribute("courseTypeList", courseTypeService.findAll());
            return "/courseTypeList";
        }

}

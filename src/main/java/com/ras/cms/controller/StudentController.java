package com.ras.cms.controller;

import com.ras.cms.service.StudentService;
import com.ras.cms.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value="/listStudent")
    public String studentList(Model model) {
        model.addAttribute("studentList", studentService.findAll());
        return "studentList";
    }

    @GetMapping(value={"/studentEdit","/studentEdit/{id}"})
    public String studentEditForm(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("student", studentService.findOne(id));
        } else {
            model.addAttribute("student", new Student());
        }
        return "studentEdit";
    }

    @PostMapping(value="/studentEdit")
    public String studentEdit(Model model, Student student) {
        studentService.saveStudent(student);
        model.addAttribute("studentList", studentService.findAll());
        return "studentList";
    }

    @GetMapping(value="/studentDelete/{id}")
    public String studentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        studentService.deleteStudent(id);
        model.addAttribute("studentList", studentService.findAll());
        return "studentList";
    }
}

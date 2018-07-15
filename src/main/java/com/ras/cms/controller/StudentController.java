package com.ras.cms.controller;

import com.ras.cms.service.student.StudentService;
import com.ras.cms.domain.Student;

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
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value="/listStudent")
    public String studentList(Model model) {
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }

    private Object getStates() {
        List<String> stateList = new ArrayList<>(26);
        stateList.add("Karnataka");
        stateList.add("J & K");
        stateList.add("Tamil Nadu");
        stateList.add("Andra Pradesh");
        stateList.add("Telangana");
        return stateList;
    }

    @GetMapping(value={"/studentEdit","/studentEdit/{id}"})
    public String studentEditForm(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("student", studentService.findOne(id));
            model.addAttribute("states",getStates());
//            model.addObject("states", getStates());
        } else {
            model.addAttribute("student", new Student());
            model.addAttribute("states",getStates());
//            model.addObject("states", getStates());
        }
        return "/studentEdit";
    }

    @PostMapping(value="/studentEdit")
    public String studentEdit(Model model, Student student) {
        studentService.saveStudent(student);
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }

    @GetMapping(value="/studentDelete/{id}")
    public String studentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        studentService.deleteStudent(id);
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }
}
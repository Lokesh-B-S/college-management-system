package com.ras.cms.controller;

import com.ras.cms.domain.Qualification;
import com.ras.cms.domain.State;
import com.ras.cms.service.state.StateService;
import com.ras.cms.service.student.StudentService;
import com.ras.cms.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    StateService stateService;

    @GetMapping(value="/listStudent")
    public String studentList(Model model) {
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }

    private Object getStates() {
        return  stateService.findAll();
    }

    @GetMapping(value={"/studentEdit","/studentEdit/{id}"})
    public String studentEditForm(Model model, @PathVariable(required = false, name = "id") Long id,
                                  @RequestParam(required = false, name="add") String add) {
        if (null != id) {
            Student student = studentService.findOne(id);
            model.addAttribute("student", student);
            model.addAttribute("states",getStates());
            model.addAttribute("native",getNativeState());
        } else {
            Student student = new Student();
            List<Qualification> qualifList = new ArrayList<>(3);
            qualifList.add(new Qualification("SSLC or 10th"));
            qualifList.add(new Qualification("PUC or 12th"));
            student.setQualifications(qualifList);
            model.addAttribute("student", student);
            model.addAttribute("states",getStates());
            model.addAttribute("native",getNativeState());
        }
        return "/studentEdit";
    }

    @PostMapping(value="/studentEdit")
    public String studentEdit(Model model, Student student) {
        studentService.saveStudent(student);
        model.addAttribute("studentList", studentService.findAll());
        cleanUpQualification(student);
        return "/studentList";
    }

    private void cleanUpQualification(Student student) {

    }

    @PostMapping(value="/studentEduAdd")
    public String studentEditAddRow(Model model, Student student){
        Student stud = studentService.findOne(student.getId());
        List<Qualification> finalList = new ArrayList<>();
        if(stud != null && student.getQualifications() != null && student.getQualifications().size() > 0){
            for(Qualification qual : student.getQualifications()) {
                if (qual != null && qual.getName() != null && !stud.hasQualification(qual)) {
                    finalList.add(qual);
                }
            }
        }
        finalList.add(new Qualification());
        student.setQualifications(finalList);
        studentService.saveStudent(student);

        stud = studentService.findOne(student.getId());
        model.addAttribute("student", stud);
        model.addAttribute("states",getStates());
        model.addAttribute("native",getNativeState());
        return "/studentEdit";
    }

    @GetMapping(value="/studentDelete/{id}")
    public String studentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        studentService.deleteStudent(id);
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }

    Map<Integer,String> getNativeState() {
        Map<Integer, String> nativeState = new HashMap<>();
        nativeState.put(1, "Karnataka State");
        nativeState.put(2, "Other State");
        return nativeState;
    }
}
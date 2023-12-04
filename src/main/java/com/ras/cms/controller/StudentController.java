package com.ras.cms.controller;

import com.ras.cms.domain.Qualification;
import com.ras.cms.domain.Student;
import com.ras.cms.service.state.StateService;
import com.ras.cms.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
//@RequestMapping("/admin")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StateService stateService;

    @GetMapping(value={"/admin/listStudent","/hod/listStudent","/student/listStudent"})
    public String studentList(Model model) {
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }

    private Object getStates() {
        return  stateService.findAll();
    }

    @GetMapping(value={"/admin/studentEdit","/admin/studentEdit/{id}"})
    public String studentEditForm(Model model, @PathVariable(required = false, name = "id") Long id,
                                  @RequestParam(required = false, name="add") String add) {
        if (null != id) {
            Student student = studentService.findOne(id);
            model.addAttribute("student", student);
            model.addAttribute("states",getStates());
            model.addAttribute("native",getNativeState());
            model.addAttribute("categories",getCategory());
            model.addAttribute("specialCategory",getSpecialCategory());
        } else {
            Student student = new Student();
            List<Qualification> qualifList = new ArrayList<>(3);
            qualifList.add(new Qualification("SSLC or 10th"));
            qualifList.add(new Qualification("PUC or 12th"));
            student.setQualifications(qualifList);
            model.addAttribute("student", student);
            model.addAttribute("states",getStates());
            model.addAttribute("native",getNativeState());
            model.addAttribute("categories",getCategory());
            model.addAttribute("specialCategory",getSpecialCategory());
        }
        return "/studentEdit";
    }

    @PostMapping(value="/admin/studentEdit")
    public String studentEdit(Model model, Student student) {
        studentService.saveStudent(student);
        model.addAttribute("studentList", studentService.findAll());
        cleanUpQualification(student);
        return "/studentList";
    }

    private void cleanUpQualification(Student student) {

    }

    @PostMapping(value="/admin/studentEduAdd")
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
        model.addAttribute("categories",getCategory());
        model.addAttribute("specialCategory",getSpecialCategory());
        return "/studentEdit";
    }

    @GetMapping(value="/admin/studentDelete/{id}")
    public String studentDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        studentService.deleteStudent(id);
        model.addAttribute("studentList", studentService.findAll());
        return "/studentList";
    }

    Map<Integer,String> getNativeState() {
        Map<Integer, String> nativeState = new LinkedHashMap<>();
        nativeState.put(0, "-- Select Category --");
        nativeState.put(1, "Karnataka State");
        nativeState.put(2, "Other State");
        return nativeState;
    }

    Map<String,String> getCategory() {
        Map<String, String> category = new LinkedHashMap<>();
        category.put("", "-- Select Category --");
        category.put("SC", "SC");
        category.put("ST", "ST");
        category.put("CAT-1", "1");
        category.put("CAT-2 A", "2A");
        category.put("CAT-2 B", "2B");
        category.put("CAT-3 A", "3A");
        category.put("CAT-3 B", "3B");
        category.put("CAT-1", "GM");
        category.put("Rural", "Rural");
        return category;
    }

    Map<String,String> getSpecialCategory() {
        Map<String, String> specialCategory = new LinkedHashMap<>();
        specialCategory.put("", "-- Select Special Category--");
        specialCategory.put("PS", "Political Sufferer");
        specialCategory.put("DP", "Defence Personnel");
        specialCategory.put("EDP", "Ex-Defence Personnel");
        specialCategory.put("SP", "Sports");
        specialCategory.put("NCC", "NCC");
        specialCategory.put("SS", "Scouts");
        specialCategory.put("JTS", "JTS");
        specialCategory.put("ITI", "ITI");
        specialCategory.put("JOC", "JOC");
        specialCategory.put("CI", "Correctional Institution");
        specialCategory.put("HK", "Horanadu Kannadiga");
        specialCategory.put("GK", "Gadinadu Kannadiga");
        specialCategory.put("PH", "Physically Handicapped");
        specialCategory.put("AI", "Anglo Indian of Karnataka");
        return specialCategory;
    }
}
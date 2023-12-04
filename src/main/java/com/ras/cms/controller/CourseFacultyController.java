package com.ras.cms.controller;

import com.ras.cms.domain.Course;
import com.ras.cms.domain.CourseFaculty;
import com.ras.cms.domain.Faculty;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.coursefaculty.CourseFacultyService;
import com.ras.cms.service.faculty.FacultyService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/admin","/hod"})
public class CourseFacultyController {

    @Autowired
    CourseFacultyService courseFacultyService;

    @Autowired
    CourseService courseService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ProgramService programService;

    @Autowired
    SemesterService semesterService;

    @Autowired
    SectionService sectionService;

    @Autowired
    FacultyService facultyService;


    @GetMapping(value="/listCourseFaculty")
    public String courseFacultyList(Model model) {
        model.addAttribute("courseFacultyList", courseFacultyService.findAll());
        return "/courseFacultyList";
    }

    @GetMapping(value={"/courseFacultyEdit","/courseFacultyEdit/{id}"})
    public String courseFacultyEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("courseFaculty", courseFacultyService.findOne(id));
        } else {
            model.addAttribute("courseFaculty", new CourseFaculty());
        }
        model.addAttribute("departments",departmentService.findAll());
        model.addAttribute("programs",programService.findAll());
        model.addAttribute("semesters",semesterService.findAll());
        model.addAttribute("sections",sectionService.findAll());
        model.addAttribute("courses",courseService.findAll());
        model.addAttribute("faculties",facultyService.findAll());

        return "/courseFacultyEdit";
    }

    @PostMapping(value="/courseFacultyEdit")
    public String courseFacultyEdit(Model model, CourseFaculty courseFaculty) {
        courseFacultyService.saveCourseFaculty(courseFaculty);
        model.addAttribute("courseFacultyList", courseFacultyService.findAll());
        return "/courseFacultyList";
    }

    @GetMapping(value="/deleteCourseFaculty/{id}")
    public String courseFacultyDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        courseFacultyService.deleteCourseFaculty(id);
        model.addAttribute("courseFacultyList", courseFacultyService.findAll());
        return "/courseFacultyList";
    }
}

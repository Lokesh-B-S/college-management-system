package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.student.StudentService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NewTimeTableController {

        @Autowired
        BatchService batchService;

        @Autowired
        DepartmentService departmentService;
        @Autowired
        ProgramService programService;
        @Autowired
        AcademicYearService academicYearService;
        @Autowired
        SemesterService semesterService;

        @Autowired
        SectionService sectionService;

        @Autowired
    TermService termService;
        @Autowired
        StudentService studentService;




    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @GetMapping({"/hod/selectPrerequisitesForTimetable"})
    public String selectbasicFieldsbeforeEligibleStudentsAllotment(Model model, HttpServletRequest request) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Section> sections = sectionService.findAll();
        List<Term> terms = termService.findAll();
        List<Program> programs = programService.getProgramsByDepartment(dep);

        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("sections", sections);
        model.addAttribute("terms", terms);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/NewTimetablePrerequisite";
    }


    @PostMapping({ "/hod/selectPrerequisitesForTimetable"})
    public String postselectofeligibleStudentPrerequisite(Model model, HttpServletRequest request,
                                                          @RequestParam(required=false, name="academicYear") Long academicYearId,
                                                          @RequestParam(required=false, name="program") Long programId,
                                                          @RequestParam(required = false, name="semester") Long semId,
                                                          @RequestParam(required = false, name="section") Long sectionId,
                                                          @RequestParam(required=false, name="term") Long termId)

    {

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Section section = sectionService.findOne(sectionId);
        Term term = termService.findOne(termId);

        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("semester", semester);
        model.addAttribute("section", section);
        model.addAttribute("term", term);

//        if (request.isUserInRole("PRINCIPAL")) {
//            return "redirect:/admin/listEligibleStudent/" + academicYearId + "/" + programId + "/" + semId + "/" + sectionId + "/" + termId;
//
//        }
        if (request.isUserInRole("DEPT_HEAD")){
            return "redirect:/hod/timeTableEntries/addnew/" + academicYearId + "/" + programId + "/" + semId + "/" + sectionId + "/" + termId;
        }

        return "/403";
    }


}

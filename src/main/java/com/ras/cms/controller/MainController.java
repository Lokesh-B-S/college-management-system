package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.day.DayService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//to fetch all the dynamic pages
@Controller
public class MainController {

    @Autowired
    BatchYearSemTermService batchYearSemTermService;

    @Autowired
    OpenElectiveService openElectiveService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @Autowired
    ProgramService programService;

    @Autowired
    AcademicYearService academicYearService;

    @Autowired
    TermService termService;

    @Autowired
    SemesterService semesterService;
    @Autowired
    SectionService sectionService;

    @Autowired
    DayService dayService;

    @GetMapping("/admin/programs/addnew")
    public String programs(Model model) {
        return "ProgramD";
    }

    @GetMapping("/admin/departments/addnew")
    public String departments(Model model) {
        return "departmentD";
    }

    @GetMapping("/hod/openElectives/addnew/{batchYearSemTermId}")
    public String openElectives(Model model,
                                HttpServletRequest request,
                                @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId) {
        if (null != batchYearSemTermId) {
            System.out.println(batchYearSemTermId);

            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.findOne(batchYearSemTermId);
            model.addAttribute("batchYearSemTerm", batchYearSemTerm);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            model.addAttribute("department", departmentAndProgramFetch.getDepartment());

            //note this program is functionally correct but logically wrong
            model.addAttribute("program", departmentAndProgramFetch.getProgram());

            //to know the type() of a variable
            System.out.println(departmentAndProgramFetch.getDepartment().getDepartmentId().getClass().getSimpleName());
            System.out.println(departmentAndProgramFetch.getProgram().getProgramId().getClass().getSimpleName());
            //to fetch these ids
            //model.addAttribute("departmentId", departmentAndProgramFetch.getDepartment().getDepartmentId());
            //model.addAttribute("programId", departmentAndProgramFetch.getProgram().getProgramId());

        }
        return "OpenElectiveEditD";
    }


    @GetMapping("/hod/courses/addnew/{batchYearSemTermId}/{programId}")
    public String courses(Model model,
                          HttpServletRequest request,
                          @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId,
                          @PathVariable(required = false, name = "programId") Long programId) {
        if (null != batchYearSemTermId) {
            System.out.println(batchYearSemTermId);

            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.findOne(batchYearSemTermId);
            model.addAttribute("batchYearSemTerm", batchYearSemTerm);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            model.addAttribute("department", departmentAndProgramFetch.getDepartment());

            Program program = programService.findOne(programId);
            model.addAttribute("program", program);
//            model.addAttribute("program", departmentAndProgramFetch.getProgram());

        }
        return "courseEditD";
    }


    @GetMapping("/hod/eligibleStudents/addnew/{academicYearId}/{programId}/{semId}/{termId}")
    public String eligibleStudents(Model model,
                                   HttpServletRequest request,
                                   @PathVariable(required = false, name = "academicYearId") Long academicYearId,
                                   @PathVariable(required = false, name = "programId") Long programId,
                                   @PathVariable(required = false, name = "semId") Long semId,
                                   @PathVariable(required = false, name = "termId") Long termId) {
        if (null != academicYearId && null != programId && semId != null && termId != null) {
//            System.out.println(batchYearSemTermId);

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            model.addAttribute("academicYear", academicYear);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            model.addAttribute("department", departmentAndProgramFetch.getDepartment());

            Program program = programService.findOne(programId);
            model.addAttribute("program", program);

            Semester semester = semesterService.findOne(semId);
            model.addAttribute("semester", semester);

            Term term = termService.findOne(termId);
            model.addAttribute("term", term);
//            model.addAttribute("program", departmentAndProgramFetch.getProgram());

        }
        return "eligibleStudentEditD";
    }


    @GetMapping("/hod/timeTableEntries/addnew/{academicYearId}/{programId}/{semId}/{sectionId}/{termId}")
    public String timeTableEntries(Model model,
                                   HttpServletRequest request,
                                   @PathVariable(required = false, name = "academicYearId") Long academicYearId,
                                   @PathVariable(required = false, name = "programId") Long programId,
                                   @PathVariable(required = false, name = "semId") Long semId,
                                   @PathVariable(required = false, name = "sectionId") Long sectionId,
                                   @PathVariable(required = false, name = "termId") Long termId) {
        if (null != academicYearId && null != programId && semId != null && sectionId != null && termId != null) {
//            System.out.println(batchYearSemTermId);

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            model.addAttribute("academicYear", academicYear);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            model.addAttribute("department", departmentAndProgramFetch.getDepartment());

            Program program = programService.findOne(programId);
            model.addAttribute("program", program);

            Semester semester = semesterService.findOne(semId);
            model.addAttribute("semester", semester);

            Section section = sectionService.findOne(sectionId);
            model.addAttribute("section", section);

            Term term = termService.findOne(termId);
            model.addAttribute("term", term);

            List<Day> days = dayService.findAll();
            model.addAttribute("days", days);
//            model.addAttribute("program", departmentAndProgramFetch.getProgram());

            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);

            if (batchYearSemTerm != null) {
                Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();
                model.addAttribute("batchYearSemTermId", batchYearSemTermId);

            }
            return "NewTimeTableD";
        }
        return "403";
    }
}

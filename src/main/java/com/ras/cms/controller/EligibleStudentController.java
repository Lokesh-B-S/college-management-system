package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.EligibleStudentRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.student.StudentService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class EligibleStudentController {

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
    StudentService studentService;

    @Autowired
    EligibleStudentService eligibleStudentService;

    @Autowired
    TermService termService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @GetMapping({"/hod/selectPrerequisitesForEligibleStudents", "/admin/selectPrerequisitesForEligibleStudents"})
    public String selectbasicFieldsbeforeEligibleStudentsAllotment(Model model, HttpServletRequest request) {


        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

//        List<Batch> batches = batchService.findAll();
//        List<Department> departments = departmentService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Term> terms = termService.findAll();
//        List<Program> programs = programService.findAll();
        List<Program> programs = programService.getProgramsByDepartment(dep);


//        model.addAttribute("batches", batches);
//        model.addAttribute("departments", departments);
        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/EligibleStudentsPrerequisite";
    }


    @PostMapping({ "/hod/selectPrerequisitesForEligibleStudents", "/admin/selectPrerequisitesForEligibleStudents"})
    public String postselectofeligibleStudentPrerequisite(Model model, HttpServletRequest request,
                                              @RequestParam(required=false, name="academicYear") Long academicYearId,
                                              @RequestParam(required=false, name="program") Long programId,
                                              @RequestParam(required = false, name="semester") Long semId,
                                              @RequestParam(required=false, name="term") Long termId)

    {
//        Batch batch = batchService.findOne(batchId);
//        Department department = departmentService.findOne(departmentId);

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
//        if(studentService.existsStudentEntryByBatchAndDepartmentAndProgramAndSemester(batch, department, program, semester)) {
//
//            System.out.println("yes");
//
//            List<Student> students = studentService.getStudentsByBatchAndDepartmentAndProgramAndSemester(batch, department, program, semester);
//            System.out.println(students);


        if (request.isUserInRole("PRINCIPAL")) {
            return "redirect:/admin/listEligibleStudent/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;
        }
        else if (request.isUserInRole("DEPT_HEAD")){
            return "redirect:/hod/listEligibleStudent/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;

        }
//
//
//
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
//        } else {
//
//            return "redirect:/hod/selectPrerequisitesForEligibleStudents";
//
//        }
//        return "/EligibleStudentsPrerequisite";
        return "/403";
    }



    @GetMapping(value={"/hod/listEligibleStudent/{academicYearId}/{programId}/{semId}/{termId}", "/admin/listEligibleStudent/{academicYearId}/{programId}/{semId}/{termId}"})
    public String eligibleStudentList(Model model,
                              @PathVariable(required = true, name = "programId") Long programId,
                              @PathVariable(required = true, name = "academicYearId") Long academicYearId,
                              @PathVariable(required = true, name = "semId") Long semId,
                              @PathVariable(required = true, name = "termId") Long termId) {

        Program program = programService.findOne(programId);
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        List<EligibleStudent> eligibleStudentList;
        if (program != null && academicYear != null && semester!=null && term!=null) {
            eligibleStudentList = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
        // Sort the list based on usn
            Collections.sort(eligibleStudentList, Comparator.comparing(EligibleStudent::getUsn));

        }
        else{
            eligibleStudentList = eligibleStudentService.findAll();
            Collections.sort(eligibleStudentList, Comparator.comparing(EligibleStudent::getUsn));

        }

//        Student firstStudent = null;
//        if (!studentList.isEmpty()) {
//            firstStudent = studentList.get(0);
//        }

//        System.out.println(firstStudent.getDepartment().getDepartmentName());
//        model.addAttribute("firstStudent", firstStudent);
        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("term", term);
        model.addAttribute("semester", semester);
//
//        logger.info("Program ID: " + programId + ", Batch ID: " + batchId);

        model.addAttribute("eligibleStudentList", eligibleStudentList);
        return "/EligibleStudentList";
    }







}

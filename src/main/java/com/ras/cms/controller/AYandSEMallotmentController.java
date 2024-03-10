package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AYandSEMallotmentController {

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
    StudentService studentService;

    @GetMapping({"/admin/selectBatchDepProg"})
    public String selectbasicFields(Model model, HttpServletRequest request) {

        List<Batch> batches = batchService.findAll();
        List<Department> departments = departmentService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Program> programs = programService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("departments", departments);
        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/BatchDepartmentProgramSelectionForAYnSEMAllotment";
    }


    @PostMapping({ "/admin/selectBatchDepProg"})
    public String postselectofbatchDepProg(Model model, HttpServletRequest request,
    @RequestParam(required=false, name="batch") Long batchId,
    @RequestParam(required=false, name="department") Long departmentId,
    @RequestParam(required=false, name="program") Long programId)

    {
        Batch batch = batchService.findOne(batchId);
        Department department = departmentService.findOne(departmentId);
        Program program = programService.findOne(programId);

        if(studentService.existsStudentEntryByBatchAndDepartmentAndProgram(batch, department, program)) {

            System.out.println("yes");

            List<Student> students = studentService.getStudentsByBatchAndDepartmentAndProgram(batch, department, program);
            System.out.println(students);
            return "redirect:/admin/updateStudentDetails/" + batchId + "/" + departmentId + "/" + programId;
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
        } else {

            return "redirect:/admin/selectBatchDepProg";

        }
//        return "/403";
    }


    @GetMapping("/admin/updateStudentDetails/{batchId}/{departmentId}/{programId}" )
    public String showUpdateStudentDetailsPage(Model model,
                                               @PathVariable(required = true, name="batchId")Long batchId,
                                               @PathVariable(required = true, name="departmentId")Long departmentId,
                                               @PathVariable(required = true, name="programId")Long programId) {


        Batch batch = batchService.findOne(batchId);
        Department department = departmentService.findOne(departmentId);
        Program program = programService.findOne(programId);

        List<Student> students = studentService.getStudentsByBatchAndDepartmentAndProgram(batch, department, program);


       // List<Student> students = studentService.findAllStudents();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();

        model.addAttribute("students", students);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);

        return "AYandSEMallotmenttoSTUDENT";
    }

    @PostMapping("/admin/updateStudentDetails")
    public String individualOrBulkUpdateStudents(@RequestParam List<Long> studentIds,
                                     @RequestParam Long academicYearId,
                                     @RequestParam Long semesterId,
                                     RedirectAttributes redirectAttributes) {
        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);
        redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
        return "redirect:/admin/selectBatchDepProg";
    }

}
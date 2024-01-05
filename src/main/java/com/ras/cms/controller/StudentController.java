package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.state.StateService;
import com.ras.cms.service.student.StudentService;
import org.apache.catalina.core.ApplicationContextFacade;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private AcademicYearService academicYearService;

    @Autowired
    private SemesterService semesterService;


    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


    @GetMapping({"/admin/selectBatchProgram", "/hod/selectBatchProgram"})
    public String selectbatchProgram(Model model, HttpServletRequest request) {

        List<Batch> batches = batchService.findAll();
        List<Program> programs = programService.findAll();
        List<Semester> semesters = semesterService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("programs", programs);
        model.addAttribute("semesters", semesters);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/BatchProgramSelectionForStudentList";
    }


    @PostMapping({"/admin/selectBatchProgram", "/hod/selectBatchProgram"})
    public String postselectofbatchprogram(Model model, HttpServletRequest request,
                                           @RequestParam(required=false, name="batch") Long batchId,
                                           @RequestParam(required=false, name="program") Long programId,
                                           @RequestParam(required = false, name="semester") Long semId) {
        Batch batch = batchService.findOne(batchId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);

        if (request.isUserInRole("PRINCIPAL")) {
            if (studentService.existsStudentEntryByBatchAndProgramAndSemester(batch, program, semester)) {
                List<Student> students = studentService.getStudentsByBatchAndProgramAndSemester(batch, program, semester);
                System.out.println(students);

                return "redirect:/admin/listStudent/" + batchId + "/" + programId + "/" + semId;
            }
            else{
                return "redirect:/admin/selectBatchProgram";
            }
        }

        else if (request.isUserInRole("DEPT_HEAD")) {
            if (studentService.existsStudentEntryByBatchAndProgramAndSemester(batch, program, semester)) {
                List<Student> students = studentService.getStudentsByBatchAndProgramAndSemester(batch, program, semester);
                System.out.println(students);

                return "redirect:/hod/listStudent/" + batchId + "/" + programId + "/" + semId;
            }
            else{
                return "redirect:/hod/selectBatchProgram";
            }
        }
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);

        return "/403";
    }

    @GetMapping(value={"/admin/listStudent/{batchId}/{programId}/{semId}","/hod/listStudent/{batchId}/{programId}/{semId}","/student/listStudent"})
    public String studentList(Model model,
                              @PathVariable(required = true, name = "programId") Long programId,
                              @PathVariable(required = true, name = "batchId") Long batchId,
                              @PathVariable(required = true, name = "semId") Long semId) {

//        List<Program> programs = programService.findAll();
//        List<Batch> batches = batchService.findAll();

        System.out.println(programId);
        System.out.println(batchId);
        System.out.println(semId);

        Program program = programService.findOne(programId);
        Batch batch = batchService.findOne(batchId);
        Semester semester = semesterService.findOne(semId);

        List<Student> studentList;
        if (program != null && batch != null && semester!=null) {
            studentList = studentService.getStudentsByBatchAndProgramAndSemester(batch, program, semester);
        }
        else{
            studentList = studentService.findAll();
        }

        Student firstStudent = null;
        if (!studentList.isEmpty()) {
            firstStudent = studentList.get(0);
        }

        System.out.println(firstStudent.getDepartment().getDepartmentName());
        model.addAttribute("firstStudent", firstStudent);
        model.addAttribute("program", program);
        model.addAttribute("batch", batch);
        model.addAttribute("semester", semester);
//
//        logger.info("Program ID: " + programId + ", Batch ID: " + batchId);
//
//        return studentList;

        model.addAttribute("studentList", studentList);
        return "/studentList";
    }

    private Object getStates() {
        return  stateService.findAll();
    }



    @GetMapping(value={"/admin/studentEdit","/admin/studentEdit/{id}"})
    public String studentEditForm(Model model, @PathVariable(required = false, name = "id") Long id,
                                  @RequestParam(required = false, name="add") String add) {

        List<Department> departments = departmentService.findAll();
        List<Program> programs = programService.findAll();
        List<Batch> batches = batchService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();

        model.addAttribute("departments", departments);
        model.addAttribute("programs", programs);
        model.addAttribute("batches", batches);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);

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
        return "redirect:/admin/studentEdit";
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
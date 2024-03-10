package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.OpenElectiveGroupConfigurationRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.coursetype.CourseTypeService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ProfessionalElectiveAllotmentController {


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
        TermService termService;

        @Autowired
        SectionService sectionService;

        @Autowired
        EligibleStudentService eligibleStudentService;

//        @Autowired
//        OpenElectiveService openElectiveService;

        @Autowired
    CourseService courseService;

        @Autowired
    CourseTypeService courseTypeService;

        @Autowired
        BatchYearSemTermService batchYearSemTermService;

        @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;


        @GetMapping({"/hod/selectPrerequisiteForPEAllotment"})
        public String selectbasicFieldsbeforePEAllotment(Model model, HttpServletRequest request) {

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            Department dep = departmentAndProgramFetch.getDepartment();

            List<Batch> batches = batchService.findAll();
            List<Department> departments = departmentService.findAll();
            List<AcademicYear> academicYears = academicYearService.findAll();
            List<Semester> semesters = semesterService.findAll();
//            List<Program> programs = programService.findAll();
            List<Program> programs = programService.getProgramsByDepartment(dep);
            List<Term> terms = termService.findAll();

            model.addAttribute("batches", batches);
            model.addAttribute("departments", departments);
            model.addAttribute("programs", programs);
            model.addAttribute("academicYears", academicYears);
            model.addAttribute("semesters", semesters);
            model.addAttribute("terms", terms);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
            return "/ProfessionalElectiveAllotmenttoStudentsPrerequisite";
        }


        @PostMapping({"/hod/selectPrerequisiteForPEAllotment"})
        public String postselectofPEAllotment(Model model, HttpServletRequest request,
                                              @RequestParam(required = false, name = "academicYear") Long academicYearId,
                                              @RequestParam(required = false, name = "program") Long programId,
                                              @RequestParam(required = false, name = "semester") Long semId,
                                              @RequestParam(required = false, name = "term") Long termId) {


            AcademicYear academicYear = academicYearService.findOne(academicYearId);
//        Department department = departmentService.findOne(departmentId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);

            model.addAttribute("program", program);
            model.addAttribute("academicYear", academicYear);
            model.addAttribute("semester", semester);
            model.addAttribute("term", term);

            if (eligibleStudentService.existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)) {

                System.out.println("yes");

                List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
                System.out.println(students);
                return "redirect:/hod/updateStudentPEDetails/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
            } else {

                return "redirect:/hod/selectPrerequisiteForPEAllotment";

            }
//        return "/403";
        }




    @GetMapping("/hod/listProfessionalElectiveStudents")
    public String listStudentsofPESelected(Model model,
                                                HttpServletRequest request,
                                                @RequestParam Long academicYearId,
                                                @RequestParam Long programId,
                                                @RequestParam Long semId,
                                                @RequestParam Long termId) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();


        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);


        BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);
        Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();
        System.out.println(batchYearSemTermId);

        List<Course> PEcourses = courseService.getAllProfessionalElectiveCoursesByBatchYearSemTermIdAndDepartment(batchYearSemTermId, dep);
        System.out.println(PEcourses);
        model.addAttribute("PEcourses", PEcourses);



//        CourseType PE1courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 1");
//        CourseType PE2courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 2");
//            CourseType PE3courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 3");
//            CourseType PE4courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 4");


//        List<Course> PE1courses = courseService.getCoursesByBatchYearSemTermIdAndCourseType(batchYearSemTermId,PE1courseType);
//        List<Course> PE2courses = courseService.getCoursesByBatchYearSemTermIdAndCourseType(batchYearSemTermId,PE2courseType);

//        List<Section> sections = sectionService.findAll();
//        model.addAttribute("sections", sections);



        return "ProfessionalElectiveStudentsList";
    }

    @PostMapping("/hod/listProfessionalElectiveStudents")
    public String postListPEStudents(Model model,
                                     HttpServletRequest request,
                                     @RequestParam Long academicYearId,
                                          @RequestParam Long programId,
                                          @RequestParam Long semId,
                                          @RequestParam Long termId,
                                          @RequestParam Long PEcourseId){

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        Course selectedPEcourse = courseService.findOne(PEcourseId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);

        model.addAttribute("selectedPEcourse", selectedPEcourse);

//        List<Section> sections = sectionService.findAll();
//        model.addAttribute("sections", sections);

        BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);
        Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();

        List<Course> PEcourses = courseService.getAllProfessionalElectiveCoursesByBatchYearSemTermIdAndDepartment(batchYearSemTermId, dep);
        model.addAttribute("PEcourses", PEcourses);

        Course PEcourse = courseService.findOne(PEcourseId);
        List<EligibleStudent> professionalElectiveStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(academicYear, program, semester, term, PEcourse);

        Collections.sort(professionalElectiveStudents, Comparator.comparing(EligibleStudent::getUsn));

        model.addAttribute("professionalElectiveStudents", professionalElectiveStudents);
        System.out.println(professionalElectiveStudents);
        return "ProfessionalElectiveStudentsList";
    }

        @GetMapping("/hod/updateStudentPEDetails/{academicYearId}/{programId}/{semId}/{termId}")
        public String showUpdateStudentPEDetailsPage(Model model,
                                                     @PathVariable(required = true, name = "academicYearId") Long academicYearId,
                                                     @PathVariable(required = true, name = "programId") Long programId,
                                                     @PathVariable(required = true, name = "semId") Long semId,
                                                     @PathVariable(required = true, name = "termId") Long termId)
//                                                           @PathVariable(required = true, name="sectionId")Long sectionId)
        {


            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);

            model.addAttribute("academicYear", academicYear);
            model.addAttribute("program", program);
            model.addAttribute("semester", semester);
            model.addAttribute("term", term);

            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);

            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);
            Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();
            List<Course> courses = courseService.getCoursesByBatchYearSemTermId(batchYearSemTermId);

            CourseType PE1courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 1");
            CourseType PE2courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 2");
//            CourseType PE3courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 3");
//            CourseType PE4courseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 4");


            List<Course> PE1courses = courseService.getCoursesByBatchYearSemTermIdAndProgramAndCourseType(batchYearSemTermId, program, PE1courseType);
            List<Course> PE2courses = courseService.getCoursesByBatchYearSemTermIdAndProgramAndCourseType(batchYearSemTermId,program, PE2courseType);
//            List<Course> PE3courses = courseService.getCoursesByBatchYearSemTermIdAndCourseType(batchYearSemTermId,PE3courseType);
//            List<Course> PE4courses = courseService.getCoursesByBatchYearSemTermIdAndCourseType(batchYearSemTermId,PE4courseType);






            Map<Long, Long> currentAssignmentsForPE1 = new HashMap<>();
            for (EligibleStudent student : students) {
                if (student.getProfessionalElective1() != null) {
                    currentAssignmentsForPE1.put(student.getEligibleStudentId(), student.getProfessionalElective1().getCourseId());
                }
            }

            Map<Long, Long> currentAssignmentsForPE2 = new HashMap<>();
            for (EligibleStudent student : students) {
                if (student.getProfessionalElective2() != null) {
                    currentAssignmentsForPE2.put(student.getEligibleStudentId(), student.getProfessionalElective2().getCourseId());
                }
            }

            model.addAttribute("students", students);
            model.addAttribute("PE1courses", PE1courses);
            model.addAttribute("PE2courses", PE2courses);
            model.addAttribute("currentAssignmentsForPE1", currentAssignmentsForPE1);
            model.addAttribute("currentAssignmentsForPE2", currentAssignmentsForPE2);


            return "ProfessionalElectiveAllotmenttoStudents";
        }

        @PostMapping("/hod/updateStudentPEDetails")
        public String postStudentPEDetailsUpdate(@RequestParam Map<String, String> formData,
//                @RequestParam Map<String, String> professionalElective1Assignments,
//                                                @RequestParam Map<String, String> professionalElective2Assignments,
                                                 @RequestParam("academicYear") Long academicYearId,
                                                 @RequestParam("program") Long programId,
                                                 @RequestParam("semester") Long semId,
                                                 @RequestParam("term") Long termId,
                                                 RedirectAttributes redirectAttributes
        ) {
            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);

            for (Map.Entry<String, String> entry : formData.entrySet()) {
                String[] nameParts = entry.getKey().split("_");
                if (nameParts.length == 2) {
                    String peType = nameParts[0]; // "pe1" or "pe2"
                    Long studentId = Long.parseLong(nameParts[1]);
                    Long courseId = Long.parseLong(entry.getValue());

                    try {
                        if (peType.equals("pe1")) {
                            eligibleStudentService.assignProfessionalElective1ToEligibleStudent(studentId, courseId);
                        } else if (peType.equals("pe2")) {
                            eligibleStudentService.assignProfessionalElective2ToEligibleStudent(studentId, courseId);
                        }
                    } catch (NumberFormatException e) {
                        // Log or handle any parsing errors
                        System.err.println("Error processing entry: " + entry);
                        e.printStackTrace();
                    } catch (Exception e) {
                        // Handle other exceptions if needed
                        e.printStackTrace();
                    }
                }
            }


//
//            for (Map.Entry<String, String> entry : professionalElective1Assignments.entrySet()) {
//
//                try {
//                    if (!isNumeric(entry.getKey()) || !isNumeric(entry.getValue()) ||
//                            entry.getKey() == null || entry.getKey().isEmpty() ||
//                            entry.getValue() == null || entry.getValue().isEmpty()) {
//                        // Skip entries that are not student assignments
//                        continue;
//                    }
//
//                    Long studentId = Long.parseLong(entry.getKey());
//                    Long courseId = Long.parseLong(entry.getValue());
//
//                    eligibleStudentService.assignProfessionalElective1ToEligibleStudent(studentId, courseId);
//                } catch (NumberFormatException e) {
//                    // Log the error for troubleshooting
//                    System.err.println("Error processing entry: " + entry);
//                    e.printStackTrace();
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    // Handle the ArrayIndexOutOfBoundsException
//                    System.err.println("ArrayIndexOutOfBoundsException" + e);
//                    e.printStackTrace();
//                }
//            }
//
//
//            for (Map.Entry<String, String> entry : professionalElective2Assignments.entrySet()) {
//
//                try {
//                    if (!isNumeric(entry.getKey()) || !isNumeric(entry.getValue()) ||
//                            entry.getKey() == null || entry.getKey().isEmpty() ||
//                            entry.getValue() == null || entry.getValue().isEmpty()) {
//                        // Skip entries that are not student assignments
//                        continue;
//                    }
//
//                    Long studentId = Long.parseLong(entry.getKey());
//                    Long courseId = Long.parseLong(entry.getValue());
//
//                    eligibleStudentService.assignProfessionalElective2ToEligibleStudent(studentId, courseId);
//                } catch (NumberFormatException e) {
//                    // Log the error for troubleshooting
//                    System.err.println("Error processing entry: " + entry);
//                    e.printStackTrace();
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    // Handle the ArrayIndexOutOfBoundsException
//                    System.err.println("ArrayIndexOutOfBoundsException" + e);
//                    e.printStackTrace();
//                }
//            }

            redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
            return "redirect:/hod/updateStudentPEDetails/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;
//        return "redirect:/hod/selectPrerequisiteForSecBatchAllotment";
        }






        // Add a helper method to check if a string is numeric
        private static boolean isNumeric(String str) {
            return str.matches("-?\\d+(\\.\\d+)?");  // Check if the string is a valid number
        }


}

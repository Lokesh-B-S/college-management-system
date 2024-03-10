package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.SectionConfigurationRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SectionAllotmentController {


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
    EligibleStudentService eligibleStudentService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @Autowired
    private SectionConfigurationRepository sectionConfigurationRepository;

//    private int noOfSections = 3; // Default value, you can adjust it as needed



    @GetMapping({"/hod/selectPrerequisiteForSectionAllotment"})
    public String selectbasicFieldsbeforeSectionAllotment(Model model, HttpServletRequest request) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        List<Batch> batches = batchService.findAll();
        List<Department> departments = departmentService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Program> programs = programService.getProgramsByDepartment(dep);
        List<Term> terms = termService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("departments", departments);
        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/SectionAllotmenttoStudentsPrerequisite";
    }


    @PostMapping({ "/hod/selectPrerequisiteForSectionAllotment"})
    public String postselectofbatchDepProgSem(Model model, HttpServletRequest request,
                                              @RequestParam(required=false, name="academicYear") Long academicYearId,
                                              @RequestParam(required=false, name="program") Long programId,
                                              @RequestParam(required = false, name="semester") Long semId,
                                              @RequestParam(required = false, name="term") Long termId)

    {
//        Batch batch = batchService.findOne(batchId);
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
//        Department department = departmentService.findOne(departmentId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);

        if(eligibleStudentService.existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)) {

            System.out.println("yes");

            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
            System.out.println(students);
            return "redirect:/hod/updateStudentSectionDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
        } else {

            return "redirect:/hod/selectPrerequisiteForSectionAllotment";

        }
//        return "/403";
    }



    @GetMapping({"/hod/selectSectionToViewStudents"})
    public String selectSection(Model model, HttpServletRequest request, @RequestParam Long sectionId) {


        return "/SectionAllotmenttoStudentsPrerequisite";
    }

//
//    @PostMapping({ "/hod/selectSectionToViewStudents"})
//    public String postSelectSection(Model model, HttpServletRequest request,
//                                              @RequestParam(required=false, name="section") Long sectionId)
//
//    {
////        Batch batch = batchService.findOne(batchId);
//        AcademicYear academicYear = academicYearService.findOne(academicYearId);
////        Department department = departmentService.findOne(departmentId);
//        Program program = programService.findOne(programId);
//        Semester semester = semesterService.findOne(semId);
//        Term term = termService.findOne(termId);
//
//        if(eligibleStudentService.existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)) {
//
//            System.out.println("yes");
//
//            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
//            System.out.println(students);
//            return "redirect:/hod/updateStudentSectionDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
////            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
//        } else {
//
//            return "redirect:/hod/selectPrerequisiteForSectionAllotment";
//
//        }
////        return "/403";
//    }

//
//    @GetMapping("/hod/listSectionStudents/{academicYearId}/{programId}/{semId}/{termId}")
//    public String listStudentsofSectionSelected(Model model,
//                                                @RequestParam Long sectionId,
//                                                @PathVariable(required = true, name="academicYearId")Long academicYearId,
//                                                @PathVariable(required = true, name="programId")Long programId,
//                                                @PathVariable(required = true, name="semId")Long semId,
//                                                @PathVariable(required = true, name="termId")Long termId) {
//
//
//        List<Section> sections = sectionService.findAll();
//        model.addAttribute("sections", sections);
//
//        Section section = sectionService.findOne(sectionId);
//        List<EligibleStudent> sectionStudents = eligibleStudentService.getStudentsBySection(section);
//        model.addAttribute("sectionStudents", sectionStudents);
//
//        return "SectionStudentsList";
//
//    }



    @GetMapping("/hod/listSectionStudents")
    public String listStudentsofSectionSelected(Model model,
                                                @RequestParam Long academicYearId,
                                                @RequestParam Long programId,
                                                @RequestParam Long semId,
                                                @RequestParam Long termId) {


        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);

        List<Section> sections = sectionService.findAll();
        model.addAttribute("sections", sections);

//        Section section = sectionService.findOne(1L);
//        List<EligibleStudent> sectionStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
//        model.addAttribute("sectionStudents", sectionStudents);
//        System.out.println(sectionStudents);
//
        return "SectionStudentsList";
    }

    @PostMapping("/hod/listSectionStudents")
    public String postListSectionStudents(Model model, @RequestParam Long academicYearId,
                                          @RequestParam Long programId,
                                          @RequestParam Long semId,
                                          @RequestParam Long termId,
                                          @RequestParam Long sectionId){

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);

        List<Section> sections = sectionService.findAll();
        model.addAttribute("sections", sections);

        Section section = sectionService.findOne(sectionId);
        List<EligibleStudent> sectionStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);

        Collections.sort(sectionStudents, Comparator.comparing(EligibleStudent::getUsn));

        model.addAttribute("sectionStudents", sectionStudents);
        System.out.println(sectionStudents);
        return "sectionStudentsList";
    }


    @GetMapping("/hod/updateStudentSectionDetails/{academicYearId}/{programId}/{semId}/{termId}" )
    public String showUpdateStudentSectionDetailsPage(Model model,
                                                      @PathVariable(required = true, name="academicYearId")Long academicYearId,
                                                      @PathVariable(required = true, name="programId")Long programId,
                                                      @PathVariable(required = true, name="semId")Long semId,
                                                      @PathVariable(required = true, name="termId")Long termId) {

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);

// Retrieve or set the default value for noOfSections
        Integer noOfSections = sectionConfigurationRepository
                .findByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)
                .map(SectionConfiguration::getNoOfSections)
                .orElse(5); // Set a default value (you can change it as needed)

        model.addAttribute("noOfSections", noOfSections);
        System.out.println("No of Sections: " + noOfSections);


        List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
        //List<Section> sections = sectionService.findAll();
        List<Section> sections = sectionService.findAll().subList(0, noOfSections);


        Map<Long, Long> currentAssignments = new HashMap<>();
        for (EligibleStudent student : students) {
            if (student.getSection() != null) {
                currentAssignments.put(student.getEligibleStudentId(), student.getSection().getSectionId());
            }
        }

        model.addAttribute("students", students);
        model.addAttribute("sections", sections);
        model.addAttribute("currentAssignments", currentAssignments);

        return "SectionAllotmenttoStudents";
    }

    @PostMapping("/hod/updateStudentSectionDetails")
    public String postStudentSectionDetailsUpdate(@RequestParam Map<String, String> sectionAssignments,
                                                  @RequestParam("academicYear") Long academicYearId,
                                                  @RequestParam("program") Long programId,
                                                  @RequestParam("semester") Long semId,
                                                  @RequestParam("term") Long termId,
                                                  @RequestParam("noOfSections") Integer noOfSections,
                                                  RedirectAttributes redirectAttributes) {
//        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        System.out.println("No of Sections: " + noOfSections);
        System.out.println(academicYear.getYear());
        System.out.println(program.getProgramName());




        // Save or update the noOfSections value in the database for the selected academicYear, program, sem, term
        SectionConfiguration sectionConfiguration = sectionConfigurationRepository
                .findByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)
                .orElse(null);

                // Check if sectionConfiguration is null
                    if (sectionConfiguration == null) {
                        // If it's null, create a new SectionConfiguration with the default value of 5
                        sectionConfiguration = new SectionConfiguration();
                        sectionConfiguration.setNoOfSections(5);  // Set a default value (you can change it as needed)
                    }

        // Retrieve previousNoOfSections
        Integer previousNoOfSections = sectionConfiguration.getNoOfSections();

        // Compare the new and previous noOfSections
        if (noOfSections < previousNoOfSections) {
            // If decreased, unassign students from sections beyond the new noOfSections

            // Find the student IDs to unassign
            List<Long> studentIdsToUnassign = eligibleStudentService.findStudentsToUnassign(academicYear, program, semester, term, noOfSections);
            System.out.println(studentIdsToUnassign);
            // Unassign students
            for (Long studentId : studentIdsToUnassign) {
                System.out.println("Unassigning student with ID: " + studentId);
                eligibleStudentService.unassignSectionFromEligibleStudent(studentId);
                System.out.println("Unassigned student : " + studentId);
            }
        }

        if (sectionConfiguration.getNoOfSections() == null || !sectionConfiguration.getNoOfSections().equals(noOfSections)) {
            sectionConfiguration.setNoOfSections(noOfSections);
            sectionConfiguration.setAcademicYear(academicYear);
            sectionConfiguration.setProgram(program);
            sectionConfiguration.setSemester(semester);
            sectionConfiguration.setTerm(term);
            sectionConfigurationRepository.save(sectionConfiguration);
    }


        for (Map.Entry<String, String> entry : sectionAssignments.entrySet()) {


try{
//            System.out.println(entry);
    if (entry.getKey().equals("semester") || entry.getKey().equals("academicYear") || entry.getKey().equals("program") || entry.getKey().equals("term") || entry.getKey().equals("noOfSections")) {
        // Skip entries that are not student assignments
        continue;
    }
//            try{
             Long studentId = Long.parseLong(entry.getKey());
//             Long studentId = Long.parseLong(entry.getKey().split("-")[1]);
            Long sectionId = Long.parseLong(entry.getValue());

            // Update the student's section in the database
            eligibleStudentService.assignSectionToEligibleStudent(studentId, sectionId);
        }  catch (NumberFormatException e) {
            // Log the error for troubleshooting
            System.err.println("Error processing entry: " + entry);
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle the ArrayIndexOutOfBoundsException
            System.err.println("ArrayIndexOutOfBoundsException" + e);
            e.printStackTrace();
        }
        }

        redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
        return "redirect:/hod/updateStudentSectionDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
//        return "redirect:/hod/selectPrerequisiteForSectionAllotment";
    }



//    @GetMapping({"/hod/selectPrerequisiteForSectionAllotment"})
//    public String selectbasicFieldsbeforeSectionAllotment(Model model, HttpServletRequest request) {
//
//        List<Batch> batches = batchService.findAll();
//        List<Department> departments = departmentService.findAll();
//        List<AcademicYear> academicYears = academicYearService.findAll();
//        List<Semester> semesters = semesterService.findAll();
//        List<Program> programs = programService.findAll();
//        List<Term> terms = termService.findAll();
//
//        model.addAttribute("batches", batches);
//        model.addAttribute("departments", departments);
//        model.addAttribute("programs", programs);
//        model.addAttribute("academicYears", academicYears);
//        model.addAttribute("semesters", semesters);
//        model.addAttribute("terms", terms);
//
//
//        SectionConfiguration sectionConfiguration = new SectionConfiguration();
//        model.addAttribute("sectionConfiguration", sectionConfiguration);
////        model.addAttribute("noOfSections", sectionConfiguration.getNoOfSections() != null ? sectionConfiguration.getNoOfSections() : 3);
//
////        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
//        return "/SectionAllotmenttoStudentsPrerequisite";
//    }
//
//
//    @PostMapping({ "/hod/selectPrerequisiteForSectionAllotment"})
//    public String postselectofbatchDepProgSem(Model model,
//                                              @ModelAttribute("sectionConfiguration") SectionConfiguration sectionConfiguration,
//                                              HttpServletRequest request,
//                                           @RequestParam(required=false, name="academicYear") Long academicYearId,
//                                           @RequestParam(required=false, name="program") Long programId,
//                                           @RequestParam(required = false, name="semester") Long semId,
//                                              @RequestParam(required = false, name="term") Long termId)
//
//    {
////        Batch batch = batchService.findOne(batchId);
//        AcademicYear academicYear = academicYearService.findOne(academicYearId);
////        Department department = departmentService.findOne(departmentId);
//        Program program = programService.findOne(programId);
//        Semester semester = semesterService.findOne(semId);
//        Term term = termService.findOne(termId);
//
//        model.addAttribute("program", program);
//        model.addAttribute("academicYear", academicYear);
//        model.addAttribute("semester", semester);
//        model.addAttribute("term", term);
//
//        SectionConfiguration savedConfiguration = sectionConfigurationRepository
//                .findByAcademicYearAndProgramAndSemesterAndTerm(
//                        sectionConfiguration.getAcademicYear(),
//                        sectionConfiguration.getProgram(),
//                        sectionConfiguration.getSemester(),
//                        sectionConfiguration.getTerm());
//
//// Save the updated configuration back to the database
//        savedConfiguration.setNoOfSections(sectionConfiguration.getNoOfSections());
//        // Set other fields as needed
//
//        sectionConfigurationRepository.save(savedConfiguration);
//
//        model.addAttribute("savedConfiguration", savedConfiguration);
//
//
//        if(eligibleStudentService.existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)) {
//
//            System.out.println("yes");
//
//            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
//            System.out.println(students);
//
//
//            System.out.println("noOfSections: "+ sectionConfiguration.getNoOfSections());
//            return "redirect:/hod/updateStudentSectionDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
////            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
//        } else {
//
//            return "redirect:/hod/selectPrerequisiteForSectionAllotment";
//
//        }
////        return "/403";
//    }
//
//
//
//        @GetMapping("/hod/listSectionStudents")
//    public String listStudentsofSectionSelected(Model model,
//                                                @RequestParam Long academicYearId,
//                                                @RequestParam Long programId,
//                                                @RequestParam Long semId,
//                                                @RequestParam Long termId) {
//
//
//            AcademicYear academicYear = academicYearService.findOne(academicYearId);
//            Program program = programService.findOne(programId);
//            Semester semester = semesterService.findOne(semId);
//            Term term = termService.findOne(termId);
//
//            model.addAttribute("academicYear", academicYear);
//            model.addAttribute("program", program);
//            model.addAttribute("semester", semester);
//            model.addAttribute("term", term);
//
//        List<Section> sections = sectionService.findAll();
//        model.addAttribute("sections", sections);
//
////        Section section = sectionService.findOne(1L);
////        List<EligibleStudent> sectionStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
////        model.addAttribute("sectionStudents", sectionStudents);
////        System.out.println(sectionStudents);
////
//        return "SectionStudentsList";
//    }
//
//    @PostMapping("/hod/listSectionStudents")
//    public String postListSectionStudents(Model model, @RequestParam Long academicYearId,
//                                          @RequestParam Long programId,
//                                          @RequestParam Long semId,
//                                          @RequestParam Long termId,
//                                          @RequestParam Long sectionId){
//
//        AcademicYear academicYear = academicYearService.findOne(academicYearId);
//        Program program = programService.findOne(programId);
//        Semester semester = semesterService.findOne(semId);
//        Term term = termService.findOne(termId);
//
//        model.addAttribute("academicYear", academicYear);
//        model.addAttribute("program", program);
//        model.addAttribute("semester", semester);
//        model.addAttribute("term", term);
//
//        List<Section> sections = sectionService.findAll();
//        model.addAttribute("sections", sections);
//
//        Section section = sectionService.findOne(sectionId);
//        List<EligibleStudent> sectionStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
//
//        Collections.sort(sectionStudents, Comparator.comparing(EligibleStudent::getUsn));
//
//        model.addAttribute("sectionStudents", sectionStudents);
//        System.out.println(sectionStudents);
//         return "sectionStudentsList";
//    }
//
//
//    @GetMapping("/hod/updateStudentSectionDetails/{academicYearId}/{programId}/{semId}/{termId}" )
//    public String showUpdateStudentSectionDetailsPage(Model model,
//                                               @PathVariable(required = true, name="academicYearId")Long academicYearId,
//                                               @PathVariable(required = true, name="programId")Long programId,
//                                               @PathVariable(required = true, name="semId")Long semId,
//                                               @PathVariable(required = true, name="termId")Long termId) {
//
//
////        System.out.println(noOfSections);
//
////        Batch batch = batchService.findOne(batchId);
////        Department department = departmentService.findOne(departmentId);
//        AcademicYear academicYear = academicYearService.findOne(academicYearId);
//        Program program = programService.findOne(programId);
//        Semester semester = semesterService.findOne(semId);
//        Term term = termService.findOne(termId);
//
////        model.addAttribute("batch", batch);
////        model.addAttribute("department", department);
//        model.addAttribute("academicYear", academicYear);
//        model.addAttribute("program", program);
//        model.addAttribute("semester", semester);
//        model.addAttribute("term", term);
//
//
//        List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
//        List<Section> sections = sectionService.findAll();
//
//        Map<Long, Long> currentAssignments = new HashMap<>();
//        for (EligibleStudent student : students) {
//            if (student.getSection() != null) {
//                currentAssignments.put(student.getEligibleStudentId(), student.getSection().getSectionId());
//            }
//        }
//
////        model.addAttribute("noOfSections", noOfSections);
//        model.addAttribute("students", students);
//        model.addAttribute("sections", sections);
//        model.addAttribute("currentAssignments", currentAssignments);
//
//        return "SectionAllotmenttoStudents";
//    }
//
//    @PostMapping("/hod/updateStudentSectionDetails")
//    public String postStudentSectionDetailsUpdate(@RequestParam Map<String, String> sectionAssignments,
//                                                 @RequestParam int noOfSections,
//                                                 RedirectAttributes redirectAttributes) {
////        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);
//
//        // Update the number of sections in the model
////        this.noOfSections = noOfSections;
//
//        // Get the existing section assignments
//        Map<Long, Long> currentAssignments = new HashMap<>();
//        for (Map.Entry<String, String> entry : sectionAssignments.entrySet()) {
//            Long studentId = Long.parseLong(entry.getKey().split("-")[1]);
//            Long sectionId = Long.parseLong(entry.getValue());
//            currentAssignments.put(studentId, sectionId);
//        }
//
//
//
//        // Iterate through the form data to update section assignments
//        for (Map.Entry<String, String> entry : sectionAssignments.entrySet()) {
//            String[] keyParts = entry.getKey().split("-");
//            if (keyParts.length == 2) {
//                Long studentId = Long.parseLong(keyParts[1]);
//                Long newSectionId = Long.parseLong(entry.getValue());
//
//                // Check if the section assignment has changed
//                if (!Objects.equals(currentAssignments.get(studentId), newSectionId)) {
//                    // Handle unassignment from the previous section if needed
//                    Long previousSectionId = currentAssignments.get(studentId);
//                    if (previousSectionId != null) {
//                        // Implement logic to unassign student from the previous section
//                        // For example, update the database or perform any other necessary steps
//                        eligibleStudentService.unassignSectionFromEligibleStudent(studentId, previousSectionId);
//
//                    }
//
//                    // Update the student's section in the database
//                    eligibleStudentService.assignSectionToEligibleStudent(studentId, newSectionId);
//                }
//            }
//        }
//
//
//        // Update the student's section in the database
////        for (Map.Entry<String, String> entry : sectionAssignments.entrySet()) {
////            Long studentId = Long.parseLong(entry.getKey().split("-")[1]);
////            Long sectionId = Long.parseLong(entry.getValue());
////
////            // Update the student's section in the database
////            eligibleStudentService.assignSectionToEligibleStudent(studentId, sectionId);
////        }
//
//        redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
//        return "redirect:/hod/selectPrerequisiteForSectionAllotment";
//    }
}


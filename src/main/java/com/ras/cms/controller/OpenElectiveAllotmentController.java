package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.OpenElectiveGroupConfigurationRepository;
import com.ras.cms.repository.SecBatchConfigurationRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.secBatchCount.SecBatchCountService;
import com.ras.cms.service.secbatch.SecBatchService;
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
import java.util.stream.Collectors;

@Controller
public class OpenElectiveAllotmentController {


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

        @Autowired
    OpenElectiveService openElectiveService;

        @Autowired
    OpenElectiveGroupConfigurationRepository openElectiveGroupConfigurationRepository;

        @Autowired
    BatchYearSemTermService batchYearSemTermService;

        @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;



    @GetMapping({"/hod/selectPrerequisiteForOEAllotment"})
    public String selectbasicFieldsbeforeOEAllotment(Model model, HttpServletRequest request) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        List<Batch> batches = batchService.findAll();
        List<Department> departments = departmentService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
//        List<Program> programs = programService.findAll();
        List<Program> programs = programService.getProgramsByDepartment(dep);
        List<Term> terms = termService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("departments", departments);
        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/OpenElectiveAllotmenttoStudentsPrerequisite";
    }


    @PostMapping({ "/hod/selectPrerequisiteForOEAllotment"})
    public String postselectofOEAllotment(Model model, HttpServletRequest request,
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
            return "redirect:/hod/updateStudentOEDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
        } else {

            return "redirect:/hod/selectPrerequisiteForOEAllotment";

        }
//        return "/403";
    }

    @GetMapping("/hod/listOpenElectiveStudents")
    public String listStudentsofOESelected(Model model,
                                           HttpServletRequest request,
                                           @RequestParam Long academicYearId,
                                           @RequestParam Long programId,
                                           @RequestParam Long semId,
                                           @RequestParam Long termId) {

//        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
//        Department dep = departmentAndProgramFetch.getDepartment();

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

        List<OpenElective> OEcourses = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);
        System.out.println(OEcourses);
        model.addAttribute("OEcourses", OEcourses);


        return "OpenElectiveStudentsList";
    }

    @PostMapping("/hod/listOpenElectiveStudents")
    public String postListOEStudents(Model model,
                                     HttpServletRequest request,
                                     @RequestParam Long academicYearId,
                                     @RequestParam Long programId,
                                     @RequestParam Long semId,
                                     @RequestParam Long termId,
                                     @RequestParam Long OEcourseId){

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

//        List<Section> sections = sectionService.findAll();
//        model.addAttribute("sections", sections);

        BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);
        Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();

        List<OpenElective> OEcourses = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);
        model.addAttribute("OEcourses", OEcourses);

        OpenElective OEcourse = openElectiveService.findOne(OEcourseId);
        List<EligibleStudent> openElectiveStudents = eligibleStudentService.getStudentsByAcademicYearAndAndSemesterAndTermAndOpenElective(academicYear, semester, term, OEcourse);

        Collections.sort(openElectiveStudents, Comparator.comparing(EligibleStudent::getUsn));

        model.addAttribute("openElectiveStudents", openElectiveStudents);
        System.out.println(openElectiveStudents);
        return "OpenElectiveStudentsList";
    }


//
//    @GetMapping("/hod/listSecBatchStudents")
//        public String listStudentsofSecBatchSelected(Model model,
//                                                     @RequestParam Long academicYearId,
//                                                     @RequestParam Long programId,
//                                                     @RequestParam Long semId,
//                                                     @RequestParam Long termId) {
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
//            List<Section> sections = sectionService.findAll();
//            model.addAttribute("sections", sections);
//
//            List<SecBatch> secBatches = secBatchService.getBatchesByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
//            model.addAttribute("secBatches", secBatches);
//
////
//            return "SecBatchStudentsList";
//        }
//
//        @PostMapping("/hod/listSecBatchStudents")
//        public String postListSecBatchStudents(Model model, @RequestParam Long academicYearId,
//                                               @RequestParam Long programId,
//                                               @RequestParam Long semId,
//                                               @RequestParam Long termId,
//                                               @RequestParam Long sectionId,
//                                               @RequestParam Long secBatchId){
//
//            AcademicYear academicYear = academicYearService.findOne(academicYearId);
//            Program program = programService.findOne(programId);
//            Semester semester = semesterService.findOne(semId);
//            Term term = termService.findOne(termId);
//            Section section = sectionService.findOne(sectionId);
//
//            model.addAttribute("academicYear", academicYear);
//            model.addAttribute("program", program);
//            model.addAttribute("semester", semester);
//            model.addAttribute("term", term);
//            model.addAttribute("section", section);
//
//
//            List<Section> sections = sectionService.findAll();
//            model.addAttribute("sections", sections);
//
//            List<SecBatch> secBatches = secBatchService.findAll();
//            model.addAttribute("secBatches", secBatches);
//
//
//            SecBatch secBatch = secBatchService.findOne(secBatchId);
//            List<EligibleStudent> secBatchStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndAndSectionAndSecBatch(academicYear, program, semester, term, section, secBatch);
//
//            Collections.sort(secBatchStudents, Comparator.comparing(EligibleStudent::getUsn));
//
//            model.addAttribute("secBatchStudents", secBatchStudents);
//            System.out.println(secBatchStudents);
//            return "SecBatchStudentsList";
//        }
//

        @GetMapping("/hod/updateStudentOEDetails/{academicYearId}/{programId}/{semId}/{termId}" )
        public String showUpdateStudentOEDetailsPage(Model model,
                                                           @PathVariable(required = true, name="academicYearId")Long academicYearId,
                                                           @PathVariable(required = true, name="programId")Long programId,
                                                           @PathVariable(required = true, name="semId")Long semId,
                                                           @PathVariable(required = true, name="termId")Long termId)
//                                                           @PathVariable(required = true, name="sectionId")Long sectionId)
        {


            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);
//            Section section = sectionService.findOne(sectionId);

            model.addAttribute("academicYear", academicYear);
            model.addAttribute("program", program);
            model.addAttribute("semester", semester);
            model.addAttribute("term", term);
//            model.addAttribute("section", section);

//            // Retrieve or set the default value for noOfSections
//            Integer noOfOEgroups = openElectiveGroupConfigurationRepository
//                    .findByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)
//                    .map(OpenElectiveGroupConfiguration::getNoOfOpenElectiveGroups)
//                    .orElse(5); // Set a default value (you can change it as needed)
//
//            model.addAttribute("noOfOEgroups", noOfOEgroups);
//            System.out.println("No of noOfOEgroups: " + noOfOEgroups);






//            for (int i = 0; i <= noOfBatches; i++) {
//                String batchName;
//                if(i==0){
//                    batchName = section.getSec() + " (Not Applicable)";
//                }
//                else {
//                    batchName = section.getSec() + i;
//                }// Adjust the batch name creation logic based on your requirements
//                secBatchService.createSecBatch(batchName, academicYear, program, semester, term, section);
//            }
//
            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);

           BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);
           Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();
            List<OpenElective> openElectives = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);

////        List<SecBatch> secBatches = secBatchService.getBatchesByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section).subList(0, noOfBatches);
//
//
//// Ensure the default batch is included in the list if it exists
//            SecBatch defaultBatch = secBatches.stream()
//                    .filter(batch -> batch.getSecBatchName().equals(section.getSec() + " (Not Applicable)"))
//                    .findFirst()
//                    .orElseGet(() -> {
//                        SecBatch newDefaultBatch = new SecBatch();
//                        newDefaultBatch.setSecBatchName(section.getSec() + " (Not Applicable)");
//                        return newDefaultBatch;
//                    });
//
//            secBatches.add(0, defaultBatch);  // Add the default batch to the beginning of the list
//
//// Limit the list size to noOfBatches
//            secBatches = secBatches.subList(0, Math.min(noOfBatches + 1, secBatches.size()));





            Map<Long, Long> currentAssignments = new HashMap<>();
            for (EligibleStudent student : students) {
                if (student.getOpenElective() != null) {
                    currentAssignments.put(student.getEligibleStudentId(), student.getOpenElective().getOpenElectiveId());
                }
            }

            model.addAttribute("students", students);
            model.addAttribute("openElectives", openElectives);
            model.addAttribute("currentAssignments", currentAssignments);

            return "OpenElectiveAllotmenttoStudents";
        }

        @PostMapping("/hod/updateStudentOEDetails")
        public String postStudentOEDetailsUpdate(@RequestParam Map<String, String> openElectiveAssignments,
                                                       @RequestParam("academicYear") Long academicYearId,
                                                       @RequestParam("program") Long programId,
                                                       @RequestParam("semester") Long semId,
                                                       @RequestParam("term") Long termId,
//                                                       @RequestParam("section")Long sectionId,
//                                                       @RequestParam("noOfBatches") Integer noOfBatches,
                                                       RedirectAttributes redirectAttributes
        ) {
//        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);
            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);
//            Section section = sectionService.findOne(sectionId);

//            System.out.println("noOfBatches: " + noOfBatches);
            System.out.println(academicYear.getYear());
            System.out.println(program.getProgramName());
//
//// Save or update the noOfBatches value in the database for the selected academicYear, program, sem, term
//            SecBatchConfiguration secBatchConfiguration = secBatchConfigurationRepository
//                    .findByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section)
//                    .orElse(null);
//
//            // Check if secBatchConfiguration is null
//            if (secBatchConfiguration == null) {
//                // If it's null, create a new SectionConfiguration with the default value of 5
//                secBatchConfiguration = new SecBatchConfiguration();
//                secBatchConfiguration.setNoOfBatches(5);  // Set a default value (you can change it as needed)
//            }
//
//
//
//            if (secBatchConfiguration.getNoOfBatches() == null || !secBatchConfiguration.getNoOfBatches().equals(noOfBatches)) {
//                secBatchConfiguration.setNoOfBatches(noOfBatches);
//                secBatchConfiguration.setAcademicYear(academicYear);
//                secBatchConfiguration.setProgram(program);
//                secBatchConfiguration.setSemester(semester);
//                secBatchConfiguration.setTerm(term);
//                secBatchConfiguration.setSection(section);
//                secBatchConfigurationRepository.save(secBatchConfiguration);
//            }



            for (Map.Entry<String, String> entry : openElectiveAssignments.entrySet()) {

                try {
                    if (!isNumeric(entry.getKey()) || !isNumeric(entry.getValue()) ||
                            entry.getKey() == null || entry.getKey().isEmpty() ||
                            entry.getValue() == null || entry.getValue().isEmpty()) {
//                if (entry.getKey().equals("semester") || entry.getKey().equals("academicYear") || entry.getKey().equals("program") || entry.getKey().equals("term") || entry.getKey().equals("section") || entry.getKey().equals("noOfBatches")) {
                        // Skip entries that are not student assignments
                        continue;
                    }

//            Long studentId = Long.parseLong(entry.getKey().split("-")[1]);
                    Long studentId = Long.parseLong(entry.getKey());
                    Long openElectiveId = Long.parseLong(entry.getValue());

                    eligibleStudentService.assignOpenElectiveToEligibleStudent(studentId, openElectiveId);
                } catch (NumberFormatException e) {
                    System.err.println("Error processing entry: " + entry);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("ArrayIndexOutOfBoundsException" + e);
                    e.printStackTrace();
                }
            }


            redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
//        return "redirect:/hod/updateStudentSecBatchDetails/" + academicYearId + "/" + programId + "/" + semId + "/" + termId + "/" + sectionId;

            return "redirect:/hod/updateStudentOEDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
//        return "redirect:/hod/selectPrerequisiteForSecBatchAllotment";
        }




        // Add a helper method to check if a string is numeric
        private static boolean isNumeric(String str) {
            return str.matches("-?\\d+(\\.\\d+)?");  // Check if the string is a valid number
        }

}



// Retrieve previousNoOfSections
//  Integer previousNoOfSecBatches = secBatchConfiguration.getNoOfBatches();

// Compare the new and previous noOfSections
//  if (noOfBatches < previousNoOfSecBatches) {
// If decreased, unassign students from sections beyond the new noOfBatches

//            // Find the student IDs to unassign
//            List<Long> studentIdsToUnassign = eligibleStudentService.findStudentsToUnassign(academicYear, program, semester, term, noOfBatches);
//            System.out.println(studentIdsToUnassign);
//            // Unassign students
//            for (Long studentId : studentIdsToUnassign) {
//                System.out.println("Unassigning student with ID: " + studentId);
//                eligibleStudentService.unassignSecBatchFromEligibleStudent(studentId);
//                System.out.println("Unassigned student : " + studentId);
//            }
//       }


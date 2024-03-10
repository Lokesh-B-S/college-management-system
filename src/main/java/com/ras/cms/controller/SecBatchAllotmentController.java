package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.SecBatchConfigurationRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
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

import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SecBatchAllotmentController {


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
    SecBatchService secBatchService;

    @Autowired
    TermService termService;

    @Autowired
    SectionService sectionService;
//
    @Autowired
    SecBatchCountService secBatchCountService;


//    @Autowired
//    StudentService studentService;

    @Autowired
    EligibleStudentService eligibleStudentService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @Autowired
    SecBatchConfigurationRepository secBatchConfigurationRepository;


    @GetMapping({"/hod/editNoOfBatches/{academicYearId}/{programId}/{semId}/{termId}"})
    public String editNoOfBatches(Model model,
                                  @PathVariable(required = true, name="academicYearId")Long academicYearId,
                                  @PathVariable(required = true, name="programId")Long programId,
                                  @PathVariable(required = true, name="semId")Long semId,
                                  @PathVariable(required = true, name="termId")Long termId
                                  ){

        List<Section> sections = sectionService.findAll();
        model.addAttribute("sections", sections);

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);


        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
//        model.addAttribute("sections", sections);

        // Retrieve existing batch counts for the given academic year, program, semester, and term
        List<secBatchCount> existingBatchCounts = secBatchCountService.getBatchCountRowsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);

        System.out.println(existingBatchCounts);

        // Create a map for easy retrieval in Thymeleaf
        Map<Long, Integer> existingBatchCountsMap = existingBatchCounts.stream()
                .collect(Collectors.toMap(batchCount -> batchCount.getSection().getSectionId(), secBatchCount::getBatchCount));

        model.addAttribute("existingBatchCountsMap", existingBatchCountsMap);

        // Add existing batch counts to the model
        model.addAttribute("existingBatchCounts", existingBatchCounts);

        return "NoOfBatchesEdit";
    }

    @PostMapping("/hod/editNoOfBatches")
    public String postNoOfBatches(Model model, @RequestParam Map<String, String> batchCountMap,
                                  @RequestParam Long academicYearId,
                                  @RequestParam Long programId,
                                  @RequestParam Long semId,
                                  @RequestParam Long termId,

                                  RedirectAttributes redirectAttributes){


        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        // Filter out the academicYearId, programId, semId, and termId from batchCountMap
        batchCountMap = batchCountMap.entrySet().stream()
                .filter(entry -> !Arrays.asList("academicYearId", "programId", "semId", "termId").contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        // Update or save the batch counts based on the user input
        for (Map.Entry<String, String> entry : batchCountMap.entrySet()) {
            Long sectionId = Long.parseLong(entry.getKey());
            String batchCountValue = entry.getValue();

            // Check if batchCountValue is not empty before parsing
            if (batchCountValue != null && !batchCountValue.isEmpty()) {
                Integer batchCount = Integer.parseInt(batchCountValue);

                Section section = sectionService.findOne(sectionId);
                System.out.println(sectionId);
                System.out.println(batchCount);


                // Update or save the batch count in the database
                secBatchCountService.updateOrSaveBatchCount(academicYear, program, semester, term, section, batchCount);
            }
        }

        redirectAttributes.addFlashAttribute("successMessage", "Batch counts updated successfully!");
        return "redirect:/hod/editNoOfBatches/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;
    }

    @GetMapping({"/hod/selectPrerequisiteForSecBatchAllotment"})
    public String selectbasicFieldsbeforeSecBatchAllotment(Model model, HttpServletRequest request) {
        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();


        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Program> programs = programService.getProgramsByDepartment(dep);
        List<Term> terms = termService.findAll();
        List<Section> sections = sectionService.findAll();

        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);
        model.addAttribute("sections", sections);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/SecBatchAllotmenttoStudentsPrerequisite";
    }


    @PostMapping({ "/hod/selectPrerequisiteForSecBatchAllotment"})
    public String postselectofbatchDepProgSemSection(Model model, HttpServletRequest request,
                                                     @RequestParam(required=false, name="academicYear") Long academicYearId,
                                                     @RequestParam(required=false, name="program") Long programId,
                                                     @RequestParam(required = false, name="semester") Long semId,
                                                     @RequestParam(required = false, name="term") Long termId,
                                                     @RequestParam(required = false, name="section")Long sectionId)

    {
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        Section section = sectionService.findOne(sectionId);

        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
        model.addAttribute("section", section);

        if(eligibleStudentService.existsEligibleStudentEntryByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section)) {
            return "redirect:/hod/updateStudentSecBatchDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId + "/" + sectionId;
        } else {
            return "redirect:/hod/selectPrerequisiteForSecBatchAllotment";
        }
//        return "/403";
    }





    @GetMapping("/hod/listSecBatchStudents")
    public String listStudentsofSecBatchSelected(Model model,
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

        List<SecBatch> secBatches = secBatchService.getBatchesByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
        model.addAttribute("secBatches", secBatches);

//
        return "SecBatchStudentsList";
    }

    @PostMapping("/hod/listSecBatchStudents")
    public String postListSecBatchStudents(Model model, @RequestParam Long academicYearId,
                                           @RequestParam Long programId,
                                           @RequestParam Long semId,
                                           @RequestParam Long termId,
                                           @RequestParam Long sectionId,
                                           @RequestParam Long secBatchId){

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        Section section = sectionService.findOne(sectionId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
        model.addAttribute("section", section);


        List<Section> sections = sectionService.findAll();
        model.addAttribute("sections", sections);

        List<SecBatch> secBatches = secBatchService.findAll();
        model.addAttribute("secBatches", secBatches);


        SecBatch secBatch = secBatchService.findOne(secBatchId);
        List<EligibleStudent> secBatchStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndAndSectionAndSecBatch(academicYear, program, semester, term, section, secBatch);

        Collections.sort(secBatchStudents, Comparator.comparing(EligibleStudent::getUsn));

        model.addAttribute("secBatchStudents", secBatchStudents);
        System.out.println(secBatchStudents);
        return "SecBatchStudentsList";
    }


    @GetMapping("/hod/updateStudentSecBatchDetails/{academicYearId}/{programId}/{semId}/{termId}/{sectionId}" )
    public String showUpdateStudentSecBatchDetailsPage(Model model,
                                                       @PathVariable(required = true, name="academicYearId")Long academicYearId,
                                                       @PathVariable(required = true, name="programId")Long programId,
                                                       @PathVariable(required = true, name="semId")Long semId,
                                                       @PathVariable(required = true, name="termId")Long termId,
                                                       @PathVariable(required = true, name="sectionId")Long sectionId) {


        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        Section section = sectionService.findOne(sectionId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
        model.addAttribute("section", section);

        // Retrieve or set the default value for noOfSections
        Integer noOfBatches = secBatchConfigurationRepository
                .findByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section)
                .map(SecBatchConfiguration::getNoOfBatches)
                .orElse(2); // Set a default value (you can change it as needed)

        model.addAttribute("noOfBatches", noOfBatches);
        System.out.println("No of noOfBatches: " + noOfBatches);

//        List<SecBatch> secBatches = new ArrayList<>();

        for (int i = 0; i <= noOfBatches; i++) {
            String batchName;
            if(i==0){
                batchName = section.getSec() + " (Not Applicable)";
            }
            else {
                batchName = section.getSec() + i;
            }
          secBatchService.createSecBatch(batchName, academicYear, program, semester, term, section);
           // secBatches.add(createdBatch);
        }

        List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
//        List<SecBatch> secBatches = secBatchService.getBatchesByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section).subList(0, noOfBatches);

        List<SecBatch> secBatches = secBatchService.getBatchesByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);

// Ensure the default batch is included in the list if it exists
//        SecBatch defaultBatch = secBatches.stream()
//                .filter(batch -> batch.getSecBatchName().equals(section.getSec() + " (Not Applicable)"))
//                .findFirst()
//                .orElseGet(() -> {
//                    SecBatch newDefaultBatch = new SecBatch();
//                    newDefaultBatch.setSecBatchName(section.getSec() + " (Not Applicable)");
//                    return newDefaultBatch;
//                });

//        secBatches.add(0, defaultBatch);  // Add the default batch to the beginning of the list

        System.out.println("secBatches before" + secBatches);

// Limit the list size to noOfBatches
        secBatches = secBatches.subList(0, Math.min(noOfBatches + 1, secBatches.size()));

        System.out.println("secBatches after" + secBatches);


        Map<Long, Long> currentAssignments = new HashMap<>();
        for (EligibleStudent student : students) {
            if (student.getSecBatch() != null) {
                currentAssignments.put(student.getEligibleStudentId(), student.getSecBatch().getSecBatchId());
            }
        }

        model.addAttribute("students", students);
        model.addAttribute("secBatches", secBatches);
        model.addAttribute("currentAssignments", currentAssignments);

        return "SecBatchAllotmenttoStudents";
    }

    @PostMapping("/hod/updateStudentSecBatchDetails")
    public String postStudentSecBatchDetailsUpdate(@RequestParam Map<String, String> secBatchAssignments,
                                                   @RequestParam("academicYear") Long academicYearId,
                                                   @RequestParam("program") Long programId,
                                                   @RequestParam("semester") Long semId,
                                                   @RequestParam("term") Long termId,
                                                   @RequestParam("section")Long sectionId,
                                                   @RequestParam("noOfBatches") Integer noOfBatches,
                                                   RedirectAttributes redirectAttributes
                                                   ) {
//        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        Section section = sectionService.findOne(sectionId);

        System.out.println("noOfBatches: " + noOfBatches);
        System.out.println(academicYear.getYear());
        System.out.println(program.getProgramName());

// Save or update the noOfBatches value in the database for the selected academicYear, program, sem, term
        SecBatchConfiguration secBatchConfiguration = secBatchConfigurationRepository
                .findByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section)
                .orElse(null);

        // Check if secBatchConfiguration is null
        if (secBatchConfiguration == null) {
            // If it's null, create a new SectionConfiguration with the default value of 5
            secBatchConfiguration = new SecBatchConfiguration();
            secBatchConfiguration.setNoOfBatches(5);  // Set a default value (you can change it as needed)
        }



        if (secBatchConfiguration.getNoOfBatches() == null || !secBatchConfiguration.getNoOfBatches().equals(noOfBatches)) {
            secBatchConfiguration.setNoOfBatches(noOfBatches);
            secBatchConfiguration.setAcademicYear(academicYear);
            secBatchConfiguration.setProgram(program);
            secBatchConfiguration.setSemester(semester);
            secBatchConfiguration.setTerm(term);
            secBatchConfiguration.setSection(section);
            secBatchConfigurationRepository.save(secBatchConfiguration);
        }

        // Assign the unassigned students to the default batch
//        List<EligibleStudent> unassignedStudents = eligibleStudentService.getUnassignedStudents(academicYear, program, semester, term, section);
//
//        // Get the default batch or create one if it doesn't exist
//        SecBatch defaultBatch = secBatchService.findOrCreateDefaultBatch(academicYear, program, semester, term, section);
//
//        // Loop through the unassigned students and assign them to the default batch
//        for (EligibleStudent student : unassignedStudents) {
//            eligibleStudentService.assignSecBatchToEligibleStudent(student.getEligibleStudentId(), defaultBatch.getSecBatchId());
//        }

        for (Map.Entry<String, String> entry : secBatchAssignments.entrySet()) {

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
                Long secBatchId = Long.parseLong(entry.getValue());

                // Update the student's secBatch in the database
                eligibleStudentService.assignSecBatchToEligibleStudent(studentId, secBatchId);
            } catch (NumberFormatException e) {
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
//        return "redirect:/hod/updateStudentSecBatchDetails/" + academicYearId + "/" + programId + "/" + semId + "/" + termId + "/" + sectionId;

        return "redirect:/hod/updateStudentSecBatchDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId + "/" + sectionId;
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
package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.GroupConfigurationRepository;
import com.ras.cms.repository.SectionConfigurationRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.group.GroupService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.studentCourseRegistration.StudentCourseRegistrationService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
public class OpenElectiveGroupAllotmentController {

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

//        @Autowired
//        SectionService sectionService;

    @Autowired
    GroupService groupService;

    @Autowired
    TermService termService;

    @Autowired
    EligibleStudentService eligibleStudentService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @Autowired
    BatchYearSemTermService batchYearSemTermService;

    @Autowired
    OpenElectiveService openElectiveService;

    @Autowired
    StudentCourseRegistrationService studentCourseRegistrationService;

//        @Autowired
//        private SectionConfigurationRepository sectionConfigurationRepository;

    @Autowired
    private GroupConfigurationRepository groupConfigurationRepository;

//    private int noOfSections = 3; // Default value, you can adjust it as needed


    @GetMapping({"/hod/selectPrerequisiteForOEGroupAllotment"})
    public String selectbasicFieldsbeforeOEGroupAllotment(Model model, HttpServletRequest request) {

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
        return "/OEGroupAllotmenttoStudentsPrerequisite";
    }


    @PostMapping({"/hod/selectPrerequisiteForOEGroupAllotment"})
    public String postselectofbatchDepProgSem(Model model, HttpServletRequest request,
                                              @RequestParam(required = false, name = "academicYear") Long academicYearId,
                                              @RequestParam(required = false, name = "program") Long programId,
                                              @RequestParam(required = false, name = "semester") Long semId,
                                              @RequestParam(required = false, name = "term") Long termId) {
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
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
//                return "redirect:/hod/updateStudentOEGroupDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
            return "redirect:/hod/selectOpenElectiveCourse/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;

        } else {
            return "redirect:/hod/selectPrerequisiteForOEGroupAllotment";

        }
//        return "/403";
    }


    @GetMapping("/hod/selectOpenElectiveCourse/{academicYearId}/{programId}/{semId}/{termId}")
    public String selectOpenElectiveCourseBeforeOEGroupAllotment(Model model, HttpServletRequest request,
                                                                 @PathVariable(required = true, name = "academicYearId") Long academicYearId,
                                                                 @PathVariable(required = true, name = "programId") Long programId,
                                                                 @PathVariable(required = true, name = "semId") Long semId,
                                                                 @PathVariable(required = true, name = "termId") Long termId) {


        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();


        List<Program> programs = programService.getProgramsByDepartment(dep);

        model.addAttribute("programs", programs);

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);

        BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(academicYear, semester, term);

        if (batchYearSemTerm != null) {
            Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();

            // Find courses for the current semester and academic year
            List<OpenElective> openElectives = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);

            List<OpenElective> sameDeptOpenElectives = new ArrayList<>();
            List<OpenElective> otherDeptOpenElectives = new ArrayList<>();

            for (OpenElective openElective : openElectives) {
                if (openElective.getDepartment().equals(dep)) {
                    sameDeptOpenElectives.add(openElective);
                } else {
                    otherDeptOpenElectives.add(openElective);
                }
            }

            model.addAttribute("sameDeptOpenElectives", sameDeptOpenElectives);
            model.addAttribute("otherDeptOpenElectives", otherDeptOpenElectives);
        } else {
            System.out.println("BatchYearSemTerm is null");
        }

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/OEGroupAllotmentSelectOpenElectives";
    }


    @PostMapping("/hod/selectOpenElectiveCourse")
    public String postOpenElectiveSelection(Model model,
                                            @RequestParam("academicYear") Long academicYearId,
                                            @RequestParam("program") Long programId,
                                            @RequestParam("semester") Long semId,
                                            @RequestParam("term") Long termId,
                                            @RequestParam(name = "selectedOpenElectiveId", required = true) Long selectedOpenElectiveId) {

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);

        if (selectedOpenElectiveId != null) {
//            return "redirect:/listOEGroupStudents?academicYearId=" + academicYearId +
//                    "&programId=" + programId +
//                    "&semId=" + semId +
//                    "&termId=" + termId +
//                    "&selectedOpenElectiveId=" + selectedOpenElectiveId;

            return "redirect:/hod/listOEGroupStudents/" + academicYearId + "/" + programId + "/" + semId + "/" + termId + "/" + selectedOpenElectiveId;

        }


        return "/403";
    }


    @GetMapping("/hod/listOEGroupStudents/{academicYearId}/{programId}/{semId}/{termId}/{openElectiveId}")
    public String listStudentsofOEGroupSelected(Model model,
                                                HttpServletRequest request,
                                                @PathVariable(name = "academicYearId") Long academicYearId,
                                                @PathVariable(name = "programId") Long programId,
                                                @PathVariable(name = "semId") Long semId,
                                                @PathVariable(name = "termId") Long termId,
                                                @PathVariable(name = "openElectiveId") Long selectedOpenElectiveId) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department loggedInDept = departmentAndProgramFetch.getDepartment();

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        OpenElective openElective = openElectiveService.findOne(selectedOpenElectiveId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
        model.addAttribute("openElective", openElective);

        if (openElective != null) {

            List<StudentCourseRegistration> registrations = studentCourseRegistrationService.findAllRegistrationsByOpenElective(openElective, academicYear, semester);

            List<EligibleStudent> foundOEStudents = new ArrayList<>();
            for (StudentCourseRegistration registration : registrations) {

                Long eligibleStudentId = registration.getEligibleStudent().getEligibleStudentId();
                EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
                if (eligibleStudent != null) {
                    foundOEStudents.add(eligibleStudent);
                }
            }

            model.addAttribute("foundOEStudents", foundOEStudents);
            model.addAttribute("openElectiveDept", openElective.getDepartment());
            model.addAttribute("loggedInDept", loggedInDept);
//                    List<EligibleStudent> OEGroupStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndGroupOfOpenElective(academicYear, program, semester, term, group);


        }

        List<OEGroup> groups = groupService.findAll();
        model.addAttribute("groups", groups);

        return "OEGroupStudentsList";
    }

//        @PostMapping("/hod/listOEGroupStudents")
//        public String postListOEGroupStudents(Model model, @RequestParam Long academicYearId,
//                                              @RequestParam Long programId,
//                                              @RequestParam Long semId,
//                                              @RequestParam Long termId
////                                            @RequestParam Long groupId
//                                            ){
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
//            List<OEGroup> groups = groupService.findAll();
//            model.addAttribute("groups", groups);
//
////            OEGroup group = groupService.findOne(groupId);
//
//            List<EligibleStudent> OEGroupStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndGroupOfOpenElective(academicYear, program, semester, term, group);
//
//            Collections.sort(OEGroupStudents, Comparator.comparing(EligibleStudent::getUsn));
//
//            model.addAttribute("OEGroupStudents", OEGroupStudents);
//            System.out.println(OEGroupStudents);
//            return "OEGroupStudentsList";
//        }


    @GetMapping("/hod/updateStudentOEGroupDetails/{academicYearId}/{programId}/{semId}/{termId}/{openElectiveId}")
    public String showUpdateStudentOEGroupDetailsPage(Model model,
                                                      HttpServletRequest request,
                                                      @PathVariable(required = true, name = "academicYearId") Long academicYearId,
                                                      @PathVariable(required = true, name = "programId") Long programId,
                                                      @PathVariable(required = true, name = "semId") Long semId,
                                                      @PathVariable(required = true, name = "termId") Long termId,
                                                      @PathVariable(required = true, name = "openElectiveId") Long openElectiveId) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department loggedInDept = departmentAndProgramFetch.getDepartment();

        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Program program = programService.findOne(programId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);
        OpenElective openElective = openElectiveService.findOne(openElectiveId);

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("program", program);
        model.addAttribute("semester", semester);
        model.addAttribute("term", term);
        model.addAttribute("openElective", openElective);

        //checking if the dept of selected open elective is being edited only by same dept HOD
        if (openElective.getDepartment() == loggedInDept) {

            // Retrieve or set the default value for noOfSections
            Integer noOfGroups = groupConfigurationRepository
                    .findByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(academicYear, program, semester, term, openElective)
                    .map(GroupConfiguration::getNoOfGroups)
                    .orElse(5); // Set a default value (you can change it as needed)

            model.addAttribute("noOfGroups", noOfGroups);
            System.out.println("No of Groups: " + noOfGroups);

            List<EligibleStudent> students = new ArrayList<>();
            if (openElective != null) {
                List<StudentCourseRegistration> registrations = studentCourseRegistrationService.findAllRegistrationsByOpenElective(openElective, academicYear, semester);
                for (StudentCourseRegistration registration : registrations) {
                    Long eligibleStudentId = registration.getEligibleStudent().getEligibleStudentId();
                    EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
                    if (eligibleStudent != null) {
                        students.add(eligibleStudent);
                    }
                }
            }
//            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(academicYear, program, semester, term, openElective);
            List<OEGroup> groups = groupService.findAll().subList(0, noOfGroups);

            System.out.println(students);

            Map<Long, Long> currentAssignments = new HashMap<>();
            for (EligibleStudent student : students) {
                if (student.getGroupOfOpenElective() != null) {
                    currentAssignments.put(student.getEligibleStudentId(), student.getGroupOfOpenElective().getGroupId());
                }
            }

            model.addAttribute("students", students);
            model.addAttribute("groups", groups);
            model.addAttribute("currentAssignments", currentAssignments);
        }

        return "OEGroupAllotmenttoStudents";
    }



        @PostMapping("/hod/updateStudentOEGroupDetails")
        public String postStudentOEGroupDetailsUpdate(@RequestParam Map<String, String> groupAssignments,
                                                      @RequestParam("academicYear") Long academicYearId,
                                                      @RequestParam("program") Long programId,
                                                      @RequestParam("semester") Long semId,
                                                      @RequestParam("term") Long termId,
                                                      @RequestParam("openElective") Long openElectiveId,
                                                      @RequestParam("noOfGroups") Integer noOfGroups,
                                                      RedirectAttributes redirectAttributes){
//        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);

                AcademicYear academicYear = academicYearService.findOne(academicYearId);
                Program program = programService.findOne(programId);
                Semester semester = semesterService.findOne(semId);
                Term term = termService.findOne(termId);
                OpenElective openElective = openElectiveService.findOne(openElectiveId);


                System.out.println("No of Groups: " + noOfGroups);
                System.out.println(academicYear.getYear());
                System.out.println(program.getProgramName());


                // Save or update the noOfSections value in the database for the selected academicYear, program, sem, term
                GroupConfiguration groupConfiguration = groupConfigurationRepository
                        .findByAcademicYearAndProgramAndSemesterAndTermAndOpenElective(academicYear, program, semester, term, openElective)
                        .orElse(null);

                // Check if sectionConfiguration is null
                if (groupConfiguration == null) {
                    // If it's null, create a new SectionConfiguration with the default value of 5
                    groupConfiguration = new GroupConfiguration();
                    groupConfiguration.setNoOfGroups(5);  // Set a default value (you can change it as needed)
                }

                // Retrieve previousNoOfSections
                Integer previousNoOfGroups = groupConfiguration.getNoOfGroups();

                // Compare the new and previous noOfSections
                if (noOfGroups < previousNoOfGroups) {
//                // If decreased, unassign students from sections beyond the new noOfSections
//
//                // Find the student IDs to unassign
//                List<Long> studentIdsToUnassign = eligibleStudentService.findStudentsToUnassign(academicYear, program, semester, term, noOfGroups);
//                System.out.println(studentIdsToUnassign);
//                // Unassign students
//                for (Long studentId : studentIdsToUnassign) {
//                    System.out.println("Unassigning student with ID: " + studentId);
//                    eligibleStudentService.unassignGroupFromEligibleStudent(studentId);
//                    System.out.println("Unassigned student : " + studentId);
//                }
                }

                if (groupConfiguration.getNoOfGroups() == null || !groupConfiguration.getNoOfGroups().equals(noOfGroups)) {
                    groupConfiguration.setNoOfGroups(noOfGroups);
                    groupConfiguration.setAcademicYear(academicYear);
                    groupConfiguration.setProgram(program);
                    groupConfiguration.setSemester(semester);
                    groupConfiguration.setTerm(term);
                    groupConfiguration.setOpenElective(openElective);
                    groupConfigurationRepository.save(groupConfiguration);
                }



                for (Map.Entry<String, String> entry : groupAssignments.entrySet()) {


                    try {
//            System.out.println(entry);
                        if (entry.getKey().equals("semester") || entry.getKey().equals("academicYear") || entry.getKey().equals("program") || entry.getKey().equals("term") || entry.getKey().equals("openElective") || entry.getKey().equals("noOfGroups")) {
                            // Skip entries that are not student assignments
                            continue;
                        }
//            try{
                        Long studentId = Long.parseLong(entry.getKey());
                        Long groupId = Long.parseLong(entry.getValue());

                        // Update the student's section in the database
                        eligibleStudentService.assignOEGroupToEligibleStudent(studentId, groupId);
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
            return "redirect:/hod/updateStudentOEGroupDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId + "/" + openElectiveId;
        }







}

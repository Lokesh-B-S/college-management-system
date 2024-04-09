package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.PEGroupConfigurationRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.pegroup.PEGroupService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.studentCourseRegistration.StudentCourseRegistrationService;
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
public class ProfessionalElectiveGroupAllotmentController {

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
        PEGroupService peGroupService;

        @Autowired
        TermService termService;

        @Autowired
        EligibleStudentService eligibleStudentService;

        @Autowired
        DepartmentProgramFetchService departmentProgramFetchService;

        @Autowired
        BatchYearSemTermService batchYearSemTermService;


        @Autowired
        CourseService courseService;

        @Autowired
        StudentCourseRegistrationService studentCourseRegistrationService;

        @Autowired
        private PEGroupConfigurationRepository groupConfigurationRepository;



        @GetMapping("/hod/listPEGroupStudents/{academicYearId}/{programId}/{semId}/{termId}/{professionalElectiveId}/{groupId}")
        public String listStudentsofPEGroupSelected(Model model,
                                                    HttpServletRequest request,
                                                    @PathVariable(name = "academicYearId") Long academicYearId,
                                                    @PathVariable(name = "programId") Long programId,
                                                    @PathVariable(name = "semId") Long semId,
                                                    @PathVariable(name = "termId") Long termId,
                                                    @PathVariable(name = "professionalElectiveId") Long selectedProfessionalElectiveId,
                                                    @PathVariable(name = "groupId") Long groupId) {

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            Department loggedInDept = departmentAndProgramFetch.getDepartment();

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);
            Course professionalElective = courseService.findOne(selectedProfessionalElectiveId);
            PEGroup groupOfProfessionalElective = peGroupService.findOne(groupId);

            model.addAttribute("academicYear", academicYear);
            model.addAttribute("program", program);
            model.addAttribute("semester", semester);
            model.addAttribute("term", term);
            model.addAttribute("professionalElective", professionalElective);
            model.addAttribute("groupOfProfessionalElective", groupOfProfessionalElective);

            if (professionalElective != null) {

                List<StudentCourseRegistration> registrations = studentCourseRegistrationService.findAllRegistrationsByProfessionalElectiveAndAcademicYearAndSemesterAndGroupOfProfessionalElective(professionalElective, academicYear, semester, groupOfProfessionalElective);

                List<EligibleStudent> foundPEStudents = new ArrayList<>();
                for (StudentCourseRegistration registration : registrations) {

                    Long eligibleStudentId = registration.getEligibleStudent().getEligibleStudentId();
                    EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
                    if (eligibleStudent != null) {
                        foundPEStudents.add(eligibleStudent);
                    }
                }

                Collections.sort(foundPEStudents, Comparator.comparing(EligibleStudent::getUsn));


                model.addAttribute("foundPEStudents", foundPEStudents);
                model.addAttribute("groupOfProfessionalElective", groupOfProfessionalElective);
                model.addAttribute("professionalElectiveDept", professionalElective.getDepartment());
                model.addAttribute("loggedInDept", loggedInDept);

            }

            List<PEGroup> groups = peGroupService.findAll();
            model.addAttribute("groups", groups);

            return "PEGroupStudentsList";
        }


        @GetMapping("/hod/updateStudentPEGroupDetails/{academicYearId}/{programId}/{semId}/{termId}/{professionalElectiveId}")
        public String showUpdateStudentPEGroupDetailsPage(Model model,
                                                          HttpServletRequest request,
                                                          @PathVariable(required = true, name = "academicYearId") Long academicYearId,
                                                          @PathVariable(required = true, name = "programId") Long programId,
                                                          @PathVariable(required = true, name = "semId") Long semId,
                                                          @PathVariable(required = true, name = "termId") Long termId,
                                                          @PathVariable(required = true, name = "professionalElectiveId") Long professionalElectiveId) {

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            Department loggedInDept = departmentAndProgramFetch.getDepartment();

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);
            Course professionalElective = courseService.findOne(professionalElectiveId);

            model.addAttribute("academicYear", academicYear);
            model.addAttribute("program", program);
            model.addAttribute("semester", semester);
            model.addAttribute("term", term);
            model.addAttribute("professionalElective", professionalElective);

            //checking if the dept of selected professional elective is being edited only by same dept HOD
            if (professionalElective.getDepartment() == loggedInDept) {

                // Retrieve or set the default value for noOfSections
                Integer noOfGroups = groupConfigurationRepository
                        .findByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(academicYear, program, semester, term, professionalElective)
                        .map(PEGroupConfiguration::getNoOfGroups)
                        .orElse(5); // Set a default value (you can change it as needed)

                model.addAttribute("noOfGroups", noOfGroups);
                System.out.println("No of Groups: " + noOfGroups);

                List<EligibleStudent> students = new ArrayList<>();
                if (professionalElective != null) {
                    List<StudentCourseRegistration> registrations = studentCourseRegistrationService.findAllRegistrationsByProfessionalElective(professionalElective, academicYear, semester);
                    for (StudentCourseRegistration registration : registrations) {
                        Long eligibleStudentId = registration.getEligibleStudent().getEligibleStudentId();
                        EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
                        if (eligibleStudent != null) {
                            students.add(eligibleStudent);
                        }
                    }
                }

                List<PEGroup> groups = peGroupService.findAll().subList(0, noOfGroups);

                System.out.println(students);

                Map<Long, Long> currentAssignments = new HashMap<>();
                for (EligibleStudent student : students) {
                    StudentCourseRegistration registration = studentCourseRegistrationService.findRegistrationByEligibleStudentAndProfessionalElectiveAndCurrentAcademicYearAndCurrentSemester(student, professionalElective, academicYear, semester);

                    if (registration != null && registration.getGroupOfProfessionalElective() != null) {
                        currentAssignments.put(student.getEligibleStudentId(), registration.getGroupOfProfessionalElective().getGroupId());
                    }
                }

                Collections.sort(students, Comparator.comparing(EligibleStudent::getUsn));
                model.addAttribute("students", students);
                model.addAttribute("groups", groups);
                model.addAttribute("currentAssignments", currentAssignments);
            }

            return "PEGroupAllotmenttoStudents";
        }



        @PostMapping("/hod/updateStudentPEGroupDetails")
        public String postStudentPEGroupDetailsUpdate(@RequestParam Map<String, String> groupAssignments,
                                                      @RequestParam("academicYear") Long academicYearId,
                                                      @RequestParam("program") Long programId,
                                                      @RequestParam("semester") Long semId,
                                                      @RequestParam("term") Long termId,
                                                      @RequestParam("professionalElective") Long professionalElectiveId,
                                                      @RequestParam("noOfGroups") Integer noOfGroups,
                                                      RedirectAttributes redirectAttributes){

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);
            Course professionalElective = courseService.findOne(professionalElectiveId);


            System.out.println("No of Groups: " + noOfGroups);


            // Save or update the noOfSections value in the database for the selected academicYear, program, sem, term
            PEGroupConfiguration groupConfiguration = groupConfigurationRepository
                    .findByAcademicYearAndProgramAndSemesterAndTermAndProfessionalElective(academicYear, program, semester, term, professionalElective)
                    .orElse(null);

            // Check if sectionConfiguration is null
            if (groupConfiguration == null) {
                // If it's null, create a new SectionConfiguration with the default value of 5
                groupConfiguration = new PEGroupConfiguration();
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
                groupConfiguration.setProfessionalElective(professionalElective);
                groupConfigurationRepository.save(groupConfiguration);
            }



            for (Map.Entry<String, String> entry : groupAssignments.entrySet()) {


                try {
                    if (entry.getKey().equals("semester") || entry.getKey().equals("academicYear") || entry.getKey().equals("program") || entry.getKey().equals("term") || entry.getKey().equals("professionalElective") || entry.getKey().equals("noOfGroups")) {
                        // Skip entries that are not student assignments
                        continue;
                    }
                    Long studentId = Long.parseLong(entry.getKey());
                    Long groupId = Long.parseLong(entry.getValue());

                    EligibleStudent eligibleStudent = eligibleStudentService.findOne(studentId);

                    //dont include program in this line, bcz it wont save for other program students
                    StudentCourseRegistration registration = studentCourseRegistrationService.findRegistrationByEligibleStudentAndProfessionalElectiveAndCurrentAcademicYearAndCurrentSemester(eligibleStudent, professionalElective, academicYear, semester);

                    if (registration != null) {
                        studentCourseRegistrationService.assignPEGroupToStudent(registration.getId(), groupId);
                    } else {
                        // Handle the scenario when registration is null (e.g., log an error or skip)
                        System.out.println("No registration found for student ID: " + studentId);
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Error processing entry: " + entry);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("ArrayIndexOutOfBoundsException" + e);
                    e.printStackTrace();
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
            return "redirect:/hod/updateStudentPEGroupDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId + "/" + professionalElectiveId;
        }


}



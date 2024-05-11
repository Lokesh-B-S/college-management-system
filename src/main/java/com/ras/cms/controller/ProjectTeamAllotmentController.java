package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.ProjectTeamConfigurationRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.projectTeam.ProjectTeamService;
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
public class ProjectTeamAllotmentController {
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
    ProjectTeamService projectTeamService;

        @Autowired
        TermService termService;

        @Autowired
        EligibleStudentService eligibleStudentService;

        @Autowired
        DepartmentProgramFetchService departmentProgramFetchService;

        @Autowired
        private ProjectTeamConfigurationRepository projectTeamConfigurationRepository;


        @GetMapping({"/hod/selectPrerequisiteForProjectTeamAllotment"})
        public String selectbasicFieldsbeforeProjectTeamAllotment(Model model, HttpServletRequest request) {

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            Department dep = departmentAndProgramFetch.getDepartment();

            List<Department> departments = departmentService.findAll();
            List<AcademicYear> academicYears = academicYearService.findAll();
            List<Semester> semesters = semesterService.findAll();
            List<Program> programs = programService.getProgramsByDepartment(dep);
            List<Term> terms = termService.findAll();

            model.addAttribute("departments", departments);
            model.addAttribute("programs", programs);
            model.addAttribute("academicYears", academicYears);
            model.addAttribute("semesters", semesters);
            model.addAttribute("terms", terms);

            return "/ProjectTeamAllotmenttoStudentsPrerequisite";
        }


        @PostMapping({ "/hod/selectPrerequisiteForProjectTeamAllotment"})
        public String postselectofbatchDepProgSemForProjectTeam(Model model, HttpServletRequest request,
                                                  @RequestParam(required=false, name="academicYear") Long academicYearId,
                                                  @RequestParam(required=false, name="program") Long programId,
                                                  @RequestParam(required = false, name="semester") Long semId,
                                                  @RequestParam(required = false, name="term") Long termId)

        {
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

                List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
                System.out.println(students);
                return "redirect:/hod/updateStudentProjectTeamDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
            } else {
                return "redirect:/hod/selectPrerequisiteForProjectTeamAllotment";
            }
//        return "/403";
        }



        @GetMapping({"/hod/selectProjectTeamToViewStudents"})
        public String selectProjectTeam(Model model, HttpServletRequest request, @RequestParam Long teamId) {

            return "/ProjectTeamAllotmenttoStudentsPrerequisite";
        }


        @GetMapping("/hod/listProjectTeamStudents")
        public String listStudentsofProjectTeamSelected(Model model,
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

            List<ProjectTeam> projectTeams = projectTeamService.findAll();
            model.addAttribute("projectTeams", projectTeams);

            return "ProjectTeamStudentsList";
        }

        @PostMapping("/hod/listProjectTeamStudents")
        public String postListProjectTeamStudents(Model model, @RequestParam Long academicYearId,
                                              @RequestParam Long programId,
                                              @RequestParam Long semId,
                                              @RequestParam Long termId,
                                              @RequestParam Long teamId){

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);

            model.addAttribute("academicYear", academicYear);
            model.addAttribute("program", program);
            model.addAttribute("semester", semester);
            model.addAttribute("term", term);

            List<ProjectTeam> projectTeams = projectTeamService.findAll();
            model.addAttribute("projectTeams", projectTeams);

            ProjectTeam projectTeam = projectTeamService.findOne(teamId);
            List<EligibleStudent> projectTeamStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTermAndProjectTeam(academicYear, program, semester, term, projectTeam);

            Collections.sort(projectTeamStudents, Comparator.comparing(EligibleStudent::getUsn));

            model.addAttribute("projectTeamStudents", projectTeamStudents);
            System.out.println(projectTeamStudents);
            return "projectTeamStudentsList";
        }


        @GetMapping("/hod/updateStudentProjectTeamDetails/{academicYearId}/{programId}/{semId}/{termId}" )
        public String showUpdateStudentProjectTeamDetailsPage(Model model,
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

// Retrieve or set the default value for noOfTeams
            Integer noOfTeams = projectTeamConfigurationRepository
                    .findByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)
                    .map(ProjectTeamConfiguration::getNoOfTeams)
                    .orElse(5); // Set a default value (you can change it as needed)

            model.addAttribute("noOfTeams", noOfTeams);
            System.out.println("No of Teams: " + noOfTeams);


            List<EligibleStudent> students = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
            List<ProjectTeam> projectTeams = projectTeamService.findAll().subList(0, noOfTeams);


            Map<Long, Long> currentAssignments = new HashMap<>();
            for (EligibleStudent student : students) {
                if (student.getProjectTeam() != null) {
                    currentAssignments.put(student.getEligibleStudentId(), student.getProjectTeam().getTeamId());
                }
            }

            model.addAttribute("students", students);
            model.addAttribute("projectTeams", projectTeams);
            model.addAttribute("currentAssignments", currentAssignments);

            return "ProjectTeamAllotmenttoStudents";
        }

        @PostMapping("/hod/updateStudentProjectTeamDetails")
        public String postStudentProjectTeamDetailsUpdate(@RequestParam Map<String, String> projectTeamAssignments,
                                                      @RequestParam("academicYear") Long academicYearId,
                                                      @RequestParam("program") Long programId,
                                                      @RequestParam("semester") Long semId,
                                                      @RequestParam("term") Long termId,
                                                      @RequestParam("noOfTeams") Integer noOfTeams,
                                                      RedirectAttributes redirectAttributes) {
//        studentService.bulkAssignAcademicDetails(studentIds, academicYearId, semesterId);

            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Program program = programService.findOne(programId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);

            System.out.println("No of Teams: " + noOfTeams);
            System.out.println(academicYear.getYear());
            System.out.println(program.getProgramName());



            // Save or update the noOfTeams value in the database for the selected academicYear, program, sem, term
            ProjectTeamConfiguration projectTeamConfiguration = projectTeamConfigurationRepository
                    .findByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term)
                    .orElse(null);

            // Check if projectTeamConfiguration is null
            if (projectTeamConfiguration == null) {
                // If it's null, create a new ProjectTeamConfiguration with the default value of 5
                projectTeamConfiguration = new ProjectTeamConfiguration();
                projectTeamConfiguration.setNoOfTeams(5);  // Set a default value (you can change it as needed)
            }

            // Retrieve previousNoOfTeams
            Integer previousNoOfTeams = projectTeamConfiguration.getNoOfTeams();

            // Compare the new and previous noOfTeams
            if (noOfTeams < previousNoOfTeams) {
//                // If decreased, unassign students from teams beyond the new noOfTeams
//
//                // Find the student IDs to unassign
//                List<Long> studentIdsToUnassign = eligibleStudentService.findStudentsToUnassign(academicYear, program, semester, term, noOfTeams);
//                System.out.println(studentIdsToUnassign);
//                // Unassign students
//                for (Long studentId : studentIdsToUnassign) {
//                    System.out.println("Unassigning student with ID: " + studentId);
//                    eligibleStudentService.unassignProjectTeamFromEligibleStudent(studentId);
//                    System.out.println("Unassigned student : " + studentId);
//                }
            }

            if (projectTeamConfiguration.getNoOfTeams() == null || !projectTeamConfiguration.getNoOfTeams().equals(noOfTeams)) {
                projectTeamConfiguration.setNoOfTeams(noOfTeams);
                projectTeamConfiguration.setAcademicYear(academicYear);
                projectTeamConfiguration.setProgram(program);
                projectTeamConfiguration.setSemester(semester);
                projectTeamConfiguration.setTerm(term);
                projectTeamConfigurationRepository.save(projectTeamConfiguration);
            }


            for (Map.Entry<String, String> entry : projectTeamAssignments.entrySet()) {

                try{
                    if (entry.getKey().equals("semester") || entry.getKey().equals("academicYear") || entry.getKey().equals("program") || entry.getKey().equals("term") || entry.getKey().equals("noOfTeams")) {
                        // Skip entries that are not student assignments
                        continue;
                    }

                    Long studentId = Long.parseLong(entry.getKey());
                    Long teamId = Long.parseLong(entry.getValue());

                    // Update the student's projectTeam in the database
                    eligibleStudentService.assignProjectTeamToEligibleStudent(studentId, teamId);
                }  catch (NumberFormatException e) {
                    System.err.println("Error processing entry: " + entry);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("ArrayIndexOutOfBoundsException" + e);
                    e.printStackTrace();
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
            return "redirect:/hod/updateStudentProjectTeamDetails/" + academicYearId  + "/" + programId + "/" + semId + "/" + termId;
        }



}

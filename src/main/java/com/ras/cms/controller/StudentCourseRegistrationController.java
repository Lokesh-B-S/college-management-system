package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.repository.StudentCourseRegistrationRepository;
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
import com.ras.cms.service.studentCourseRegistration.StudentCourseRegistrationService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class StudentCourseRegistrationController {

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
    DepartmentProgramFetchService departmentProgramFetchService;

    @Autowired
    CourseService courseService;

    @Autowired
    BatchYearSemTermService batchYearSemTermService;

    @Autowired
    CourseTypeService courseTypeService;

    @Autowired
    OpenElectiveService openElectiveService;

    @Autowired
    StudentCourseRegistrationService studentCourseRegistrationService;

    @Autowired
    StudentCourseRegistrationRepository studentCourseRegistrationRepository;

    @GetMapping({"/hod/selectPrerequisitesForStudentCourseSelection", "/admin/selectPrerequisitesForStudentCourseSelection"})
    public String selectbasicFieldsbeforeStudentCourseSelectionAllotment(Model model, HttpServletRequest request) {


        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Term> terms = termService.findAll();
        List<Program> programs = programService.getProgramsByDepartment(dep);

        model.addAttribute("programs", programs);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);

//        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/StudentCourseSelectionPrerequisite";
    }


    @PostMapping({"/hod/selectPrerequisitesForStudentCourseSelection", "/admin/selectPrerequisitesForStudentCourseSelection"})
    public String postselectofStudentCourseSelectionPrerequisite(Model model, HttpServletRequest request,
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

        if (request.isUserInRole("PRINCIPAL")) {
            return "redirect:/admin/listStudentsForCoursesAllotment/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;
        } else if (request.isUserInRole("DEPT_HEAD")) {
            return "redirect:/hod/listStudentsForCoursesAllotment/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;

        }
        return "/403";
    }




    @GetMapping(value={"/hod/listStudentsForCoursesAllotment/{academicYearId}/{programId}/{semId}/{termId}", "/admin/listEligibleStudent/{academicYearId}/{programId}/{semId}/{termId}"})
    public String studentslistforcourseallotment(Model model,
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

        // Fetch course registrations for each student
        Map<Long, List<Object>> courseRegistrationsMap = new HashMap<>();
        Map<Long, Integer> totalCoursesMap = new HashMap<>();
        Map<Long, Long> totalCreditsMap = new HashMap<>();
        for (EligibleStudent student : eligibleStudentList) {

            //to group and order based on regular, pe, oe courses further (same as in listCoursesOfStudent)
                    List<StudentCourseRegistration> allottedCoursesList = studentCourseRegistrationService.findRegistrationsByStudent(student);
                    List<Course> foundRegularCourses = new ArrayList<>();
                    List<Course> foundPECourses = new ArrayList<>();
                    List<OpenElective> foundOpenElectiveCourses = new ArrayList<>();
                    Long totalCredits = 0L;

                    for (StudentCourseRegistration registration : allottedCoursesList) {
                        if(registration.getCourseType().equals("regular") ){
                            Long courseId = registration.getCourse().getCourseId();
                            Course course = courseService.findOne(courseId);
                            if (course != null) {
                                foundRegularCourses.add(course);
                                totalCredits += course.getTotalCredits();
                            }
                        }

                        else if(registration.getCourseType().equals("pe1") || registration.getCourseType().equals("pe2")){
                            Long courseId = registration.getCourse().getCourseId();
                            Course course = courseService.findOne(courseId);
                            if (course != null) {
                                foundPECourses.add(course);
                                totalCredits += course.getTotalCredits();
                            }
                        }

                        else if(registration.getCourseType().equals("oe")){
                            Long openElectiveId = registration.getOpenElective().getOpenElectiveId();
                            OpenElective openElective = openElectiveService.findOne(openElectiveId);
                            if(openElective != null){
                                foundOpenElectiveCourses.add(openElective);
                                totalCredits += openElective.getTotalCredits();
                            }
                        }
                    }

                        //add them to a single list (purpose: rowStat in thymleaf)
                        List<Object> allRegisteredCoursesList = new ArrayList<>();
                        allRegisteredCoursesList.addAll(foundRegularCourses);
                        allRegisteredCoursesList.addAll(foundPECourses);
                        allRegisteredCoursesList.addAll(foundOpenElectiveCourses);


                        // Sort the allRegisteredCoursesList based on the semester
                        Collections.sort(allRegisteredCoursesList, (o1, o2) -> {
                            Semester semester1 = (o1 instanceof Course) ? ((Course) o1).getSemester() : ((OpenElective) o1).getSemester();
                            Semester semester2 = (o2 instanceof Course) ? ((Course) o2).getSemester() : ((OpenElective) o2).getSemester();
                            return semester2.getSem().compareTo(semester1.getSem());
                        });


            courseRegistrationsMap.put(student.getEligibleStudentId(), allRegisteredCoursesList);
            totalCoursesMap.put(student.getEligibleStudentId(), allRegisteredCoursesList.size());
            totalCreditsMap.put(student.getEligibleStudentId(), totalCredits);

//            List<StudentCourseRegistration> courseRegistrations = studentCourseRegistrationService.findRegistrationsByStudent(student);
//            courseRegistrationsMap.put(student.getEligibleStudentId(), courseRegistrations);

        }

        model.addAttribute("courseRegistrationsMap", courseRegistrationsMap);
        model.addAttribute("totalCoursesMap", totalCoursesMap);
        model.addAttribute("totalCreditsMap", totalCreditsMap);



        model.addAttribute("program", program);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("term", term);
        model.addAttribute("semester", semester);

        model.addAttribute("eligibleStudentList", eligibleStudentList);
        return "/StudentsListForCoursesAllotment";
    }


    @GetMapping(value={"/hod/listAllottedCoursesForSpecificStudent/{eligibleStudentId}/{academicYearId}/{programId}/{semId}/{termId}", "/admin/listAllottedCoursesForSpecificStudent/{eligibleStudentId}/{academicYearId}/{programId}/{semId}/{termId}"})
    public String coursesAllottedToSpecificStudent(Model model,
                                       @PathVariable(required = true, name = "eligibleStudentId") Long eligibleStudentId,
                                       @PathVariable(required = true, name = "programId") Long programId,
                                      @PathVariable(required = true, name = "academicYearId") Long academicYearId,
                                      @PathVariable(required = true, name = "semId") Long semId,
                                      @PathVariable(required = true, name = "termId") Long termId) {

        EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
        Program program = programService.findOne(programId);
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Semester semester = semesterService.findOne(semId);
        Term term = termService.findOne(termId);


        if (eligibleStudent != null && program != null && academicYear != null && semester != null && term != null) {

            List<StudentCourseRegistration> allottedCoursesList = studentCourseRegistrationService.findRegistrationsByStudent(eligibleStudent);
//            List<Course> foundRegularAndPECourses = new ArrayList<>(); // List to store found Course objects
           List<Course> foundRegularCourses = new ArrayList<>();
           List<Course> foundPECourses = new ArrayList<>();
            List<OpenElective> foundOpenElectiveCourses = new ArrayList<>();

            for (StudentCourseRegistration registration : allottedCoursesList) {
//                if(registration.getCourseType().equals("regular") || registration.getCourseType().equals("pe1") || registration.getCourseType().equals("pe2")){
//                    Long courseId = registration.getCourse().getCourseId();
//                    Course course = courseService.findOne(courseId);
//                    if (course != null) {
//                        foundRegularAndPECourses.add(course);
//                    }
//                }
                if(registration.getCourseType().equals("regular") ){
                    Long courseId = registration.getCourse().getCourseId();
                    Course course = courseService.findOne(courseId);
                    if (course != null) {
                        foundRegularCourses.add(course);
                    }
                }

                else if(registration.getCourseType().equals("pe1") || registration.getCourseType().equals("pe2")){
                    Long courseId = registration.getCourse().getCourseId();
                    Course course = courseService.findOne(courseId);
                    if (course != null) {
                        foundPECourses.add(course);
                    }
                }


                else if(registration.getCourseType().equals("oe")){
                    Long openElectiveId = registration.getOpenElective().getOpenElectiveId();
                    OpenElective openElective = openElectiveService.findOne(openElectiveId);
                    if(openElective != null){
                        foundOpenElectiveCourses.add(openElective);
                    }
                }
            }

            //add them to a single list (purpose: rowStat in thymleaf)
            List<Object> allRegisteredCoursesList = new ArrayList<>();
            allRegisteredCoursesList.addAll(foundRegularCourses);
            allRegisteredCoursesList.addAll(foundPECourses);
            allRegisteredCoursesList.addAll(foundOpenElectiveCourses);


            // Sort the allRegisteredCoursesList based on the semester
            Collections.sort(allRegisteredCoursesList, (o1, o2) -> {
                Semester semester1 = (o1 instanceof Course) ? ((Course) o1).getSemester() : ((OpenElective) o1).getSemester();
                Semester semester2 = (o2 instanceof Course) ? ((Course) o2).getSemester() : ((OpenElective) o2).getSemester();
                return semester2.getSem().compareTo(semester1.getSem());
            });

            System.out.println(allRegisteredCoursesList);
//working (just separate)- dont delete
//            System.out.println("foundRegularAndPECourses" + foundRegularAndPECourses);
//            System.out.println("foundOpenElectiveCourses" + foundOpenElectiveCourses);


            model.addAttribute("eligibleStudent", eligibleStudent);
            model.addAttribute("program", program);
            model.addAttribute("academicYear", academicYear);
            model.addAttribute("term", term);
            model.addAttribute("semester", semester);

//            model.addAttribute("foundRegularAndPECourses", foundRegularAndPECourses);
//            model.addAttribute("foundOpenElectiveCourses", foundOpenElectiveCourses);
            model.addAttribute("allRegisteredCoursesList", allRegisteredCoursesList);
            return "/StudentCourseRegistrationList";
        }
        return "403";
    }




    @GetMapping("/hod/updateStudentCourseRegistration/{eligibleStudentId}")
    public String showUpdateStudentCourseRegistration(Model model, HttpServletRequest request,
                                                      @PathVariable(required = true, name = "eligibleStudentId") Long eligibleStudentId)
    {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
        System.out.println(eligibleStudent.getName() + " with id: " + eligibleStudent.getEligibleStudentId());

        AcademicYear currentAcademicYear = academicYearService.findOne(eligibleStudent.getAcademicYear().getId());
        Program program = programService.findOne(eligibleStudent.getProgram().getProgramId());
        Semester currentSemester = semesterService.findOne(eligibleStudent.getSemester().getSemId());
        Term term = termService.findOne(eligibleStudent.getTerm().getId());
        model.addAttribute("currentAcademicYear", currentAcademicYear);
        model.addAttribute("program", program);
        model.addAttribute("currentSemester", currentSemester);
        model.addAttribute("term", term);



        List<EligibleStudent> allRowsOfStudent = eligibleStudentService.findAllIdsByUsn(eligibleStudent.getUsn());
        Collections.sort(allRowsOfStudent, Comparator.comparing((EligibleStudent s) -> s.getSemester().getSem()).reversed());
        System.out.println(allRowsOfStudent);


        // Create a list to store data for each semester
        List<Map<String, Object>> semesterDataList = new ArrayList<>();

        for (EligibleStudent eachStudentRow : allRowsOfStudent) {
            // Get the batch year, semester, and term information for the current student
            System.out.println(eachStudentRow);
            Semester sem = eachStudentRow.getSemester();

                if (sem != null && sem.getSem() <= currentSemester.getSem() ) {
                    AcademicYear ay = eachStudentRow.getAcademicYear();
                    Term termm = eachStudentRow.getTerm();

                    System.out.println(ay.getId());
                    System.out.println(sem.getSemId());
                    System.out.println(termm.getId());

                    // Get the BatchYearSemTermId for the current student
                    BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.getRowByAcademicYearAndSemesterAndTerm(ay, sem, termm);

                    if (batchYearSemTerm != null) {
                        Long batchYearSemTermId = batchYearSemTerm.getBatchYearSemTermId();

                        // Find courses for the current semester and academic year
                        List<Course> regularCourses = courseService.getAllRegularCoursesByBatchYearSemTermIdAndDepartment(batchYearSemTermId, dep);
                        List<OpenElective> openElectives = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);
                        CourseType pe1CourseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 1");
                        CourseType pe2CourseType = courseTypeService.getCourseTypeByTypeOfCourse("Professional Elective 2");
                        List<Course> pe1Courses = courseService.getCoursesByBatchYearSemTermIdAndProgramAndCourseType(batchYearSemTermId, program, pe1CourseType);
                        List<Course> pe2Courses = courseService.getCoursesByBatchYearSemTermIdAndProgramAndCourseType(batchYearSemTermId, program, pe2CourseType);

                        Map<String, Object> semesterData = new LinkedHashMap<>();
                        semesterData.put("semester", sem.getSem());
                        semesterData.put("regularCourses", regularCourses);
                        semesterData.put("openElectives", openElectives);
                        semesterData.put("pe1Courses", pe1Courses);
                        semesterData.put("pe2Courses", pe2Courses);

                        semesterDataList.add(semesterData);

                    } else {
                        System.out.println("BatchYearSemTerm is null");
                    }
                } else {
                    System.out.println("Semester is null");
                }
            }


        model.addAttribute("eligibleStudent", eligibleStudent);
        model.addAttribute("semesterDataList", semesterDataList);
        System.out.println(semesterDataList);

        Long totalCoursesRegistered = 0L;
        Long totalCreditsRegistered = 0L;

        List<Long> currentRegularCourseIds = new ArrayList<>();
        List<StudentCourseRegistration> courseRegistrations = studentCourseRegistrationService.findRegistrationsByStudentAndCourseType(eligibleStudent, "regular");
        for (StudentCourseRegistration registration : courseRegistrations) {
            System.out.println(registration.getCourse().getCourseName());

            if (registration.getCourse() != null) {
                currentRegularCourseIds.add(registration.getCourse().getCourseId());

                totalCoursesRegistered = totalCoursesRegistered + 1L;
                totalCreditsRegistered = totalCreditsRegistered + registration.getCourse().getTotalCredits();
            }
        }
        System.out.println(currentRegularCourseIds);


        Map<Long, OpenElective> currentOpenElectives = new HashMap<>();
        List<StudentCourseRegistration> openElectiveRegistrations = studentCourseRegistrationService.findRegistrationsByStudentAndCourseType(eligibleStudent, "oe");
        for (StudentCourseRegistration registration : openElectiveRegistrations) {
            OpenElective openElective = registration.getOpenElective();
            if (openElective != null) {
                currentOpenElectives.put(openElective.getOpenElectiveId(), openElective);
                totalCoursesRegistered++;
                totalCreditsRegistered += openElective.getTotalCredits();
            }
        }


        Map<Long, Course> currentAssignmentsForPE1 = new HashMap<>();
        List<StudentCourseRegistration> pe1Registrations = studentCourseRegistrationService.findRegistrationsByStudentAndCourseType(eligibleStudent, "pe1");
        for (StudentCourseRegistration registration : pe1Registrations) {
            if (registration.getCourse() != null) {
                currentAssignmentsForPE1.put(registration.getCourse().getCourseId(),registration.getCourse());

                totalCoursesRegistered = totalCoursesRegistered + 1L;
                totalCreditsRegistered = totalCreditsRegistered + registration.getCourse().getTotalCredits();
            }
        }


        Map<Long, Course> currentAssignmentsForPE2 = new HashMap<>();
        List<StudentCourseRegistration> pe2Registrations = studentCourseRegistrationService.findRegistrationsByStudentAndCourseType(eligibleStudent, "pe2");
        for (StudentCourseRegistration registration : pe2Registrations) {
            if (registration.getCourse() != null) {
                currentAssignmentsForPE2.put(registration.getCourse().getCourseId(),registration.getCourse());

                totalCoursesRegistered = totalCoursesRegistered + 1L;
                totalCreditsRegistered = totalCreditsRegistered + registration.getCourse().getTotalCredits();
            }
        }
//        System.out.println(currentRegularCourseIds);

        model.addAttribute("currentRegularCourseIds", currentRegularCourseIds);
        model.addAttribute("currentOpenElectives", currentOpenElectives);
        model.addAttribute("currentAssignmentsForPE1", currentAssignmentsForPE1);
        model.addAttribute("currentAssignmentsForPE2", currentAssignmentsForPE2);

        model.addAttribute("totalCoursesRegistered", totalCoursesRegistered);
        model.addAttribute("totalCreditsRegistered",totalCreditsRegistered);
        return "StudentCourseRegistrationEdit";
    }

    @PostMapping("/hod/updateStudentCourseRegistration")
    public String postStudentCourseRegistrationUpdate(
                                                        @RequestParam Map<String, String> formData,
                                                      @RequestParam("eligibleStudent") Long eligibleStudentId,
                                                      HttpServletRequest request,
                                             RedirectAttributes redirectAttributes
    ) {

        System.out.println(formData);
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String[] nameParts = entry.getKey().split("_");

            if (nameParts.length == 3) {
                String type = nameParts[0];
                Long studentId = Long.parseLong(nameParts[1]);
                Long courseId = Long.parseLong(entry.getValue());

                System.out.println("Processing entry: " + entry.getKey() + ", Value: " + entry.getValue());

                try {
                    EligibleStudent eligibleStudent = eligibleStudentService.findOne(studentId);

                    if (type.equals("pe1") || type.equals("pe2")) {
                        Course course = courseService.findOne(courseId);
                        if (type.equals("pe1")) {
                            studentCourseRegistrationService.assignProfessionalElective1ToEligibleStudent(eligibleStudent, course, course.getAcademicYear(), course.getSemester());
                        } else {
                            studentCourseRegistrationService.assignProfessionalElective2ToEligibleStudent(eligibleStudent, course, course.getAcademicYear(), course.getSemester());
                        }
                    } else if (type.equals("oe")) {
                        OpenElective openElective = openElectiveService.findOne(courseId);
                        studentCourseRegistrationService.assignOpenElectiveToEligibleStudent(eligibleStudent, openElective, openElective.getAcademicYear(), openElective.getSemester());
                    }


                } catch (NumberFormatException e) {
                    System.err.println("Error processing entry: " + entry);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("ArrayIndexOutOfBoundsException" + e);
                    e.printStackTrace();
                }
            }
        }


        for (Map.Entry<String, String> entry : formData.entrySet()) {

            String key = entry.getKey();
            if (key.startsWith("rc_")) { // Check if the entry is for a regular course
                Long courseId = Long.parseLong(entry.getValue());
                System.out.println("Processing regular course entry for studentId: " + eligibleStudentId + ", Course ID: " + courseId);

                try {
                    // Retrieve eligible student and course objects
                    EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);
                    Course course = courseService.findOne(courseId);
                    studentCourseRegistrationService.registerRegularCourseToStudent(eligibleStudent, course, course.getAcademicYear(), course.getSemester());

                } catch (Exception e) {
                    // Handle exception
                }
            }
        }
//
            redirectAttributes.addFlashAttribute("successMessage", "Students updated successfully!");
            return "redirect:/hod/updateStudentCourseRegistration/" + eligibleStudentId;
//        return "redirect:/hod/updateStudentCourseRegistration/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;

        }




    @GetMapping(value={"/hod/deleteCourseRegistered/{eligibleStudentId}/{courseId}"})
    public String CourseRegistrationDelete(Model model, @PathVariable(required = true, name = "eligibleStudentId") Long eligibleStudentId,
                                           @PathVariable(required = false, name = "courseId") Long courseId,
                                           RedirectAttributes attributes,
                                           HttpServletRequest request) {

        System.out.println(courseId);
        Course course = null;
        OpenElective openElective = null;
        EligibleStudent eligibleStudent = eligibleStudentService.findOne(eligibleStudentId);

        if (courseId != null) {
            if (courseService.existsById(courseId)) {
                course = courseService.findOne(courseId);
            }
             else{
                 openElective = openElectiveService.findOne(courseId);
             }
        }

        StudentCourseRegistration registration = null;
        if (course != null) {
            registration = studentCourseRegistrationService.findRegistrationByStudentAndCourse(eligibleStudent, course);
        } else if (openElective != null) {
            registration = studentCourseRegistrationService.findRegistrationByStudentAndOpenElective(eligibleStudent, openElective);
        }

        Long academicYearId = eligibleStudent.getAcademicYear().getId();
        Long programId = eligibleStudent.getProgram().getProgramId();
        Long semId = eligibleStudent.getSemester().getSemId();
        Long termId = eligibleStudent.getTerm().getId();

        if(registration != null){
            studentCourseRegistrationService.deleteRegisteredCourseFromList(registration.getId());
        }
        attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully");

            return "redirect:/hod/listAllottedCoursesForSpecificStudent/" + eligibleStudentId + "/" + academicYearId + "/" + programId + "/" + semId + "/" + termId;



    }

}



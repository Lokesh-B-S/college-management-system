package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyeardeptprogramsem.BatchYearDeptProgramSemService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.coursetype.CourseTypeService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.teachingdepartment.TeachingDepartmentService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
//@RequestMapping("/admin")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseTypeService courseTypeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ProgramService programService;

    @Autowired
    SemesterService semesterService;

    @Autowired
    BatchService batchService;

    @Autowired
    AcademicYearService academicYearService;
    @Autowired
    BatchYearDeptProgramSemService batchYearDeptProgramSemService;

    @Autowired
    TeachingDepartmentService teachingDepartmentService;

    @Autowired
    BatchYearSemTermService batchYearSemTermService;

    @Autowired
    TermService termService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;


    @GetMapping(value = {"/admin/listCourse/{batchYearSemTermId}/{courseTypeId}", "/hod/listCourse/{batchYearSemTermId}"})
    public String courseList(Model model, HttpServletRequest request, Authentication authentication,
                                         @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId,
                                         @PathVariable(required = false, name = "courseTypeId") Long courseTypeId)
    {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        if (null != batchYearSemTermId) {
            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.findOne(batchYearSemTermId);
            model.addAttribute("batchYearSemTermView", batchYearSemTerm);

            if (request.isUserInRole("PRINCIPAL")) {

                if (courseTypeId != null) {
                    CourseType courseType = courseTypeService.findOne(courseTypeId); // Fetch the CourseType
                    if (courseType != null) {
                        List<Course> AllDeptCourses = courseService.getCoursesByBatchYearSemTermIdAndCourseType(batchYearSemTermId, courseType);
                        model.addAttribute("AllDeptCourses", AllDeptCourses);
                    }
                    else{
                        System.out.println("course type not found");
                    }
                }
            }
            else  if (request.isUserInRole("DEPT_HEAD")) {

                List<Course> AllDeptCourses = courseService.getCoursesByBatchYearSemTermIdAndDepartment(batchYearSemTermId, dep);
                model.addAttribute("AllDeptCourses", AllDeptCourses);

                //Calculate the total sum of lectureCredits, tutorialCredits, practicalCredits, totalCredits, and contactHours
                Long totalLectureCredits = AllDeptCourses.stream().mapToLong(Course::getLectureCredits).sum();
                Long totalTutorialCredits = AllDeptCourses.stream().mapToLong(Course::getTutorialCredits).sum();
                Long totalPracticalCredits = AllDeptCourses.stream().mapToLong(Course::getPracticalCredits).sum();
                Long totalTotalCredits = AllDeptCourses.stream().mapToLong(Course::getTotalCredits).sum();
                Long totalContactHours = AllDeptCourses.stream().mapToLong(Course::getContactHours).sum();

                model.addAttribute("totalLectureCredits", totalLectureCredits);
                model.addAttribute("totalTutorialCredits", totalTutorialCredits);
                model.addAttribute("totalPracticalCredits", totalPracticalCredits);
                model.addAttribute("totalTotalCredits", totalTotalCredits);
                model.addAttribute("totalContactHours", totalContactHours);
            }

        }
        return "/courseList";

    }

    @GetMapping({"/admin/selectCourse", "hod/selectCourse"})
    public String selectCoursebasicFields(Model model) {

        List<Batch> batches = batchService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Term> terms = termService.findAll();
        List<CourseType> courseTypes = courseTypeService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);
        model.addAttribute("courseTypes", courseTypes);

        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/courseSelection";
    }


    @PostMapping({"hod/selectCourse", "/admin/selectCourse"})
    public String selectandsubmitCourse(Model model, Authentication authentication,
                                                    BatchYearSemTerm batchYearSemTerm, RedirectAttributes redirectAttributes, HttpServletRequest request,
                                                    @RequestParam(required= false, name="courseType") Long courseTypeId) {

        System.out.println(courseTypeId);
        if (batchYearSemTermService.existsEntry(batchYearSemTerm)) {
            redirectAttributes.addFlashAttribute("message", "Entry already exists.");
            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);

            if (request.isUserInRole("PRINCIPAL")) {
                return "redirect:/admin/listCourse/" + batchYearSemTerm1.getBatchYearSemTermId() + "/" + courseTypeId ;
            }
            else if(request.isUserInRole("DEPT_HEAD")){
                return "redirect:/hod/listCourse/" + batchYearSemTerm1.getBatchYearSemTermId();
            }
        } else {
            try {
                batchYearSemTermService.saveEntry(batchYearSemTerm);
                try {
                    BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findOne(batchYearSemTerm.getBatchYearSemTermId());
                    model.addAttribute("batchYearSemTerm1", batchYearSemTerm1);
                    if (request.isUserInRole("PRINCIPAL")) {
                        return "redirect:/admin/listCourse/" + batchYearSemTerm1.getBatchYearSemTermId() + "/" + courseTypeId ;
                    } else if (request.isUserInRole("DEPT_HEAD")) {
                        //if i had to use openElectiveEdit(non dynamic)
//                        return "redirect:/hod/openElectiveEdit/" + batchYearSemTerm.getBatchYearSemTermId();
                        return "redirect:/hod/listCourse/" + batchYearSemTerm1.getBatchYearSemTermId();

                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        }
        return "/403";
    }


    @GetMapping(value = {"/hod/courseEdit", "/hod/courseEdit/{courseId}", "/hod/courseEdit/{batchYearSemTermId}"})
    public String CourseEdit(Model model, HttpServletRequest request,
                                         @RequestParam(required = false, name = "id") Long id,
                                         @PathVariable(required = false, name="courseId") Long courseId,
                                         @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId)
    {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        model.addAttribute("department", departmentAndProgramFetch.getDepartment());
        model.addAttribute("program", departmentAndProgramFetch.getProgram());


        if (null != batchYearSemTermId) {
            model.addAttribute("batchYearSemTerm1", batchYearSemTermService.findOne(batchYearSemTermId));
        } else {
            model.addAttribute("batchYearSemTerm1", new BatchYearSemTerm());
        }

        // BatchYearDeptProgramSem batchYearDeptProgramSem = batchYearDeptProgramSemService.findOne(id);
        if(courseId!=null){
            model.addAttribute("course", courseService.findOne(courseId));
            Course course1 = courseService.findOne(courseId);
            Long batchYearSemTermId1 = course1.getBatchYearSemTermId();
            model.addAttribute("batchYearSemTerm1", batchYearSemTermService.findOne(batchYearSemTermId1));
        }
        else {
            model.addAttribute("course", new Course());
        }

        model.addAttribute("courseTypes", courseTypeService.findAll());
        model.addAttribute("teachingDepartments", teachingDepartmentService.findAll());

        return "/courseEdit";
    }

    @PostMapping(value = "/hod/courseEdit")
    public String CourseEdit(Model model,
                                         HttpServletRequest request,
                                         @ModelAttribute CourseType courseType, Course course, BatchYearSemTerm batchYearSemTerm1, RedirectAttributes attributes, @RequestParam Long batchYearSemTermId) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);

        try {
            // Check if an entry already exists for the given parameters, if yes set the batch,program etc values to course and save
            boolean entryExists = courseService.doesEntryExist(batchYearSemTermId, course.getContactHours(),course.getCourseBatchesCount(), course.getCourseCode(), course.getCourseName(), course.getTeachingDepartment(), course.getCourseType(), course.getLectureCredits(), course.getTutorialCredits(), course.getPracticalCredits(), course.getTotalCredits());

            if (!entryExists) {
                course.setBatchYearSemTermId(batchYearSemTerm1.getBatchYearSemTermId());
                course.setDepartment(departmentAndProgramFetch.getDepartment());
                course.setProgram(departmentAndProgramFetch.getProgram());
                course.setSemester(batchYearSemTerm1.getSemester());
                course.setTerm(batchYearSemTerm1.getTerm());
                course.setBatch(batchYearSemTerm1.getBatch());
                course.setAcademicYear(batchYearSemTerm1.getAcademicYear());
                //course.setCourseType(courseType.getTypeOfCourse());

                System.out.println(courseType.getTypeOfCourse() + " in post");
                courseService.saveCourse(course);

                System.out.println("Success");
                // model.addAttribute("alertType", "success");
                attributes.addFlashAttribute("successMessage", "Course saved successfully");
            } else {
                attributes.addFlashAttribute("EntryAlreadyExistsError", "Sorry! already there is an entry exists");
            }

        } catch (Exception e) {
            System.out.println("fail");
            System.out.println(e);
            // model.addAttribute("alertType", "danger");
            attributes.addFlashAttribute("errorMessage", "Course couldn't be saved!");

        }
        return "redirect:/hod/courseEdit/" + batchYearSemTermId;
//        return "redirect:/admin/courseEdit?id=" + batchYearDeptProgramSemId;
    }



    @GetMapping(value={"/hod/deleteCourse/{courseId}","/admin/deleteCourse/{courseId}"})
    public String CourseDeleteHOD(Model model, @PathVariable(required = true, name = "courseId") Long courseId, RedirectAttributes attributes, BatchYearSemTerm batchYearSemTerm, HttpServletRequest request) {
        Course course1 = courseService.findOne(courseId);
        Long batchYearSemTermId = course1.getBatchYearSemTermId();
        Long courseTypeId = course1.getCourseType().getCourseTypeId();

        courseService.deleteCourse(courseId);
        attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully");

        if(request.isUserInRole("DEPT_HEAD")) {
            return "redirect:/hod/listCourse/" + batchYearSemTermId ;
        }
        else if(request.isUserInRole("PRINCIPAL")) {
            return "redirect:/admin/listCourse/" + batchYearSemTermId + "/" + courseTypeId;
        }

        return "/403";
    }


}

package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyeardeptprogramsem.BatchYearDeptProgramSemService;
import com.ras.cms.service.coursetype.CourseTypeService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.teachingdepartment.TeachingDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
@RequestMapping("/admin")
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



    @GetMapping(value = "/listCourse/{id}")
    public String courseList(Model model, @PathVariable(required = false, name = "id") Long id) {
        List<Course> entries = courseService.getEntriesByBatchYearDeptProgramSemId(id);
        model.addAttribute("id", id);

        BatchYearDeptProgramSem batchYearDeptProgramSem = batchYearDeptProgramSemService.findOne(id);
        if (batchYearDeptProgramSem != null) {
            List<Course> courses = courseService.getCoursesByDepartmentAndSemester(batchYearDeptProgramSem.getDepartment(), batchYearDeptProgramSem.getSemester());
            model.addAttribute("courses", courses);
            model.addAttribute("batchYearDeptProgramSemView", batchYearDeptProgramSem);

            // Calculate the total sum of lectureCredits, tutorialCredits, practicalCredits, totalCredits, and contactHours
            Long totalLectureCredits = courses.stream().mapToLong(Course::getLectureCredits).sum();
            Long totalTutorialCredits = courses.stream().mapToLong(Course::getTutorialCredits).sum();
            Long totalPracticalCredits = courses.stream().mapToLong(Course::getPracticalCredits).sum();
            Long totalTotalCredits = courses.stream().mapToLong(Course::getTotalCredits).sum();
            Long totalContactHours = courses.stream().mapToLong(Course::getContactHours).sum();

            model.addAttribute("courses", courses);
            //model.addAttribute("teachingDepartments",teachingDepartmentService.findAll());
            model.addAttribute("totalLectureCredits", totalLectureCredits);
            model.addAttribute("totalTutorialCredits", totalTutorialCredits);
            model.addAttribute("totalPracticalCredits", totalPracticalCredits);
            model.addAttribute("totalTotalCredits", totalTotalCredits);
            model.addAttribute("totalContactHours", totalContactHours);
        } else {
            model.addAttribute("courses", courseService.findAll());
        }

        //model.addAttribute("courseList", courseService.findAll());
        return "/courseList";
    }

    @GetMapping("/selectCourse")
    public String selectCourse(Model model) {

        List<Batch> batches = batchService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Department> departments = departmentService.findAll();
        List<Program> programs = programService.findAll();
        List<Semester> semesters = semesterService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("departments", departments);
        model.addAttribute("programs", programs);
        model.addAttribute("semesters", semesters);

        model.addAttribute("batchYearDeptProgramSem", new BatchYearDeptProgramSem());

        return "/courseSelection";
    }


    @PostMapping("/selectCourse")
    public String selectandsubmitCourse(Model model, BatchYearDeptProgramSem batchYearDeptProgramSem, RedirectAttributes redirectAttributes) {

        if (batchYearDeptProgramSemService.existsEntry(batchYearDeptProgramSem)) {
            redirectAttributes.addFlashAttribute("message", "Entry already exists.");
            BatchYearDeptProgramSem batchYearDeptProgramSem1 = batchYearDeptProgramSemService.findRow(batchYearDeptProgramSem);
            return "redirect:/admin/selectCourseType?id=" + batchYearDeptProgramSem1.getBatchYearDeptProgramSemId();
        } else {
            try {
                batchYearDeptProgramSemService.saveEntry(batchYearDeptProgramSem);
                try {
                    BatchYearDeptProgramSem batchYearDeptProgramSem1 = batchYearDeptProgramSemService.findOne(batchYearDeptProgramSem.getBatchYearDeptProgramSemId());
                    model.addAttribute("batchYearDeptProgramSem1", batchYearDeptProgramSem1);
                    return "redirect:/admin/selectCourseType?id=" + batchYearDeptProgramSem.getBatchYearDeptProgramSemId();
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        }
        return "/403";
    }

    @GetMapping("/selectCourseType")
    public String selectCourseType(Model model, @RequestParam(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("batchYearDeptProgramSem1", batchYearDeptProgramSemService.findOne(id));
        } else {
            model.addAttribute("batchYearDeptProgramSem1", new BatchYearDeptProgramSem());
        }

        List<CourseType> courseTypes = courseTypeService.findAll();
        model.addAttribute("courseTypes", courseTypes);
        return "courseTypeSelect";
    }

    @PostMapping("/selectCourseType")
    public String selectandsubmitCourseType(Model model,@ModelAttribute CourseType courseType, BatchYearDeptProgramSem batchYearDeptProgramSem, RedirectAttributes redirectAttributes) {

        System.out.println(courseType.getCourseTypeId() +  courseType.getTypeOfCourse());


            BatchYearDeptProgramSem batchYearDeptProgramSem1 = batchYearDeptProgramSemService.findRow(batchYearDeptProgramSem);
            return "redirect:/admin/courseEdit/" + batchYearDeptProgramSem1.getBatchYearDeptProgramSemId() + "/" + courseType.getTypeOfCourse() ;
//return "courseTypeSelect";
    }


    @GetMapping(value = {"/courseEdit", "/courseEdit/{courseId}", "/courseEdit/{batchYearDeptProgramSemId}/{courseType}"})
    public String courseEdit(Model model, @RequestParam(required = false, name = "id") Long id,
                             @PathVariable(required = false, name="courseId") Long courseId,
                             @PathVariable(required = false, name = "batchYearDeptProgramSemId") Long batchYearDeptProgramId,
                             @PathVariable(required = false, name="courseType") String typeOfCourse) {
       System.out.println(typeOfCourse);
        if (null != batchYearDeptProgramId) {
            model.addAttribute("batchYearDeptProgramSem1", batchYearDeptProgramSemService.findOne(batchYearDeptProgramId));
        } else {
            model.addAttribute("batchYearDeptProgramSem1", new BatchYearDeptProgramSem());
        }

       // BatchYearDeptProgramSem batchYearDeptProgramSem = batchYearDeptProgramSemService.findOne(id);
if(courseId!=null){
    model.addAttribute("course", courseService.findOne(courseId));
    Course course1 = courseService.findOne(courseId);
    Long batchYearDeptProgramSemId = course1.getBatchYearDeptProgramSemId();
    model.addAttribute("batchYearDeptProgramSem1", batchYearDeptProgramSemService.findOne(batchYearDeptProgramSemId));
}
else {
    model.addAttribute("course", new Course());
}

CourseType courseType = courseTypeService.getCourseTypeByTypeOfCourse(typeOfCourse);

        model.addAttribute("courseType", courseType);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("programs", programService.findAll());
        model.addAttribute("semesters", semesterService.findAll());
        model.addAttribute("teachingDepartments", teachingDepartmentService.findAll());


        System.out.println(courseType.getTypeOfCourse() + " in get");
       // model.addAttribute("courseEntry", new Course());
        return "/courseEdit";
    }

    @PostMapping(value = "/courseEdit")
    public String courseEdit(Model model,@ModelAttribute CourseType courseType, Course course, BatchYearDeptProgramSem batchYearDeptProgramSem1, RedirectAttributes attributes, @RequestParam Long batchYearDeptProgramSemId) {

                try {
            // Check if an entry already exists for the given parameters, if yes set the batch,program etc values to course and save
            boolean entryExists = courseService.doesEntryExist(batchYearDeptProgramSemId, course.getContactHours(),course.getCourseBatchesCount(), course.getCourseCode(), course.getCourseName(), course.getTeachingDepartment(), course.getCourseType(), course.getLectureCredits(), course.getTutorialCredits(), course.getPracticalCredits(), course.getTotalCredits());

            if (!entryExists) {
                course.setBatchYearDeptProgramSemId(batchYearDeptProgramSem1.getBatchYearDeptProgramSemId());
                course.setDepartment(batchYearDeptProgramSem1.getDepartment());
                course.setProgram(batchYearDeptProgramSem1.getProgram());
                course.setSemester(batchYearDeptProgramSem1.getSemester());
                course.setBatch(batchYearDeptProgramSem1.getBatch());
                course.setAcademicYear(batchYearDeptProgramSem1.getAcademicYear());
               course.setCourseType(courseType.getTypeOfCourse());

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
        return "redirect:/admin/courseEdit/" + batchYearDeptProgramSemId + "/" + courseType.getTypeOfCourse();
//        return "redirect:/admin/courseEdit?id=" + batchYearDeptProgramSemId;
    }


    @GetMapping(value="/deleteCourse/{courseId}")
    public String courseDelete(Model model, @PathVariable(required = true, name = "courseId") Long courseId, RedirectAttributes attributes, BatchYearDeptProgramSem batchYearDeptProgramSem) {
        Course course1 = courseService.findOne(courseId);
        Long batchYearDeptProgramSemId = course1.getBatchYearDeptProgramSemId();

        courseService.deleteCourse(courseId);
       attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully");

        //model.addAttribute("courses", courseService.findAll());
     return "redirect:/admin/listCourse/" + batchYearDeptProgramSemId;
    }
}

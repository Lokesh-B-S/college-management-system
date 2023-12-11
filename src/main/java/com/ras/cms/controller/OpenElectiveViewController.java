package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.batch.BatchService;
import com.ras.cms.service.batchyeardeptprogramsem.BatchYearDeptProgramSemService;
//import com.ras.cms.service.course.OpenElectiveService;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import com.ras.cms.service.openelectivetype.OpenElectiveTypeService;
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
public class OpenElectiveViewController {
    
    @Autowired
    OpenElectiveService openElectiveService;

    @Autowired
    OpenElectiveTypeService openElectiveTypeService;

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




//    @Autowired
//    private HttpServletRequest request;
//
//    private String userName;
//    private String departmentFromUserName;

//    @PostConstruct
//    public void init(){
//        userName = request.getUserPrincipal().getName();
//        departmentFromUserName= getDepartmentFromUserName(userName);
//    }

//    private String getDepartmentFromUserName(String userName){
//        String department = "";
//        if ("hodcse".equals(userName)) {
//            department = "Computer Science";
//        } else if ("hodise".equals(userName)) {
//            department = "Information Science";
//        } else if ("hodmech".equals(userName)) {
//            department = "Mechanical";
//        } else if ("hodiem".equals(userName)) {
//            department = "Industrial Engineering and Management";
//        }
//        return department;
//    }

    @GetMapping(value = {"/admin/listOpenElective/{batchYearSemTermId}/{openElectiveTypeId}", "/hod/listOpenElective/{batchYearSemTermId}"})
    public String openElectiveCourseList(Model model, HttpServletRequest request, Authentication authentication,
                                         @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId,
                                        @PathVariable(required = false, name = "openElectiveTypeId") Long openElectiveTypeId)
                                          {


        if (null != batchYearSemTermId) {
            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.findOne(batchYearSemTermId);
            model.addAttribute("batchYearSemTermView", batchYearSemTerm);

                if (request.isUserInRole("PRINCIPAL")) {

                    if (openElectiveTypeId != null) {
                        OpenElectiveType electiveType = openElectiveTypeService.findOne(openElectiveTypeId); // Fetch the OpenElectiveType
                        if (electiveType != null) {
                            List<OpenElective> AllDeptOpenElectives = openElectiveService.getOpenElectivesByBatchYearSemTermIdAndOpenElectiveType(batchYearSemTermId, electiveType);
                            model.addAttribute("AllDeptOpenElectives", AllDeptOpenElectives);
                        }
                        else{
                            System.out.println("elective type not found");
                        }
                    }
                }
                else  if (request.isUserInRole("DEPT_HEAD")) {
                    List<OpenElective> AllDeptOpenElectives = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);
                    model.addAttribute("AllDeptOpenElectives", AllDeptOpenElectives);

                    //Calculate the total sum of lectureCredits, tutorialCredits, practicalCredits, totalCredits, and contactHours
                    Long totalLectureCredits = AllDeptOpenElectives.stream().mapToLong(OpenElective::getLectureCredits).sum();
                    Long totalTutorialCredits = AllDeptOpenElectives.stream().mapToLong(OpenElective::getTutorialCredits).sum();
                    Long totalPracticalCredits = AllDeptOpenElectives.stream().mapToLong(OpenElective::getPracticalCredits).sum();
                    Long totalTotalCredits = AllDeptOpenElectives.stream().mapToLong(OpenElective::getTotalCredits).sum();
                    Long totalContactHours = AllDeptOpenElectives.stream().mapToLong(OpenElective::getContactHours).sum();

                    model.addAttribute("totalLectureCredits", totalLectureCredits);
                    model.addAttribute("totalTutorialCredits", totalTutorialCredits);
                    model.addAttribute("totalPracticalCredits", totalPracticalCredits);
                    model.addAttribute("totalTotalCredits", totalTotalCredits);
                    model.addAttribute("totalContactHours", totalContactHours);
                }

            }
            return "/openElectiveList";
//        List<OpenElective> entries = openElectiveService.getEntriesByBatchYearDeptProgramSemId(id);
//        model.addAttribute("id", id);
//
//        BatchYearDeptProgramSem batchYearDeptProgramSem = batchYearDeptProgramSemService.findOne(id);
//        if (batchYearDeptProgramSem != null) {
//            List<OpenElective> openElectives = openElectiveService.getCoursesByDepartmentAndSemester(batchYearDeptProgramSem.getDepartment(), batchYearDeptProgramSem.getSemester());
//            model.addAttribute("openElectives", openElectives);
//            model.addAttribute("batchYearDeptProgramSemView", batchYearDeptProgramSem);
//
//            // Calculate the total sum of lectureCredits, tutorialCredits, practicalCredits, totalCredits, and contactHours
//            Long totalLectureCredits = openElectives.stream().mapToLong(OpenElective::getLectureCredits).sum();
//            Long totalTutorialCredits = openElectives.stream().mapToLong(OpenElective::getTutorialCredits).sum();
//            Long totalPracticalCredits = openElectives.stream().mapToLong(OpenElective::getPracticalCredits).sum();
//            Long totalTotalCredits = openElectives.stream().mapToLong(OpenElective::getTotalCredits).sum();
//            Long totalContactHours = openElectives.stream().mapToLong(OpenElective::getContactHours).sum();
//
//            model.addAttribute("openElectives", openElectives);
//            //model.addAttribute("teachingDepartments",teachingDepartmentService.findAll());
//            model.addAttribute("totalLectureCredits", totalLectureCredits);
//            model.addAttribute("totalTutorialCredits", totalTutorialCredits);
//            model.addAttribute("totalPracticalCredits", totalPracticalCredits);
//            model.addAttribute("totalTotalCredits", totalTotalCredits);
//            model.addAttribute("totalContactHours", totalContactHours);
//        } else {
//            model.addAttribute("openElectives", openElectiveService.findAll());
//        }
//
//        //model.addAttribute("courseList", openElectiveService.findAll());
//        return "/openElectiveList";
    }

    @GetMapping({"/admin/selectOpenElective", "hod/selectOpenElective"})
    public String selectOpenElectiveCourse(Model model) {

        List<Batch> batches = batchService.findAll();
        List<AcademicYear> academicYears = academicYearService.findAll();
        List<Semester> semesters = semesterService.findAll();
        List<Term> terms = termService.findAll();
        List<OpenElectiveType> openElectiveTypes = openElectiveTypeService.findAll();

        model.addAttribute("batches", batches);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("semesters", semesters);
        model.addAttribute("terms", terms);
        model.addAttribute("openElectiveTypes", openElectiveTypes);

       // model.addAttribute("batchYearDeptProgramSem", new BatchYearDeptProgramSem());
        model.addAttribute("batchYearSemTerm", new BatchYearSemTerm());
        return "/openElectiveSelection";
    }


    @PostMapping({"hod/selectOpenElective", "/admin/selectOpenElective"})
    public String selectandsubmitOpenElectiveCourse(Model model, Authentication authentication,
                                                    BatchYearSemTerm batchYearSemTerm, RedirectAttributes redirectAttributes, HttpServletRequest request,
                                                    @RequestParam(required= false, name="openElectiveType") Long openElectiveTypeId) {

       System.out.println(openElectiveTypeId);
        if (batchYearSemTermService.existsEntry(batchYearSemTerm)) {
            redirectAttributes.addFlashAttribute("message", "Entry already exists.");
            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);

            if (request.isUserInRole("PRINCIPAL")) {
            return "redirect:/admin/listOpenElective/" + batchYearSemTerm1.getBatchYearSemTermId() + "/" + openElectiveTypeId ;
            }
            else if(request.isUserInRole("DEPT_HEAD")){
//                return "redirect:/hod/selectOpenElectiveType?id=" + batchYearSemTerm1.getBatchYearSemTermId();
                return "redirect:/hod/listOpenElective/" + batchYearSemTerm1.getBatchYearSemTermId();
//                return "redirect:/hod/openElectiveEdit/" + batchYearSemTerm1.getBatchYearSemTermId();

            }
        } else {
            try {
                batchYearSemTermService.saveEntry(batchYearSemTerm);
                try {
                    BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findOne(batchYearSemTerm.getBatchYearSemTermId());
                    model.addAttribute("batchYearSemTerm1", batchYearSemTerm1);
                    if (request.isUserInRole("PRINCIPAL")) {
                        return "redirect:/admin/listOpenElective/" + batchYearSemTerm1.getBatchYearSemTermId() + "/" + openElectiveTypeId ;
                    } else if (request.isUserInRole("DEPT_HEAD")) {
                        //if i had to use openElectiveEdit(non dynamic)
//                        return "redirect:/hod/openElectiveEdit/" + batchYearSemTerm.getBatchYearSemTermId();
                        return "redirect:/hod/listOpenElective/" + batchYearSemTerm1.getBatchYearSemTermId();

                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        }
        return "/403";
    }

//    @GetMapping({"hod/selectOpenElectiveType", "/admin/selectOpenElectiveType"})
//    public String selectOpenElectiveCourseType(Model model,
//                                               HttpServletRequest request,
//                                               @RequestParam(required = false, name = "id") Long id) {
//
//        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
//        model.addAttribute("department", departmentAndProgramFetch.getDepartment());
//        model.addAttribute("program", departmentAndProgramFetch.getProgram());
//
//        if (null != id) {
//            model.addAttribute("batchYearSemTerm1", batchYearSemTermService.findOne(id));
//        } else {
//            model.addAttribute("batchYearSemTerm1", new BatchYearSemTerm());
//        }
//
//        List<OpenElectiveType> openElectiveTypes = openElectiveTypeService.findAll();
//        model.addAttribute("openElectiveTypes", openElectiveTypes);
//        return "openElectiveTypeSelect";
//    }

//    @PostMapping({"hod/selectOpenElectiveType","/admin/selectOpenElectiveType"})
//    public String selectandsubmitOpenElectiveCourseType(Model model,@ModelAttribute OpenElectiveType openElectiveType, BatchYearSemTerm batchYearSemTerm, RedirectAttributes redirectAttributes, HttpServletRequest request) {
//
//        System.out.println(openElectiveType.getId() +  openElectiveType.getTypeOfOpenElective());
//        BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);
//
//
//        if(request.isUserInRole("PRINCIPAL")){
//            return "redirect:/admin/listOpenElective/" + batchYearSemTerm1.getBatchYearSemTermId() + "/" + openElectiveType.getTypeOfOpenElective() ;
//        }
//        else if(request.isUserInRole("DEPT_HEAD")){
//            return "redirect:/hod/openElectiveEdit/" + batchYearSemTerm1.getBatchYearSemTermId() + "/" + openElectiveType.getTypeOfOpenElective() ;
//        }
//      return "/403";
//    }


    @GetMapping(value = {"/hod/openElectiveEdit", "/hod/openElectiveEdit/{openElectiveId}", "/hod/openElectiveEdit/{batchYearSemTermId}"})
    public String openElectiveCourseEdit(Model model, HttpServletRequest request,
                             @RequestParam(required = false, name = "id") Long id,
                             @PathVariable(required = false, name="openElectiveId") Long openElectiveId,
                             @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId)
                             {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        model.addAttribute("department", departmentAndProgramFetch.getDepartment());
        model.addAttribute("program", departmentAndProgramFetch.getProgram());

        //System.out.println(typeOfOpenElective);

        if (null != batchYearSemTermId) {
            model.addAttribute("batchYearSemTerm1", batchYearSemTermService.findOne(batchYearSemTermId));
        } else {
            model.addAttribute("batchYearSemTerm1", new BatchYearSemTerm());
        }

        // BatchYearDeptProgramSem batchYearDeptProgramSem = batchYearDeptProgramSemService.findOne(id);
        if(openElectiveId!=null){
            model.addAttribute("openElective", openElectiveService.findOne(openElectiveId));
            OpenElective course1 = openElectiveService.findOne(openElectiveId);
            Long batchYearSemTermId1 = course1.getBatchYearSemTermId();
            model.addAttribute("batchYearSemTerm1", batchYearSemTermService.findOne(batchYearSemTermId1));
        }
        else {
            model.addAttribute("openElective", new OpenElective());
        }

       // OpenElectiveType openElectiveType = openElectiveTypeService.findAll();
        model.addAttribute("openElectiveTypes", openElectiveTypeService.findAll());

        model.addAttribute("teachingDepartments", teachingDepartmentService.findAll());
      //  System.out.println(courseType.getTypeOfCourse() + " in get");

        return "/openElectiveEdit";
    }

    @PostMapping(value = "/hod/openElectiveEdit")
    public String openElectiveCourseEdit(Model model,
                                         HttpServletRequest request,
                                         @ModelAttribute OpenElectiveType openElectiveType, OpenElective openElective, BatchYearSemTerm batchYearSemTerm1, RedirectAttributes attributes, @RequestParam Long batchYearSemTermId) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);

        try {
            // Check if an entry already exists for the given parameters, if yes set the batch,program etc values to course and save
            boolean entryExists = openElectiveService.doesEntryExist(batchYearSemTermId, openElective.getContactHours(),openElective.getCourseBatchesCount(), openElective.getCourseCode(), openElective.getCourseName(), openElective.getTeachingDepartment(), openElective.getOpenElectiveType(), openElective.getLectureCredits(), openElective.getTutorialCredits(), openElective.getPracticalCredits(), openElective.getTotalCredits());

            if (!entryExists) {
                openElective.setBatchYearSemTermId(batchYearSemTerm1.getBatchYearSemTermId());
                openElective.setDepartment(departmentAndProgramFetch.getDepartment());
                openElective.setProgram(departmentAndProgramFetch.getProgram());
                openElective.setSemester(batchYearSemTerm1.getSemester());
                openElective.setTerm(batchYearSemTerm1.getTerm());
                openElective.setBatch(batchYearSemTerm1.getBatch());
                openElective.setAcademicYear(batchYearSemTerm1.getAcademicYear());
                //openElective.setOpenElectiveType(openElectiveType.getTypeOfOpenElective());

                System.out.println(openElectiveType.getTypeOfOpenElective() + " in post");
                openElectiveService.saveOpenElective(openElective);

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
        return "redirect:/hod/openElectiveEdit/" + batchYearSemTermId;
//        return "redirect:/admin/courseEdit?id=" + batchYearDeptProgramSemId;
    }



    @GetMapping(value={"/hod/deleteOpenElective/{openElectiveId}","/admin/deleteOpenElective/{openElectiveId}"})
    public String openElectiveCourseDeleteHOD(Model model, @PathVariable(required = true, name = "openElectiveId") Long openElectiveId, RedirectAttributes attributes, BatchYearSemTerm batchYearSemTerm, HttpServletRequest request) {
        OpenElective course1 = openElectiveService.findOne(openElectiveId);
        Long batchYearSemTermId = course1.getBatchYearSemTermId();
        Long openElectiveTypeId = course1.getOpenElectiveType().getId();

        openElectiveService.deleteOpenElective(openElectiveId);
        attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully");

        if(request.isUserInRole("DEPT_HEAD")) {
            return "redirect:/hod/listOpenElective/" + batchYearSemTermId ;
        }
        else if(request.isUserInRole("PRINCIPAL")) {
            return "redirect:/admin/listOpenElective/" + batchYearSemTermId + "/" + openElectiveTypeId;
        }

        return "/403";
        }

//
//    public String openElectiveCourseDeletePrincipal(Model model, @PathVariable(required = true, name = "openElectiveId") Long openElectiveId, RedirectAttributes attributes, BatchYearSemTerm batchYearSemTerm) {
//        OpenElective course1 = openElectiveService.findOne(openElectiveId);
//        Long batchYearSemTermId = course1.getBatchYearSemTermId();
//        String typeOfOpenElective = course1.getCourseType();
//
//        openElectiveService.deleteOpenElective(openElectiveId);
//        attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully");
//
//        return "redirect:/admin/listOpenElective/" + batchYearSemTermId + "/" + typeOfOpenElective;
//    }

//
//    @GetMapping(value={"/hod/deleteOpenElective/{openElectiveId}","/admin/deleteOpenElective/{openElectiveId}"})
//    public String openElectiveCourseDelete(Model model,Authentication authentication, @PathVariable(required = true, name = "openElectiveId") Long openElectiveId, RedirectAttributes attributes, BatchYearDeptProgramSem batchYearDeptProgramSem) {
//        OpenElective course1 = openElectiveService.findOne(openElectiveId);
//        Long batchYearDeptProgramSemId = course1.getBatchYearDeptProgramSemId();
//
//        openElectiveService.deleteOpenElective(openElectiveId);
//        attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully");
//
//        //model.addAttribute("courses", openElectiveService.findAll());
//
//        if (authentication != null && authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("PRINCIPAL"))) {
//            return "redirect:/admin/listOpenElective";
//        }
//        else if(authentication != null && authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("DEPT_HEAD"))) {
//            return "redirect:/hod/listOpenElective/" + batchYearDeptProgramSemId;
//        }
//        return "/";
//    }


}

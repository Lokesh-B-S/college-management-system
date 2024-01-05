package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.faculty.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/admin")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping(value={"/admin/listFaculty/{departmentId}","/hod/listFaculty/{departmentId}","/hod/listFaculty","/faculty/listFaculty"})
    public String facultyList(Model model, HttpServletRequest request,
                              @PathVariable(required = false, name="departmentId")Long departmentId) {


        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        model.addAttribute("departmentLoggedIn", departmentAndProgramFetch.getDepartment());
        //
        if(departmentId != null){
            Department department = departmentService.findOne(departmentId);
            List<Faculty> faculties = facultyService.getFacultiesByDepartment(department);
            model.addAttribute("department", department);
            model.addAttribute("facultyList", faculties);
//            System.out.println(faculties);
        }
        else {

            if(request.isUserInRole("DEPT_HEAD")){
                List<Faculty> faculties = facultyService.getFacultiesByDepartment(departmentAndProgramFetch.getDepartment());
                model.addAttribute("department", departmentAndProgramFetch.getDepartment());
                model.addAttribute("facultyList", faculties);
            }

        }

//        model.addAttribute("facultyList", facultyService.findAll());
        return "/facultyList";
    }


    @GetMapping({"/hod/selectDeptForFaculty", "/admin/selectDeptForFaculty"})
    public String deptSelectionforFaculty(Model model){
        model.addAttribute("departments", departmentService.findAll());
        return "/AllDeptFaculty";
    }

    @PostMapping({"/hod/selectDeptForFaculty", "/admin/selectDeptForFaculty"})
    public String postDeptSelectionforFaculty(HttpServletRequest request,
                                              @RequestParam(required = true, name="department") Long departmentId){

        Department department = departmentService.findOne(departmentId);

        if (request.isUserInRole("PRINCIPAL")) {
            if (facultyService.existsFacultyByDepartment(department)) {
                return "redirect:/admin/listFaculty/" + departmentId;
            }
            else{
                return "redirect:/admin/selectDeptForFaculty";
            }
        }

        else if (request.isUserInRole("DEPT_HEAD")) {
            if (facultyService.existsFacultyByDepartment(department)) {
                return "redirect:/hod/listFaculty/" + departmentId;
            }
            else{
                return "redirect:/hod/selectDeptForFaculty";
            }
        }
//            BatchYearSemTerm batchYearSemTerm1 = batchYearSemTermService.findRow(batchYearSemTerm);

        return "/403";

//        return "redirect:/hod/listFaculty/";
    }



    @GetMapping(value={"/hod/editFaculty","/hod/editFaculty/{id}"})
    public String facultyEdit(Model model,
                              HttpServletRequest request,
                              @PathVariable(required = false, name = "id") Long id) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        model.addAttribute("department", departmentAndProgramFetch.getDepartment());

        if (null != id) {
            model.addAttribute("faculty", facultyService.findOne(id));
            model.addAttribute("categories", getCategory());
        } else {
            model.addAttribute("faculty", new Faculty());
            model.addAttribute("categories", getCategory());
        }
        return "/facultyEdit";
    }

    @PostMapping("/hod/editFaculty")
    public String facultyEdit(Model model, Faculty faculty, HttpServletRequest request){

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        faculty.setDepartment(departmentAndProgramFetch.getDepartment());
        facultyService.saveFaculty(faculty);
       // List<Faculty> facultyList = facultyService.findAll();
        model.addAttribute("facultyList",facultyService.findAll());
        return "/facultyList";
    }

    @GetMapping(value="/hod/deleteFaculty/{id}")
    public String facultyDelete(Model model,@PathVariable(required = true, name = "id") Long id){
        facultyService.deleteFaculty(id);
        model.addAttribute("facultyList",facultyService.findAll());
        return "/facultyList";
    }

    Map<String,String> getCategory() {
        Map<String, String> category = new LinkedHashMap<>();
        category.put("", "-- Select Category --");
        category.put("SC", "SC");
        category.put("ST", "ST");
        category.put("CAT-1", "1");
        category.put("CAT-2 A", "2A");
        category.put("CAT-2 B", "2B");
        category.put("CAT-3 A", "3A");
        category.put("CAT-3 B", "3B");
        category.put("CAT-1", "GM");
        category.put("Rural", "Rural");
        return category;
    }
}

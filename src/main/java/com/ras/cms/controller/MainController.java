package com.ras.cms.controller;

import com.ras.cms.domain.*;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import com.ras.cms.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//to fetch all the dynamic pages
@Controller
public class MainController {

@Autowired
    BatchYearSemTermService batchYearSemTermService;

@Autowired
    OpenElectiveService openElectiveService;

@Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

@Autowired
    ProgramService programService;

@GetMapping("/admin/programs/addnew")
    public String programs(Model model){return "ProgramD";}

    @GetMapping("/admin/departments/addnew")
    public String departments(Model model){return "departmentD";}

    @GetMapping("/hod/openElectives/addnew/{batchYearSemTermId}")
    public String openElectives(Model model,
                                HttpServletRequest request,
        @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId){
        if (null != batchYearSemTermId) {
            System.out.println(batchYearSemTermId);

            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.findOne(batchYearSemTermId);
            model.addAttribute("batchYearSemTerm", batchYearSemTerm);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            model.addAttribute("department", departmentAndProgramFetch.getDepartment());

           //note this program is functionally correct but logically wrong
            model.addAttribute("program", departmentAndProgramFetch.getProgram());

            //to know the type() of a variable
            System.out.println(departmentAndProgramFetch.getDepartment().getDepartmentId().getClass().getSimpleName());
            System.out.println(departmentAndProgramFetch.getProgram().getProgramId().getClass().getSimpleName());
            //to fetch these ids
            //model.addAttribute("departmentId", departmentAndProgramFetch.getDepartment().getDepartmentId());
            //model.addAttribute("programId", departmentAndProgramFetch.getProgram().getProgramId());

        }
    return "OpenElectiveEditD";}




    @GetMapping("/hod/courses/addnew/{batchYearSemTermId}/{programId}")
    public String courses(Model model,
                                HttpServletRequest request,
                                @PathVariable(required = false, name = "batchYearSemTermId") Long batchYearSemTermId,
                                @PathVariable(required = false, name = "programId") Long programId){
        if (null != batchYearSemTermId) {
            System.out.println(batchYearSemTermId);

            BatchYearSemTerm batchYearSemTerm = batchYearSemTermService.findOne(batchYearSemTermId);
            model.addAttribute("batchYearSemTerm", batchYearSemTerm);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            model.addAttribute("department", departmentAndProgramFetch.getDepartment());

            Program program = programService.findOne(programId);
            model.addAttribute("program", program);
//            model.addAttribute("program", departmentAndProgramFetch.getProgram());

        }
        return "courseEditD";}
}

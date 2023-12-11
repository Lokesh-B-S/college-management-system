package com.ras.cms.controller;

import com.ras.cms.domain.BatchYearSemTerm;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentAndProgramFetch;
import com.ras.cms.domain.OpenElective;
import com.ras.cms.service.batchyearsemterm.BatchYearSemTermService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
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
            System.out.println(batchYearSemTerm.getBatchYearSemTermId());
            System.out.println(batchYearSemTerm.getBatch().getBatchName());
            System.out.println(batchYearSemTerm.getAcademicYear().getYear());
            System.out.println(batchYearSemTerm.getSemester().getSem());
            System.out.println(batchYearSemTerm.getTerm().getTermDate());

//            OpenElective openElective = openElectiveService.findOne(8L);
//            System.out.println(openElective);
//            model.addAttribute("openElective2",openElective);
            model.addAttribute("batchYearSemTerm", batchYearSemTerm);

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            System.out.println(departmentAndProgramFetch.getDepartment().getDepartmentId().getClass().getSimpleName());
            System.out.println(departmentAndProgramFetch.getProgram().getProgramId().getClass().getSimpleName());

            //model.addAttribute("departmentId", departmentAndProgramFetch.getDepartment().getDepartmentId());
            //model.addAttribute("programId", departmentAndProgramFetch.getProgram().getProgramId());

            model.addAttribute("department", departmentAndProgramFetch.getDepartment());
            model.addAttribute("program", departmentAndProgramFetch.getProgram());

        }
    return "OpenElectiveEditD";}
}

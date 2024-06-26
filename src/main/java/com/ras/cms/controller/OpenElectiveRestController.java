package com.ras.cms.controller;

import com.ras.cms.dao.DepartmentUpdateDAO;
import com.ras.cms.dao.OpenElectiveUpdateDAO;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentAndProgramFetch;
import com.ras.cms.domain.OpenElective;
import com.ras.cms.repository.OpenElectiveRepository;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.openElectiveService.OpenElectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping()
public class OpenElectiveRestController {

    @Autowired
    OpenElectiveService openElectiveService;

    @Autowired
    OpenElectiveRepository openElectiveRepo;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @GetMapping(value = {"/hod/openElectives", "/hod/openElectives/{batchYearSemTermId}"})
    public List<OpenElective> openElectivesList(Model model,
                                                HttpServletRequest request,
                                                @PathVariable(required = false, name="batchYearSemTermId") Long batchYearSemTermId) {

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        //  List<OpenElective> openElectives = openElectiveService.findAll();
      //  List<OpenElective> openElectives = openElectiveService.getOpenElectivesByBatchYearSemTermId(batchYearSemTermId);
        List<OpenElective> openElectives = openElectiveService.getOpenElectivesByBatchYearSemTermIdAndDepartment(batchYearSemTermId, dep);
        model.addAttribute("openElectives", openElectives);

        return openElectives;
    }


    @PostMapping(value = "/hod/openElectives", consumes = "application/json", produces = "application/json")
    public List<OpenElective> updateOpenElectives(final @RequestBody List<OpenElectiveUpdateDAO> list) {
        List<OpenElective> toDelete = list.stream().filter(o -> o.getAction() == OpenElectiveUpdateDAO.Action.DELETE)
                .map(OpenElectiveUpdateDAO::getData).collect(Collectors.toList());
        List<OpenElective> toUpdate = list.stream().filter(o -> o.getAction() == OpenElectiveUpdateDAO.Action.UPDATE)
                .map(OpenElectiveUpdateDAO::getData).collect(Collectors.toList());

        List<OpenElective> result = new ArrayList<>();

        if (!toDelete.isEmpty()) {
            openElectiveRepo.deleteInBatch(toDelete);
        }
        if (!toUpdate.isEmpty()) {
            result = openElectiveRepo.saveAll(toUpdate);
        }

        return result;
    }
}

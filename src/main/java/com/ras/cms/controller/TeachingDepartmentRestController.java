package com.ras.cms.controller;

import com.ras.cms.dao.OpenElectiveUpdateDAO;
import com.ras.cms.dao.TeachingDepartmentUpdateDAO;
import com.ras.cms.domain.OpenElective;
import com.ras.cms.domain.TeachingDepartment;
import com.ras.cms.repository.TeachingDepartmentRepository;
import com.ras.cms.service.teachingdepartment.TeachingDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeachingDepartmentRestController {


    @Autowired
    TeachingDepartmentService teachingDepartmentService;

    @Autowired
    TeachingDepartmentRepository teachingDepartmentRepo;
    @GetMapping(value = "/hod/teachingDepartments")
    public List<TeachingDepartment> TeachingDepartmentList(Model model) {

        List<TeachingDepartment> teachingDepartments = teachingDepartmentService.findAll();
        model.addAttribute("teachingDepartments", teachingDepartments);
        return teachingDepartmentRepo.findAll();
    }


    @PostMapping(value = "/hod/teachingDepartments", consumes = "application/json", produces = "application/json")
    public List<TeachingDepartment> updateTeachingDepartments(final @RequestBody List<TeachingDepartmentUpdateDAO> list) {
        List<TeachingDepartment> toDelete = list.stream().filter(o -> o.getAction() == TeachingDepartmentUpdateDAO.Action.DELETE)
                .map(TeachingDepartmentUpdateDAO::getData).collect(Collectors.toList());
        List<TeachingDepartment> toUpdate = list.stream().filter(o -> o.getAction() == TeachingDepartmentUpdateDAO.Action.UPDATE)
                .map(TeachingDepartmentUpdateDAO::getData).collect(Collectors.toList());

        List<TeachingDepartment> result = new ArrayList<>();

        if (!toDelete.isEmpty()) {
            teachingDepartmentRepo.deleteInBatch(toDelete);
        }
        if (!toUpdate.isEmpty()) {
            result = teachingDepartmentRepo.saveAll(toUpdate);
        }

        return result;
    }
}

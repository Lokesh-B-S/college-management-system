package com.ras.cms.controller;

import com.ras.cms.dao.ProgramUpdateDAO;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.Program;
//import com.ras.cms.domain.User;
import com.ras.cms.repository.ProgramRepository;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/programs")
public class ProgramRestController {

    @Autowired
    ProgramService programService;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public List<Program> programList(Model model) {

        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments",departments);
        return programRepository.findAll();
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public List<Program> updatePrograms(final @RequestBody List<ProgramUpdateDAO> list) {
        List<Program> toDelete = list.stream().filter(o -> o.getAction() == ProgramUpdateDAO.Action.DELETE)
                .map(ProgramUpdateDAO::getData).collect(Collectors.toList());
        List<Program> toUpdate = list.stream().filter(o -> o.getAction() == ProgramUpdateDAO.Action.UPDATE)
                .map(ProgramUpdateDAO::getData).collect(Collectors.toList());

        List<Program> result = new ArrayList<>();

        if(!toDelete.isEmpty()){
            programRepository.deleteInBatch(toDelete);
        }
        if(!toUpdate.isEmpty()){
            result = programRepository.saveAll(toUpdate);
        }

        return result;
    }

}

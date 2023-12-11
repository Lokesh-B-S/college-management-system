package com.ras.cms.controller;

import com.ras.cms.dao.OpenElectiveTypeUpdateDAO;
import com.ras.cms.dao.OpenElectiveUpdateDAO;
import com.ras.cms.domain.OpenElective;
import com.ras.cms.domain.OpenElectiveType;
import com.ras.cms.repository.OpenElectiveTypeRepository;
import com.ras.cms.service.openelectivetype.OpenElectiveTypeService;
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
public class OpenElectiveTypeRestController {


    @Autowired
    OpenElectiveTypeService openElectiveTypeService;

    @Autowired
    OpenElectiveTypeRepository openElectiveTypeRepo;
    @GetMapping(value = "/hod/openElectiveTypes")
    public List<OpenElectiveType> openElectiveTypesList(Model model) {

        List<OpenElectiveType> openElectiveTypes = openElectiveTypeService.findAll();
        model.addAttribute("openElectiveTypes", openElectiveTypes);
        return openElectiveTypeRepo.findAll();
    }


    @PostMapping(value = "/hod/openElectiveTypes", consumes = "application/json", produces = "application/json")
    public List<OpenElectiveType> updateOpenElectiveTypes(final @RequestBody List<OpenElectiveTypeUpdateDAO> list) {
        List<OpenElectiveType> toDelete = list.stream().filter(o -> o.getAction() == OpenElectiveTypeUpdateDAO.Action.DELETE)
                .map(OpenElectiveTypeUpdateDAO::getData).collect(Collectors.toList());
        List<OpenElectiveType> toUpdate = list.stream().filter(o -> o.getAction() == OpenElectiveTypeUpdateDAO.Action.UPDATE)
                .map(OpenElectiveTypeUpdateDAO::getData).collect(Collectors.toList());

        List<OpenElectiveType> result = new ArrayList<>();

        if (!toDelete.isEmpty()) {
            openElectiveTypeRepo.deleteInBatch(toDelete);
        }
        if (!toUpdate.isEmpty()) {
            result = openElectiveTypeRepo.saveAll(toUpdate);
        }

        return result;
    }

}

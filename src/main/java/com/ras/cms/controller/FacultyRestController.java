package com.ras.cms.controller;

import com.ras.cms.dao.FacultyUpdateDAO;
import com.ras.cms.dao.TeachingDepartmentUpdateDAO;
import com.ras.cms.domain.Faculty;
import com.ras.cms.domain.TeachingDepartment;
import com.ras.cms.repository.FacultyRepository;
import com.ras.cms.repository.TeachingDepartmentRepository;
import com.ras.cms.service.faculty.FacultyService;
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
public class FacultyRestController {

        @Autowired
    FacultyService facultyService;

        @Autowired
    FacultyRepository facultyRepository;

        @GetMapping(value = "/hod/faculties")
        public List<Faculty> FacultyList(Model model) {

            List<Faculty> faculties = facultyService.findAll();
            model.addAttribute("faculties", faculties);
            return facultyRepository.findAll();
        }


        @PostMapping(value = "/hod/faculties", consumes = "application/json", produces = "application/json")
        public List<Faculty> updateFaculties(final @RequestBody List<FacultyUpdateDAO> list) {
            List<Faculty> toDelete = list.stream().filter(o -> o.getAction() == FacultyUpdateDAO.Action.DELETE)
                    .map(FacultyUpdateDAO::getData).collect(Collectors.toList());
            List<Faculty> toUpdate = list.stream().filter(o -> o.getAction() == FacultyUpdateDAO.Action.UPDATE)
                    .map(FacultyUpdateDAO::getData).collect(Collectors.toList());

            List<Faculty> result = new ArrayList<>();

            if (!toDelete.isEmpty()) {
                facultyRepository.deleteInBatch(toDelete);
            }
            if (!toUpdate.isEmpty()) {
                result = facultyRepository.saveAll(toUpdate);
            }

            return result;
        }


}

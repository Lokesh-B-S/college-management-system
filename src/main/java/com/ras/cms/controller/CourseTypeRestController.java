package com.ras.cms.controller;

import com.ras.cms.dao.CourseTypeUpdateDAO;
import com.ras.cms.dao.OpenElectiveTypeUpdateDAO;
import com.ras.cms.domain.CourseType;
import com.ras.cms.domain.OpenElectiveType;
import com.ras.cms.repository.CourseTypeRepository;
import com.ras.cms.repository.OpenElectiveTypeRepository;
import com.ras.cms.service.coursetype.CourseTypeService;
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

//used in courseEditD for dynamic fetch

@RestController
public class CourseTypeRestController {


        @Autowired
        CourseTypeService courseTypeService;

        @Autowired
        CourseTypeRepository courseTypeRepo;
        @GetMapping(value = "/hod/courseTypes")
        public List<CourseType> courseTypesList(Model model) {

            List<CourseType> courseTypes = courseTypeService.findAll();
            model.addAttribute("courseTypes", courseTypes);
            return courseTypeRepo.findAll();
        }


        @PostMapping(value = "/hod/courseTypes", consumes = "application/json", produces = "application/json")
        public List<CourseType> updateCourseTypes(final @RequestBody List<CourseTypeUpdateDAO> list) {
            List<CourseType> toDelete = list.stream().filter(o -> o.getAction() == CourseTypeUpdateDAO.Action.DELETE)
                    .map(CourseTypeUpdateDAO::getData).collect(Collectors.toList());
            List<CourseType> toUpdate = list.stream().filter(o -> o.getAction() == CourseTypeUpdateDAO.Action.UPDATE)
                    .map(CourseTypeUpdateDAO::getData).collect(Collectors.toList());

            List<CourseType> result = new ArrayList<>();

            if (!toDelete.isEmpty()) {
                courseTypeRepo.deleteInBatch(toDelete);
            }
            if (!toUpdate.isEmpty()) {
                result = courseTypeRepo.saveAll(toUpdate);
            }

            return result;
        }

    }



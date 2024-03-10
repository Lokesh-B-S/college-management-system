package com.ras.cms.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.core.JsonProcessingException;

import com.ras.cms.dao.CourseUpdateDAO;
import com.ras.cms.dao.EligibleStudentUpdateDAO;
import com.ras.cms.domain.*;
import com.ras.cms.repository.CourseRepository;
import com.ras.cms.repository.EligibleStudentRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EligibleStudentRestController {

        @Autowired
        EligibleStudentController eligibleStudentController;

        @Autowired
        EligibleStudentRepository eligibleStudentRepository;

        @Autowired
        EligibleStudentService eligibleStudentService;

        @Autowired
        ProgramService programService;

        @Autowired
    AcademicYearService academicYearService;

        @Autowired
    TermService termService;
        @Autowired
    SemesterService semesterService;

        @Autowired
        DepartmentProgramFetchService departmentProgramFetchService;

        @GetMapping(value = {"/hod/eligibleStudents", "/hod/eligibleStudents/{academicYearId}/{programId}/{semId}/{termId}"})
        public List<EligibleStudent> eligibleStudentsListMethod(Model model,
                                                       HttpServletRequest request,
                                                       @PathVariable(required = false, name = "academicYearId") Long academicYearId,
                                                       @PathVariable(required = false, name = "programId") Long programId,
                                                       @PathVariable(required = false, name = "semId") Long semId,
                                                       @PathVariable(required = false, name = "termId") Long termId){

            DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
            Department dep = departmentAndProgramFetch.getDepartment();

            Program prog = programService.findOne(programId);
            AcademicYear academicYear = academicYearService.findOne(academicYearId);
            Semester semester = semesterService.findOne(semId);
            Term term = termService.findOne(termId);

            List<EligibleStudent> eligibleStudents = eligibleStudentService.getStudentsByAcademicYearAndProgramAndSemesterAndTerm(academicYear, prog, semester, term);
            model.addAttribute("eligibleStudents", eligibleStudents);

// Sort the list based on usn
            Collections.sort(eligibleStudents, Comparator.comparing(EligibleStudent::getUsn));

            return eligibleStudents;
        }


        @PostMapping(value = "/hod/eligibleStudents", consumes = "application/json", produces = "application/json")
        public List<EligibleStudent> updateEligibleStudents(final @RequestBody List<EligibleStudentUpdateDAO> list) {
            List<EligibleStudent> toDelete = list.stream().filter(o -> o.getAction() == EligibleStudentUpdateDAO.Action.DELETE)
                    .map(EligibleStudentUpdateDAO::getData).collect(Collectors.toList());
            List<EligibleStudent> toUpdate = list.stream().filter(o -> o.getAction() == EligibleStudentUpdateDAO.Action.UPDATE)
                    .map(EligibleStudentUpdateDAO::getData).collect(Collectors.toList());

            List<EligibleStudent> result = new ArrayList<>();

            if (!toDelete.isEmpty()) {
                eligibleStudentRepository.deleteInBatch(toDelete);
            }
            if (!toUpdate.isEmpty()) {
                result = eligibleStudentRepository.saveAll(toUpdate);
            }

            return result;
        }

//
//    String jsonInput = "[{\"eligibleStudentId\":1,\"department\":{\"departmentId\":1,\"departmentCode\":\"CS\",\"departmentName\":\"Computer Science\",\"college\":{\"courses\":[],\"collegeAddress\":\"aasjnsdsklcklscklacklsac\",\"collegeCode\":\"1122\",\"collegeName\":\"abcd\",\"collegeId\":1}},\"program\":{\"programId\":1,\"department\":{\"departmentId\":1,\"departmentCode\":\"CS\",\"departmentName\":\"Computer Science\",\"college\":{\"courses\":[],\"collegeAddress\":\"aasjnsdsklcklscklacklsac\",\"collegeCode\":\"1122\",\"collegeName\":\"abcd\",\"collegeId\":1}},\"programName\":\"BE - computer science\",\"programCode\":\"CSE\"},\"academicYear\":{\"id\":8,\"year\":\"2022-23\"},\"term\":{\"id\":1,\"termDate\":\"1-Jan-2023 to 10-Apr-2023\"},\"batch\":{\"batchId\":2,\"batchName\":\"1NH19\",\"department\":{\"departmentId\":1,\"departmentCode\":\"CS\",\"departmentName\":\"Computer Science\",\"college\":{\"courses\":[],\"collegeAddress\":\"aasjnsdsklcklscklacklsac\",\"collegeCode\":\"1122\",\"collegeName\":\"abcd\",\"collegeId\":1}},\"program\":{\"programId\":1,\"department\":{\"departmentId\":1,\"departmentCode\":\"CS\",\"departmentName\":\"Computer Science\",\"college\":{\"courses\":[],\"collegeAddress\":\"aasjnsdsklcklscklacklsac\",\"collegeCode\":\"1122\",\"collegeName\":\"abcd\",\"collegeId\":1}},\"programName\":\"BE - computer science\",\"programCode\":\"CSE\"}},\"semester\":{\"semId\":1,\"sem\":1},\"section\":null,\"regNumber\":\"1NH19CS001\",\"usn\":\"1NH19CS001\",\"name\":\"Lokesh\"}]";
//    ObjectMapper objectMapper = new ObjectMapper();
//    EligibleStudent eligibleStudent = null;
//
//    try {
//        eligibleStudent = objectMapper.readValue(jsonInput, EligibleStudent.class);
//    }
//    catch (JsonProcessingException e) {
//        // Handle exception, such as JSON parsing errors
//        e.printStackTrace();
//    }


}

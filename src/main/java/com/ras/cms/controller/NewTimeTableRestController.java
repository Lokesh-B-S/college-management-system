package com.ras.cms.controller;

import com.ras.cms.dao.EligibleStudentUpdateDAO;
import com.ras.cms.dao.NewTimeTableEntryUpdateDAO;
import com.ras.cms.domain.*;
import com.ras.cms.repository.EligibleStudentRepository;
import com.ras.cms.repository.NewTimeTableRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.day.DayService;
import com.ras.cms.service.departmentProgramFetch.DepartmentProgramFetchService;
import com.ras.cms.service.eligibleStudents.EligibleStudentService;
import com.ras.cms.service.newTimeTable.NewTimeTableService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NewTimeTableRestController {


    @Autowired
    ProgramService programService;

    @Autowired
    AcademicYearService academicYearService;

    @Autowired
    TermService termService;
    @Autowired
    SemesterService semesterService;

    @Autowired
    SectionService sectionService;

    @Autowired
    DayService dayService;

    @Autowired
    NewTimeTableService newTimeTableService;

    @Autowired
    NewTimeTableRepository newTimeTableRepository;

    @Autowired
    DepartmentProgramFetchService departmentProgramFetchService;

    @GetMapping(value = {"/hod/timeTableEntries", "/hod/timeTableEntries/{academicYearId}/{programId}/{semId}/{sectionId}/{termId}/{dayId}"})
    public List<NewTimeTableEntry> timeTableListMethod(Model model,
                                                            HttpServletRequest request,
                                                            @PathVariable(required = false, name = "academicYearId") Long academicYearId,
                                                            @PathVariable(required = false, name = "programId") Long programId,
                                                            @PathVariable(required = false, name = "semId") Long semId,
                                                            @PathVariable(required = false, name= "sectionId") Long sectionId,
                                                            @PathVariable(required = false, name = "termId") Long termId,
                                                            @PathVariable(required = false, name = "dayId") Long dayId){

        DepartmentAndProgramFetch departmentAndProgramFetch = departmentProgramFetchService.processRequest(request);
        Department dep = departmentAndProgramFetch.getDepartment();

        Program prog = programService.findOne(programId);
        AcademicYear academicYear = academicYearService.findOne(academicYearId);
        Semester semester = semesterService.findOne(semId);
        Section section = sectionService.findOne(sectionId);
        Term term = termService.findOne(termId);
        Day day = dayService.findOne(dayId);

        List<NewTimeTableEntry> timeTableEntries = newTimeTableService.getTimeTableEntriesByAcademicYearAndProgramAndSemesterAndSectionAndTermAndDay(academicYear, prog, semester, section, term, day);
        model.addAttribute("timeTableEntries", timeTableEntries);

// Sort
//        Collections.sort(timeTableEntries, Comparator.comparing(EligibleStudent::getUsn));

        return timeTableEntries;
    }


    @PostMapping(value = "/hod/timeTableEntries", consumes = "application/json", produces = "application/json")
    public List<NewTimeTableEntry> updateNewTimeTableEntries(final @RequestBody List<NewTimeTableEntryUpdateDAO> list) {
        List<NewTimeTableEntry> toDelete = list.stream().filter(o -> o.getAction() == NewTimeTableEntryUpdateDAO.Action.DELETE)
                .map(NewTimeTableEntryUpdateDAO::getData).collect(Collectors.toList());
        List<NewTimeTableEntry> toUpdate = list.stream().filter(o -> o.getAction() == NewTimeTableEntryUpdateDAO.Action.UPDATE)
                .map(NewTimeTableEntryUpdateDAO::getData).collect(Collectors.toList());

        List<NewTimeTableEntry> result = new ArrayList<>();

        if (!toDelete.isEmpty()) {
            newTimeTableRepository.deleteInBatch(toDelete);
        }
        if (!toUpdate.isEmpty()) {
            result = newTimeTableRepository.saveAll(toUpdate);
        }

        return result;
    }
}

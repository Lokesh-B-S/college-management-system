package com.ras.cms.controller;

import com.ras.cms.domain.Program;
import com.ras.cms.domain.TimeSlotSelection;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.timeslotselection.TimeSlotSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/hod")
public class TimeSlotSelectionController{

    @Autowired
    TimeSlotSelectionService timeSlotSelectionService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ProgramService programService;

    @GetMapping(value="/listTimeSlots")
    public String timeSlotList(Model model) {
        model.addAttribute("timeSlotList", timeSlotSelectionService.findAll());
        return "/timeSlotList";
    }

    @GetMapping(value={"/editTimeSlot","/editTimeSlot/{id}"})
    public String timeSlotEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("timeSlotSelection", timeSlotSelectionService.findOne(id));
        } else {
            model.addAttribute("timeSlotSelection", new TimeSlotSelection());
        }
        model.addAttribute("departments",departmentService.findAll());
        model.addAttribute("programs",programService.findAll());
        return "/timeSlotEdit";
    }

    @PostMapping(value="/editTimeSlot")
    public String timeSlotSelectionEdit(Model model, TimeSlotSelection timeSlotSelection) {

        timeSlotSelectionService.saveTimeSlotSelection(timeSlotSelection);
        model.addAttribute("timeSlotList", timeSlotSelectionService.findAll());
        return "/timeSlotList";
    }

    @GetMapping(value="/deleteTimeSlot/{id}")
    public String timeSlotDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        timeSlotSelectionService.deleteTimeSlotSelection(id);
        model.addAttribute("timeSlotList", timeSlotSelectionService.findAll());
        return "/timeSlotList";
    }

}

package com.ras.cms.controller;

import com.ras.cms.domain.Batch;
import com.ras.cms.domain.TimeSlotSelection;
import com.ras.cms.service.batch.BatchService;
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

@Controller
@RequestMapping("/hod")
public class BatchController {
    @Autowired
    BatchService batchService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ProgramService programService;

    @GetMapping(value="/listBatches")
    public String batchList(Model model) {
        model.addAttribute("batchList", batchService.findAll());
        return "/batchList";
    }

    @GetMapping(value={"/editBatch","/editBatch/{id}"})
    public String batchEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("batch", batchService.findOne(id));
        } else {
            model.addAttribute("batch", new Batch());
        }
        model.addAttribute("departments",departmentService.findAll());
        model.addAttribute("programs",programService.findAll());
        return "/batchEdit";
    }

    @PostMapping(value="/editBatch")
    public String batchEdit(Model model, Batch batch) {

        batchService.saveBatch(batch);
        model.addAttribute("batchList", batchService.findAll());
        return "/batchList";
    }

    @GetMapping(value="/deleteBatch/{id}")
    public String batchDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        batchService.deleteBatch(id);
        model.addAttribute("batchList", batchService.findAll());
        return "/batchList";
    }

}

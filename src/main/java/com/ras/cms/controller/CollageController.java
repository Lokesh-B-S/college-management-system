package com.ras.cms.controller;

import com.ras.cms.domain.Collage;
import com.ras.cms.service.collage.CollageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
public class CollageController {

    @Autowired
    CollageService collageService;

    @GetMapping(value="/listCollage")
    public String collageList(Model model) {
        model.addAttribute("collageList", collageService.findAll());
        return "/collageList";
    }

    @GetMapping(value={"/collageEdit","/collageEdit/{id}"})
    public String collageEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("collage", collageService.findOne(id));
        } else {
            model.addAttribute("collage", new Collage());
        }
        return "/collageEdit";
    }

    @PostMapping(value="/collageEdit")
    public String collageEdit(Model model, Collage collage) {
        collageService.saveCollage(collage);
        model.addAttribute("collageList", collageService.findAll());
        return "/collageList";
    }

    @GetMapping(value="/collageDelete/{id}")
    public String collageDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        collageService.deleteCollage(id);
        model.addAttribute("collageList", collageService.findAll());
        return "/collageList";
    }
}
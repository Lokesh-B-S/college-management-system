package com.ras.cms.controller;

import com.ras.cms.domain.User;
import com.ras.cms.service.collage.CollageService;
import com.ras.cms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Created by Surya on 12-Jun-18.
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CollageService collageService;

    @GetMapping(value="/listUser")
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "/userList";
    }

    @GetMapping(value={"/userEdit","/userEdit/{id}"})
    public String userEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("user", userService.findOne(id));
        } else {
            model.addAttribute("user", new User());
        }
        model.addAttribute("collages",collageService.findAll());
//        model.addAttribute("branches",collageService.findAll().get(0).getCourses());
        return "/userEdit";
    }

    @PostMapping(value="/userEdit")
    public String userEdit(Model model, User user) {
        userService.saveUser(user);
        model.addAttribute("userList", userService.findAll());
        return "/userList";
    }

    @GetMapping(value="/userDelete/{id}")
    public String userDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        userService.deleteUser(id);
        model.addAttribute("userList", userService.findAll());
        return "/userList";
    }
}
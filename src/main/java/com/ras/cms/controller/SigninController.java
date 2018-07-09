package com.ras.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SigninController {
    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView validateUser(String username, String password){
        return new ModelAndView("/home");
    }
}

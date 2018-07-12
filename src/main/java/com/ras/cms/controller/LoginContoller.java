package com.ras.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginContoller {
    @GetMapping(value="/login")
    public String login() {
        return "/login";
    }
}

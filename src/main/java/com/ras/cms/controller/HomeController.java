package com.ras.cms.controller;

import org.springframework.core.SpringVersion;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;
@RequestMapping("/")
public class HomeController {
    @GetMapping("/home")
    String index(Principal principal, Model model){
        model.addAttribute("springVersion", SpringVersion.getVersion());
//        return principal != null ? "/homeSignedIn" : "/homeNotSignedIn";
        return "/home";
    }
}

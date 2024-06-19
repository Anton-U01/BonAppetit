package com.bonappetit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String notLoggedIndex(){
        return "index";
    }

    @GetMapping("/home")
    public String loggedIndex(){
        return "home";
    }

}

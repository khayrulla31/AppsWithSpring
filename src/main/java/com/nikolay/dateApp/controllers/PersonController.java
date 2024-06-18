package com.nikolay.dateApp.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class PersonController {

    @GetMapping
    public String homePage(){
        return "homePage";

    }
}

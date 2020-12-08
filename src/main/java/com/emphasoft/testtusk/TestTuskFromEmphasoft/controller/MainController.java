package com.emphasoft.testtusk.TestTuskFromEmphasoft.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MainController {

    @GetMapping
    public String getIndex(){
        log.info("Opening main page...");
        return "index";
    }

    @GetMapping("/shutdown")
    public String shutdown(){
        System.exit(0);
        return "redirect:/";
    }
}

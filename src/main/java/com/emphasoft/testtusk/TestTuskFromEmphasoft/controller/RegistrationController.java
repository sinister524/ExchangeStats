package com.emphasoft.testtusk.TestTuskFromEmphasoft.controller;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Account;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
@Log4j2
public class RegistrationController {

    private final AccountService accountService;

    @GetMapping
    public String getRegForm(){
        log.info("Getting registration form...");
        return "registration";
    }

    @PostMapping
    public String regNewAccount(@RequestParam String username, @RequestParam String email, @RequestParam String password,
                                @RequestParam String firstName, @RequestParam String lastName) {
        log.info("Registration a new user...");
        accountService.createAccount(new Account(username, email, password, firstName, lastName));
        return "redirect:/login";
    }
}

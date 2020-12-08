package com.emphasoft.testtusk.TestTuskFromEmphasoft.controller;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.service.ExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/exchange")
@Log4j2
public class ExchangeController {

    private final ExchangeService exchangeService;


    @GetMapping
    public String getConcertForm (Model model){
        log.info("Open exchange form...");
        try {
            model.addAttribute("currencies", exchangeService.getCurrencies());
            return "exchange";
        } catch (JsonProcessingException e) {
            model.addAttribute("message", "Неудалось получить список валют");
            return "error";
        }
    }

    @PostMapping
    public String exchangeCurrency (Model model, Principal principal, @RequestParam String from, @RequestParam String to,
                                   @RequestParam Double value) throws JsonProcessingException {
        log.info("Exchanging currency...");
        if (principal != null){
            model.addAttribute("sum", exchangeService.exchange(from, to, value, principal.getName()));
        } else {
            model.addAttribute("sum", exchangeService.exchange(from, to, value));
        }
        return getConcertForm(model);
    }
}

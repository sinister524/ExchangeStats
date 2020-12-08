package com.emphasoft.testtusk.TestTuskFromEmphasoft.controller;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.service.AccountService;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.service.ExchangeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/stats")
@AllArgsConstructor
@Log4j2
public class StatsController {

    private final AccountService accountService;

    private final ExchangeService exchangeService;


    @GetMapping
    public String getStats(Model model, Principal principal){
        log.info("Getting exchange stats...");
        model.addAttribute("exchanges", exchangeService.getAll(accountService.findByLogin(principal.getName())));
        return "stats";
    }

    @GetMapping("/rating")
    public String getExchangeRating (Model model){
        log.info("Getting exchange rating...");
        model.addAttribute("ratings", exchangeService.getExchangeRating());
        return "rating";
    }

    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        log.info("Getting accounts...");
        model.addAttribute("accounts", accountService.allAccounts());
        return "accounts";
    }

    @GetMapping("/accounts/max-value")
    public String getAccountsWithMaxExchange(Model model, @RequestParam("maxValue") Double maxValue) {
        log.info("Getting accounts with max exchange value more that $" + maxValue + "...");
        model.addAttribute("accounts", accountService.getAccountsWithMaxExchangeMoreThat(maxValue));
        return "accounts";
    }

    @GetMapping("/accounts/total-value")
    public String getAccountsWithTotalExchange(Model model, @RequestParam("totalValue") Double totalValue) {
        log.info("Getting accounts with total exchange value more that $" + totalValue + "...");
        model.addAttribute("accounts", accountService.getAccountsWithTotalExchangeMoreThat(totalValue));
        return "accounts";
    }

    @GetMapping("/accounts/{id}")
    public String getAccount (Model model, @PathVariable Long id) {
        log.info("Getting account with ID - " + id + "...");
        model.addAttribute("account", accountService.findAccountById(id));
        return "account-open";
    }


}

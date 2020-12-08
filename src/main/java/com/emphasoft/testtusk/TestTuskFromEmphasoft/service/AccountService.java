package com.emphasoft.testtusk.TestTuskFromEmphasoft.service;


import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.Exchange;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Account;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Role;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.exception.NotUniqueValueException;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.exception.ResourceNotFoundException;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class AccountService {

    private final AccountRepository accountRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public Account findByLogin(String login) {
        log.debug("Load account by login: " + login + " from DataBase");
        return this.accountRepository.findAccountByUsername(login)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found"));
    }


    public Account findAccountById(Long accountId) {
        log.debug("Load account by ID: " + accountId + " from DataBase");
        return accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found"));
    }

    public List<Account> allAccounts() {
        log.info("Load all accounts from DataBase...");
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account) {
        log.info("Saving account...");
        return accountRepository.save(account);
    }

    public Account createAccount(Account account) {
        log.info("Creating new account with role USER...");
        try {
            log.debug("Checking Username and Email...");
            findByLogin(account.getUsername());
        } catch (ResourceNotFoundException e) {
            log.debug("Username and Email not busy");
            Role userRole = this.roleService.findByName("ROLE_USER");
            if (userRole == null)
                userRole = this.roleService.save(new Role("ROLE_USER"));
            account.setRole(userRole);
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setCreateTime(new Date());
            return saveAccount(account);
        }
        throw new NotUniqueValueException("Username or Email not unique");
    }

    public Account createAccountAdmin(Account account) {
        log.info("Creating new account with role ADMIN...");
        try {
            log.debug("Checking Username and Email...");
            findByLogin(account.getUsername());
        } catch (ResourceNotFoundException e) {
            log.debug("Username and Email not busy");
            Role userRole = this.roleService.findByName("ROLE_ADMIN");
            if (userRole == null)
                userRole = this.roleService.save(new Role("ROLE_ADMIN"));
            account.setRole(userRole);
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setCreateTime(new Date());
            return accountRepository.save(account);
        }
        throw new NotUniqueValueException("Username or Email not unique");
    }

    public List<Account> getAccountsWithMaxExchangeMoreThat(Double value) {
        log.info("Load accounts with max exchange value more that $" + value + " from DataBase");
        return allAccounts().stream()
                .filter(account -> account.getMaxExchange() >= value)
                .sorted(Comparator.comparing(Account::getMaxExchange).reversed())
                .collect(Collectors.toList());
    }

    public List<Account> getAccountsWithTotalExchangeMoreThat(Double value) {
        log.info("Load accounts with total exchange value more that $" + value + " from DataBase");
        return allAccounts().stream()
                .filter(account -> account.getTotalExchange() >= value)
                .sorted(Comparator.comparing(Account::getTotalExchange).reversed())
                .collect(Collectors.toList());
    }
}

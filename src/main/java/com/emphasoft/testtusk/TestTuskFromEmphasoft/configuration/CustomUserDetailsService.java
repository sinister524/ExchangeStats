package com.emphasoft.testtusk.TestTuskFromEmphasoft.configuration;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Account;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.service.AccountService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    public CustomUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountService.findByLogin(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(account);
    }
}

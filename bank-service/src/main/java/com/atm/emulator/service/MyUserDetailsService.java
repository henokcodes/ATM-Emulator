package com.atm.emulator.service;

import com.atm.emulator.model.Account;
import com.atm.emulator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        return  this.accountRepository.findAccountByCardNumber(cardNumber);
    }
}

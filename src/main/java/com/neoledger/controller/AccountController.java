package com.neoledger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neoledger.dto.AccountRequest;
import com.neoledger.dto.AccountResponse;
import com.neoledger.repository.UserRepository;
import com.neoledger.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public AccountResponse create(
            @RequestBody AccountRequest request) {

        return service.createAccount(request);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse get(
            @PathVariable String accountNumber) {

        return service.getAccount(accountNumber);
    }

    @GetMapping("/user/{id}")
    public List<AccountResponse> all(
            @PathVariable Long id) {

        return service.getAccounts(id);
    }

    @GetMapping("/email/{email}")
    public List<AccountResponse> getAccountsByEmail(
            @PathVariable String email) {

        Long userId =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"))
                        .getId();

        return service.getAccounts(userId);
    }
}
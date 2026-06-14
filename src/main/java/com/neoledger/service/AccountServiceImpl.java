 package com.neoledger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoledger.dto.AccountRequest;
import com.neoledger.dto.AccountResponse;
import com.neoledger.entity.Account;
import com.neoledger.entity.User;
import com.neoledger.repository.AccountRepository;
import com.neoledger.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AccountResponse createAccount(AccountRequest request) {

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String accountNumber = generateAccountNumber();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
                .balance(BigDecimal.ZERO)
                .user(user)
                .build();

        Account saved = accountRepository.save(account);

        return new AccountResponse(
                saved.getAccountNumber(),
                saved.getAccountType().name(),
                saved.getBalance()
        );
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = "ACC" +
                    UUID.randomUUID()
                            .toString()
                            .replace("-", "")
                            .substring(0, 10)
                            .toUpperCase();

        } while (accountRepository
                .findByAccountNumber(accountNumber)
                .isPresent());

        return accountNumber;
    }

    @Override
    public AccountResponse getAccount(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return new AccountResponse(
                account.getAccountNumber(),
                account.getAccountType().name(),
                account.getBalance()
        );
    }

    @Override
    public List<AccountResponse> getAccounts(Long userId) {

        return accountRepository
                .findByUserId(userId)
                .stream()
                .map(account -> new AccountResponse(
                        account.getAccountNumber(),
                        account.getAccountType().name(),
                        account.getBalance()))
                .collect(Collectors.toList());
    }
}
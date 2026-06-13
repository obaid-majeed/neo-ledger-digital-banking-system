package com.neoledger.service;

import java.util.List;

import com.neoledger.dto.AccountRequest;
import com.neoledger.dto.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccount(String accountNumber);

    List<AccountResponse> getAccounts(Long userId);

}
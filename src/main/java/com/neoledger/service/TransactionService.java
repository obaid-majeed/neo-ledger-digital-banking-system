 package com.neoledger.service;

import java.util.List;

import com.neoledger.dto.AccountResponse;
import com.neoledger.dto.DepositRequest;
import com.neoledger.dto.TransactionResponse;
import com.neoledger.dto.TransferRequest;
import com.neoledger.dto.WithdrawRequest;
import com.neoledger.dto.TransactionHistoryResponse;

public interface TransactionService {

    TransactionResponse deposit(
            DepositRequest request);

    TransactionResponse withdraw(
            WithdrawRequest request);

    TransactionResponse transfer(
            TransferRequest request);

    List<TransactionHistoryResponse> history(
            String accountNumber);
    
    

}
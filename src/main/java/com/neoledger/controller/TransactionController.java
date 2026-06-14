package com.neoledger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neoledger.dto.*;
import com.neoledger.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/deposit")
    public TransactionResponse deposit(@RequestBody DepositRequest request) {
        return service.deposit(request);
    }

    @PostMapping("/withdraw")
    public TransactionResponse withdraw(@RequestBody WithdrawRequest request) {
        return service.withdraw(request);
    }

    @PostMapping("/transfer")
    public TransactionResponse transfer(@RequestBody TransferRequest request) {
        return service.transfer(request);
    }

    @GetMapping("/history/{account}")
    public List<TransactionHistoryResponse> history(@PathVariable String account) {
        return service.history(account);
    }
}
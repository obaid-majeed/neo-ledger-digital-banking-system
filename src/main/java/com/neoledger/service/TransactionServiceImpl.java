 package com.neoledger.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoledger.dto.*;
import com.neoledger.entity.*;
import com.neoledger.repository.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }

    private void saveTransaction(String from, String to, BigDecimal amount, TransactionType type) {

        Transaction tx = Transaction.builder()
                .fromAccount(from)
                .toAccount(to)
                .amount(amount)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(tx);
    }

    @Transactional
    @Override
    public TransactionResponse deposit(DepositRequest request) {

        validateAmount(request.getAmount());

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        saveTransaction(
                null,
                account.getAccountNumber(),
                request.getAmount(),
                TransactionType.DEPOSIT
        );

        return new TransactionResponse(
                "Deposit Successful",
                account.getAccountNumber(),
                request.getAmount()
        );
    }

    @Transactional
    @Override
    public TransactionResponse withdraw(WithdrawRequest request) {

        validateAmount(request.getAmount());

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() == null ||
                account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        saveTransaction(
                account.getAccountNumber(),
                null,
                request.getAmount(),
                TransactionType.WITHDRAW
        );

        return new TransactionResponse(
                "Withdrawal Successful",
                account.getAccountNumber(),
                request.getAmount()
        );
    }

    @Transactional
    @Override
    public TransactionResponse transfer(TransferRequest request) {

        validateAmount(request.getAmount());

        Account from = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account to = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (from.getBalance() == null ||
                from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        accountRepository.save(from);
        accountRepository.save(to);

        saveTransaction(
                from.getAccountNumber(),
                to.getAccountNumber(),
                request.getAmount(),
                TransactionType.TRANSFER
        );

        return new TransactionResponse(
                "Transfer Successful",
                from.getAccountNumber(),
                request.getAmount()
        );
    }

    @Override
    public List<TransactionHistoryResponse> history(String accountNumber) {

        return transactionRepository
                .findByFromAccountOrToAccount(accountNumber, accountNumber)
                .stream()
                .map(tx -> new TransactionHistoryResponse(
                        tx.getFromAccount(),
                        tx.getToAccount(),
                        tx.getAmount(),
                        tx.getType(),
                        tx.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
} 
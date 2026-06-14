package com.neoledger.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoledger.dto.DepositRequest;
import com.neoledger.dto.TransferRequest;
import com.neoledger.dto.TransactionHistoryResponse;
import com.neoledger.dto.TransactionResponse;
import com.neoledger.dto.WithdrawRequest;
import com.neoledger.entity.Account;
import com.neoledger.entity.Transaction;
import com.neoledger.entity.TransactionType;
import com.neoledger.repository.AccountRepository;
import com.neoledger.repository.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private void validateAmount(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }

    private void saveTransaction(
            String from,
            String to,
            BigDecimal amount,
            TransactionType type) {

        Transaction transaction = Transaction.builder()
                .fromAccount(from)
                .toAccount(to)
                .amount(amount)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
    }

    @Override
    public TransactionResponse deposit(
            DepositRequest request) {

        validateAmount(request.getAmount());

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        account.setBalance(
                account.getBalance().add(request.getAmount()));

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

    @Override
    public TransactionResponse withdraw(
            WithdrawRequest request) {

        validateAmount(request.getAmount());

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getBalance() == null ||
                account.getBalance().compareTo(request.getAmount()) < 0) {

            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(
                account.getBalance().subtract(request.getAmount()));

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

    @Override
    public TransactionResponse transfer(
            TransferRequest request) {

        validateAmount(request.getAmount());

        Account fromAccount = accountRepository
                .findByAccountNumber(request.getFromAccount())
                .orElseThrow(() ->
                        new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository
                .findByAccountNumber(request.getToAccount())
                .orElseThrow(() ->
                        new RuntimeException("Receiver account not found"));

        if (fromAccount.getBalance() == null ||
                fromAccount.getBalance().compareTo(request.getAmount()) < 0) {

            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(request.getAmount()));

        toAccount.setBalance(
                toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        saveTransaction(
                fromAccount.getAccountNumber(),
                toAccount.getAccountNumber(),
                request.getAmount(),
                TransactionType.TRANSFER
        );

        return new TransactionResponse(
                "Transfer Successful",
                fromAccount.getAccountNumber(),
                request.getAmount()
        );
    }

    @Override
    public List<TransactionHistoryResponse> history(
            String accountNumber) {

        return transactionRepository
                .findByFromAccountOrToAccount(
                        accountNumber,
                        accountNumber)
                .stream()
                .map(transaction ->
                        new TransactionHistoryResponse(
                                transaction.getFromAccount(),
                                transaction.getToAccount(),
                                transaction.getAmount(),
                                transaction.getType(),
                                transaction.getCreatedAt()
                        ))
                .collect(Collectors.toList());
    }
}
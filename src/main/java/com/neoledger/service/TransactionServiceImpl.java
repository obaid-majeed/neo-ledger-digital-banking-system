 package com.neoledger.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoledger.dto.DepositRequest;
import com.neoledger.dto.TransferRequest;
import com.neoledger.dto.WithdrawRequest;

import com.neoledger.entity.Account;
import com.neoledger.entity.Transaction;
import com.neoledger.entity.TransactionType;

import com.neoledger.repository.AccountRepository;
import com.neoledger.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;

import com.neoledger.dto.TransactionResponse;
import java.util.stream.Collectors;
import com.neoledger.dto.TransactionHistoryResponse;
@Service
public class TransactionServiceImpl
implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private void saveTransaction(

            String accountNumber,

            BigDecimal amount,

            TransactionType type) {

        Transaction tx =
                Transaction.builder()

                .accountNumber(
                        accountNumber)

                .amount(
                        amount)

                .type(
                        type)

                .createdAt(
                        LocalDateTime.now())

                .build();

        transactionRepository
                .save(tx);
    }

    @Override
    public TransactionResponse deposit(
            DepositRequest request) {
    	if (request.getAmount().signum() <= 0) {
    	    throw new RuntimeException(
    	            "Amount must be greater than zero");
    	}

        Account account =
                accountRepository
                .findByAccountNumber(
                        request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Account not found"));

        account.setBalance(

                account.getBalance()
                .add(
                        request.getAmount())

        );

        Account saved =
                accountRepository
                .save(account);

        saveTransaction(

                account.getAccountNumber(),

                request.getAmount(),

                TransactionType.DEPOSIT

        );

        return new TransactionResponse(
                "Deposit Successful",
                saved.getAccountNumber(),
                request.getAmount()
        );
    }

    @Override
    public TransactionResponse withdraw(
            WithdrawRequest request) {
    	if (request.getAmount().signum() <= 0) {
    	    throw new RuntimeException(
    	            "Amount must be greater than zero");
    	}

        Account account =
                accountRepository
                .findByAccountNumber(
                        request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Account not found"));

        if (account.getBalance()
                .compareTo(
                        request.getAmount()) < 0) {

            throw new RuntimeException(
                    "Insufficient balance");
        }

        account.setBalance(

                account.getBalance()
                .subtract(
                        request.getAmount())

        );

        Account saved =
                accountRepository
                .save(account);

        saveTransaction(

                account.getAccountNumber(),

                request.getAmount(),

                TransactionType.WITHDRAW

        );

        return new TransactionResponse(
                "Withdrawal Successful",
                saved.getAccountNumber(),
                request.getAmount()
        );
    }
    @Transactional
    @Override
    public  TransactionResponse transfer(
            TransferRequest request) {
    	if (request.getAmount().signum() <= 0) {
    	    throw new RuntimeException(
    	            "Amount must be greater than zero");
    	}

        Account from =
                accountRepository
                .findByAccountNumber(
                        request.getFromAccount())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sender account not found"));

        Account to =
                accountRepository
                .findByAccountNumber(
                        request.getToAccount())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Receiver account not found"));

        if (from.getBalance()
                .compareTo(
                        request.getAmount()) < 0) {

            throw new RuntimeException(
                    "Insufficient balance");
        }

        from.setBalance(

                from.getBalance()
                .subtract(
                        request.getAmount())

        );

        to.setBalance(

                to.getBalance()
                .add(
                        request.getAmount())

        );

        accountRepository
                .save(from);

        accountRepository
                .save(to);

        saveTransaction(

                from.getAccountNumber(),

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
    public List<TransactionHistoryResponse> history(
            String accountNumber) {

        return transactionRepository
                .findByAccountNumber(accountNumber)
                .stream()
                .map(tx -> new TransactionHistoryResponse(
                        tx.getAccountNumber(),
                        tx.getAmount(),
                        tx.getType(),
                        tx.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

} 
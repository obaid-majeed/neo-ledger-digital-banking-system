 package com.neoledger.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.neoledger.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionHistoryResponse {

    private String accountNumber;

    private BigDecimal amount;

    private TransactionType type;

    private LocalDateTime createdAt;
}
package com.neoledger.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.neoledger.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryResponse {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime createdAt;
}
 package com.neoledger.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {

    private String message;

    private String accountNumber;

    private BigDecimal amount;
}
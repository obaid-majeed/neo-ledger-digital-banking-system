package com.neoledger.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {

    private String accountNumber;

    private BigDecimal amount;
}
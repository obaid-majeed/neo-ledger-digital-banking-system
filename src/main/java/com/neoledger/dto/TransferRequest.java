 package com.neoledger.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;
}
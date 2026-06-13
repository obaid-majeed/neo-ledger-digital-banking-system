 package com.neoledger.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountResponse {

 
    private String accountNumber;

    private String accountType;

    private BigDecimal balance;
}
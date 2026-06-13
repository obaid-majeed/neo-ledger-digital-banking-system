package com.neoledger.dto;

import com.neoledger.entity.AccountType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private Long userId;

    private AccountType accountType;
}
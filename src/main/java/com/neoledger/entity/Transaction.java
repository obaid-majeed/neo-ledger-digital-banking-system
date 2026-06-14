 package com.neoledger.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccount;
    private String toAccount;
    private String accountNumber;


    private BigDecimal amount;

    //private String type; // DEPOSIT / WITHDRAW / TRANSFER


    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private LocalDateTime createdAt;
}
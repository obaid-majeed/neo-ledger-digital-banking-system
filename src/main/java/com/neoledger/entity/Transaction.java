  package com.neoledger.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name="transactions")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Transaction {

    @Id
    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY)

    private Long id;

    private String accountNumber;

    private BigDecimal amount;

    @Enumerated(
            EnumType.STRING)

    private TransactionType type;

    private LocalDateTime createdAt;

}
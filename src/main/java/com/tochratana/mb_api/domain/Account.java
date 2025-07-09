package com.tochratana.mb_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String actType;

    @Column(nullable = false)
    private String actCurrency;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean isDeleted;


    /**
     * Multiple account can have only one customer
     */
    @ManyToOne
    @JoinColumn(name = "cust_id") // change name column that has a relationship
    private Customer customer; // by default customer_id


    @ManyToOne
    @JoinColumn(name = "account_type_id") // FK to AccountType
    private AccountType accountType;
}

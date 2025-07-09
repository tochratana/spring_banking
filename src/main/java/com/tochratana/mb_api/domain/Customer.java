package com.tochratana.mb_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

// create this pojo as an entity and create table
@Entity
@Table(name = "customers")
public class Customer {

    @Id // use to add primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for auto ins
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(length = 15,nullable = false)
    private String gender;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private  String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;

    /**
     * One customer can have a multiple account
     */
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    /**
     * One customer can have only one kyc
     */
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KYC kyc;

}

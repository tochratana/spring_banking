package com.tochratana.mb_api.domain;

// Know your customer

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class KYC {
    @Id
    private Integer id;
    private String nationalCardId;
    private Boolean isVerified;
    private Boolean isDeleted;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cust_id")
    private Customer customer;

}

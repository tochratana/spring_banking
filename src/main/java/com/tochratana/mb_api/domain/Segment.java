package com.tochratana.mb_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

// create this pojo as an entity and create table
@Entity
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String segment;

    @Column(nullable = false)
    private BigDecimal overLimit;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "segment")
    private List<Customer> customers;
}

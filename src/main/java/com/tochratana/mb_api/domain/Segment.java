package com.tochratana.mb_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

// create this pojo as an entity and create table
@Entity
@Table(name = "segments")
public class Segment {
    @Id
    private Integer id;

    private String segment;
    private Boolean isDeleted;
}

package com.kathford.wms.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class IdEntity {

    @Id
    @GeneratedValue
    private Long id;
}

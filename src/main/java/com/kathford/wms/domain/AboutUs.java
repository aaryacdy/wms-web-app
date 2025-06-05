package com.kathford.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name = "tbl_about_us")
@Getter
@Setter
@SelectBeforeUpdate
@DynamicUpdate
public class AboutUs {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 5000, nullable = false)
    private String message;

}

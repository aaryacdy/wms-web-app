package com.kathford.wms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_category")
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseAdminModel {

    public Category(Long id) {
        super.setId(id);
    }

    @Column(name = "name")
    private String name;

    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;

    @Column(columnDefinition = "boolean default true")
    private boolean status;
}

package com.kathford.wms.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class CategoryResponse {

    private BigInteger id;

    private String name;

    private BigDecimal price;

    private String remarks;

    private String description;

    private String icon;

    private boolean status;

    // Automatically strip HTML tags in the getter
    public String getDescription() {
        return description != null ? description.replaceAll("<.*?>", "") : "";
    }

}

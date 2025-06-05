package com.kathford.wms.domain;

import com.kathford.wms.config.constant.DefaultValue;
import com.kathford.wms.util.Strings;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_waste")
@Getter
@Setter
@NoArgsConstructor
public class Waste extends BaseModel {

    private String name;

    private String weight;

    private BigDecimal price;

    private String address;

    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(columnDefinition = DefaultValue.PENDING)
    private String status; //only updated by the driver or collector


    private String description;

    private String image;

    @OneToOne
    @NotNull
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by", insertable = false)
    private Admin approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = Strings.DATE_TIME_PATTERN)
    @Column(name = "approved_date")
    private Date approvedDate;

}

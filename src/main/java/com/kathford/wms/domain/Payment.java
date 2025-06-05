package com.kathford.wms.domain;

import javax.persistence.*;

import com.kathford.wms.util.Strings;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.IntegerType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_payment")
@Getter
@Setter
public class Payment extends BaseAdminModel {

    @OneToOne
    @JoinColumn(name = "waste_id")
    private Waste waste;

    private Integer year;

    @Column(name = "payment_method")
    private String paymentMethod;

    private Integer month;

    private BigDecimal amount;

    @Column(columnDefinition = "varchar default 'Pending'")
    private String status;

//    @OneToOne
//    @JoinColumn(name = "approved_rejected_by", insertable = false)
//    private Admin approvedOrRejectedBy;

    @OneToOne
    @JoinColumn(name = "approved_rejected_by", insertable = false)
    private User approvedOrRejectedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = Strings.DATE_TIME_PATTERN)
    @Column(name = "approved_rejected_date")
    private Date approveOrRejectedDate;

}

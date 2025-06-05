package com.kathford.wms.domain;

import com.kathford.wms.util.Strings;
import lombok.Getter;
import javax.persistence.*;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseAdminModel {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Admin createdBy;

    @OneToOne
    @JoinColumn(name = "modified_by", insertable = false)
    private Admin modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = Strings.DATE_TIME_PATTERN)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = Strings.DATE_TIME_PATTERN)
    @Column(name = "modified_date")
    private Date modifiedDate;

}

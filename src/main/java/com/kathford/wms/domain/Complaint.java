package com.kathford.wms.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_complaint")
@Getter
@Setter
@NoArgsConstructor
public class Complaint extends BaseModel {

    private String heading;

//    @Column(columnDefinition = "longtext")
    private String description;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'Pending'")
    private String status;

}
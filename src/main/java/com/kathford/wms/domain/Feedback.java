package com.kathford.wms.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "tbl_feedback")
@Getter
@Setter
@NoArgsConstructor
public class Feedback extends BaseModel {

    private String description;
}
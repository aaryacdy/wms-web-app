package com.kathford.wms.domain;

import com.kathford.wms.config.constant.DefaultValue;
import com.kathford.wms.util.Strings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    public User(Long id) {
        this.setId(id);
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    private String address;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "profile_pic")
    private String profilePicture;

    private String role;

    @Column(columnDefinition = DefaultValue.BOOLEAN_INACTIVE)
    private boolean blocked;

    @Column(columnDefinition = DefaultValue.BOOLEAN_ACTIVE)
    private boolean status;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = Strings.DATE_TIME_PATTERN)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = Strings.DATE_TIME_PATTERN)
    @Column(name = "modified_date", insertable = false)
    private Date modifiedDate;

//    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "user")
//    private Token token;
}

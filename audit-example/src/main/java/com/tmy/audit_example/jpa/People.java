package com.tmy.audit_example.jpa;


import com.tmy.audit.annotation.Audit;
import com.tmy.audit.annotation.Ignore;
import com.tmy.audit.annotation.OnEmpty;
import com.tmy.audit.annotation.OnEmptyIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "people")
@Data
@Audit
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @OnEmptyIgnore
    private String lastName;

    @Column(name = "office")
    @Ignore
    private String office;

    @Column(name = "user_name")
    @OnEmpty("None")
    private String userName;

}

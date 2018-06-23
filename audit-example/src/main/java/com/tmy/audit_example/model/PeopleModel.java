package com.tmy.audit_example.model;

import lombok.Data;

import java.time.Instant;


@Data
public class PeopleModel {
    private String userName;
    private String office;
    private String firstName;
    private String lastName;

}

package com.tmy.audit_example.mapper;


import com.tmy.audit_example.jpa.People;
import com.tmy.audit_example.model.PeopleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleMapper {


    People from(PeopleModel peopleModel);

    PeopleModel from(People people);
}

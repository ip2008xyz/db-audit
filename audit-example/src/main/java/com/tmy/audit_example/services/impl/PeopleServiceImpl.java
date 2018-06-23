package com.tmy.audit_example.services.impl;


import com.tmy.audit_example.jpa.People;
import com.tmy.audit_example.mapper.PeopleMapper;
import com.tmy.audit_example.model.PeopleModel;
import com.tmy.audit_example.repository.PeopleRepository;
import com.tmy.audit_example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private PeopleRepository peopleRepository;

    public PeopleModel save(PeopleModel peopleModel) {


        People fromDb = peopleRepository.findByUserName(peopleModel.getUserName());

        People people = peopleMapper.from(peopleModel);

        if (nonNull(fromDb)) {
            people.setId(fromDb.getId());
        }
        System.out.println(people);
        people = peopleRepository.save(people);

        return peopleMapper.from(people);
    }
}

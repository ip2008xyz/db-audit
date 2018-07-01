package com.tmy.audit_example.services.impl;


import com.tmy.audit_example.jpa.People;
import com.tmy.audit_example.mapper.PeopleMapper;
import com.tmy.audit_example.model.PeopleModel;
import com.tmy.audit_example.repository.PeopleRepository;
import com.tmy.audit_example.services.PeopleService;
import static java.util.Objects.nonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl implements PeopleService {

    private static final Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);

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
        logger.info("People: {}", people);
        
        people = peopleRepository.save(people);

        return peopleMapper.from(people);
    }
}

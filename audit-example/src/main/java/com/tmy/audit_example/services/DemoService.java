package com.tmy.audit_example.services;


import com.tmy.audit_example.model.PeopleModel;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//TODO put this in the tests
@Component
public class DemoService {

    @Autowired
    private PeopleService peopleService;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setLastName(RandomStringUtils.randomAlphabetic(8));
        peopleModel.setFirstName(RandomStringUtils.randomAlphabetic(6));
        peopleModel.setOffice(RandomStringUtils.randomAlphabetic(3));
        peopleModel.setUserName(RandomStringUtils.randomAlphabetic(1));

        if (peopleModel.getLastName().contains("a")) {
            peopleModel.setLastName(null);
        }

        if (peopleModel.getUserName().contains("a")) {
            peopleModel.setUserName(null);
        }

        peopleService.save(peopleModel);
    }
}

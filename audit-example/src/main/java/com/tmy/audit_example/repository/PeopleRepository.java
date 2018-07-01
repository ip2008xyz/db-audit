package com.tmy.audit_example.repository;

import com.tmy.audit_example.jpa.People;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PeopleRepository extends CrudRepository<People, Long> {

    People findByUserName(String username);
}

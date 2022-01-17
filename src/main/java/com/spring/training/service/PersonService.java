package com.spring.training.service;

import io.spring.guides.gs_producing_web_service.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    public Person findPerson(String name) {
        Person person = new Person();
        person.setFirstName("Mamadou Lamine");
        person.setLastName("Ba");
        return person;
    }

}

package com.spring.training.mapping;

import com.spring.training.entity.PersonEntity;
import io.spring.guides.gs_producing_web_service.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    Person toPerson(PersonEntity PersonEntity);

    PersonEntity fromPerson(Person Person);

}

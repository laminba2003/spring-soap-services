package com.spring.training.mapping;

import com.spring.training.entity.CountryEntity;
import io.spring.guides.gs_producing_web_service.Country;
import org.mapstruct.Mapper;

@Mapper
public interface CountryMapper {

    Country toCountry(CountryEntity countryEntity);

    CountryEntity fromCountry(Country country);

}

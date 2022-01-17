package com.spring.training.service;

import com.spring.training.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import io.spring.guides.gs_producing_web_service.Country;

@Service
@AllArgsConstructor
public class CountryService {

    private CountryRepository repository;

    public Country findCountry(String name) {
        return repository.findByNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("country not found")).toCountry();
    }
}
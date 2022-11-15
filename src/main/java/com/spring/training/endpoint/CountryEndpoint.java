package com.spring.training.endpoint;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.service.CountryService;
import com.spring.training.model.*;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.spring.training.config.ApplicationConfig.NAMESPACE_URI;

@Endpoint
@AllArgsConstructor
public class CountryEndpoint {

    final CountryService service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountriesRequest")
    @ResponsePayload
    public GetCountriesResponse getCountry(@RequestPayload GetCountriesRequest request) {
        GetCountriesResponse response = new GetCountriesResponse();
        response.getCountries().addAll(service.getCountries());
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(service.getCountry(request.getName()));
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "createCountryRequest")
    @ResponsePayload
    public CreateCountryResponse createCountry(@RequestPayload CreateCountryRequest request) {
        CreateCountryResponse response = new CreateCountryResponse();
        response.setCountry(service.createCountry(request.getCountry()));
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "updateCountryRequest")
    @ResponsePayload
    public UpdateCountryResponse updateCountry(@RequestPayload UpdateCountryRequest request) {
        UpdateCountryResponse response = new UpdateCountryResponse();
        response.setCountry(service.updateCountry(request.getCountry()));
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "deleteCountryRequest")
    @ResponsePayload
    public DeleteCountryResponse deleteCountry(@RequestPayload DeleteCountryRequest request) {
        DeleteCountryResponse response = new DeleteCountryResponse();
        service.deleteCountry(request.getName());
        return response;
    }


}
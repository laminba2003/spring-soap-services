package com.spring.training.endpoint;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.service.PersonService;
import io.spring.guides.gs_producing_web_service.*;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@AllArgsConstructor
public class PersonEndpoint {

    PersonService service;

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "getPersonsRequest")
    @ResponsePayload
    public GetPersonsResponse getPersons(@RequestPayload GetPersonsRequest request) {
        GetPersonsResponse response = new GetPersonsResponse();
        response.getPersons().addAll(service.getPersons());
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPerson(@RequestPayload GetPersonRequest request) {
        GetPersonResponse response = new GetPersonResponse();
        response.setPerson(service.getPerson(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "createPersonRequest")
    @ResponsePayload
    public CreatePersonResponse createPerson(@RequestPayload CreatePersonRequest request) {
        CreatePersonResponse response = new CreatePersonResponse();
        response.setPerson(service.createPerson(request.getPerson()));
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "updatePersonRequest")
    @ResponsePayload
    public UpdatePersonResponse updatePerson(@RequestPayload UpdatePersonRequest request) {
        UpdatePersonResponse response = new UpdatePersonResponse();
        response.setPerson(service.updatePerson(request.getPerson()));
        return response;
    }

    @PayloadRoot(namespace = ApplicationConfig.NAMESPACE_URI, localPart = "deletePersonRequest")
    @ResponsePayload
    public DeletePersonResponse deletePerson(@RequestPayload DeletePersonRequest request) {
        DeletePersonResponse response = new DeletePersonResponse();
        service.deletePerson(request.getId());
        return response;
    }

}
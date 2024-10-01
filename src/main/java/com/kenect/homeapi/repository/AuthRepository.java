package com.kenect.homeapi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.homeapi.dto.Contact;
import com.kenect.homeapi.dto.Contacts;
import com.kenect.homeapi.exception.KenectApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestClient;
import java.util.*;

public abstract class AuthRepository {

    Logger logger = Logger.getLogger(AuthRepository.class);

    @Value("${spring.baseUrl}")
    private String baseUrl;

    @Value("${spring.bearerToken}")
    private String bearerToken;

    public List<Contacts> getContactsByExternalConnection(){
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<Contacts> listOfContacts = new ArrayList<>();
        RestClient restClient = RestClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader("Authorization", "Bearer " + bearerToken)
                    .build();
        try {
            restClient.get().exchange((request, response) -> {
                if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                    logger.debug("Getting the contacts succesfully");
                    Integer numberOfPages = Integer.parseInt(response.getHeaders().get("Total-Pages").get(0));
                    listOfContacts.add(objectMapper.readValue(response.getBody(), new TypeReference<>() {
                    }));
                    for (int iterator = 1; iterator < numberOfPages; iterator++) {

                        listOfContacts.addAll(callKenectApiPagination(numberOfPages));
                    }

                } else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    logger.error("Error trying to retrieve the contacts through Kenect API");
                    throw new KenectApiException("The connection through Kenect API has an error "
                            + response.getStatusCode() + "/" + response.getStatusText());
                } else {
                    logger.error("Error trying to retrieve the contacts through Kenect API Uknown issue");
                    throw new KenectApiException("The connection through Kenect API has an uknown issue "
                            + response.getStatusCode() + "/" + response.getStatusText());
                }
                return null;
            });
        }catch (HttpMessageNotReadableException error){
                logger.error("Error trying to get the contacts due to Malformed Http response");
                throw new KenectApiException("Error trying to get the contacts due to Malformed Http response "
                        + error);

        }catch (Exception error){
            logger.error("Error trying to get the contacts due to a general error");
            throw new KenectApiException("Error trying to get the contacts due to a general error "
                    + error);
        }

        return listOfContacts;
    }

    public Contact getContactsByIdExternalConnection(Integer id){
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Contact contact = new Contact();
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl + "/" + id)
                .defaultHeader("Authorization", "Bearer " + bearerToken)
                .build();
        try {
            contact = restClient.get().exchange((request, response) -> {
                if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                    logger.debug("Getting the contact by Id succesfully");
                    return objectMapper.readValue(response.getBody(), new TypeReference<>() {
                    });

                } else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    logger.error("Error trying to retrieve the contacts through Kenect API");
                    throw new KenectApiException("The connection through Kenect API has an error "
                            + response.getStatusCode() + "/" + response.getStatusText());
                } else {
                    logger.error("Error trying to retrieve the contacts through Kenect API Uknown issue");
                    throw new KenectApiException("The connection through Kenect API has an uknown issue "
                            + response.getStatusCode() + "/" + response.getStatusText());
                }
            });

        }catch (HttpMessageNotReadableException error){
                logger.error("Error trying to get the contacts due to Malformed Http response");
                throw new KenectApiException("Error trying to get the contacts due to Malformed Http response "
                        + error);
        }catch (Exception error){
            logger.error("Error trying to get the contact by Id due to a general error");
            throw new KenectApiException("Error trying to get the contact by Id due to a general error "
                    + error);
        }

        return contact;
    }

    public List<Contacts> callKenectApiPagination(Integer page){
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<Contacts> listOfContacts = new ArrayList<>();
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl + "/?page=" + page)
                .defaultHeader("Authorization", "Bearer " + bearerToken)
                .build();
        try {
            listOfContacts = restClient.get().exchange((request, response) -> {
                if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                    logger.debug("Getting the contacts succesfully");
                    return objectMapper.readValue(response.getBody(), new TypeReference<>() {
                    });

                } else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    logger.error("Error trying to retrieve the contacts through Kenect API");
                    throw new KenectApiException("The connection through Kenect API has an error "
                            + response.getStatusCode() + "/" + response.getStatusText());
                } else {
                    logger.error("Error trying to retrieve the contacts through Kenect API Uknown issue");
                    throw new KenectApiException("The connection through Kenect API has an uknown issue "
                            + response.getStatusCode() + "/" + response.getStatusText());
                }
            });
        }catch (HttpMessageNotReadableException error){
                logger.error("Error trying to get the contacts due to Malformed Http response");
                throw new KenectApiException("Error trying to get the contacts due to Malformed Http response "
                        + error);
        }catch (Exception error){
                logger.error("Error trying to get the contacts by page due to a general error");
                throw new KenectApiException("Error trying to get the contact by page due to a general error "
                        + error);
        }

        return listOfContacts;
    }
}

package com.kenect.homeapi.controller;

import com.kenect.homeapi.dto.Contact;
import com.kenect.homeapi.dto.Contacts;
import com.kenect.homeapi.exception.KenectApiException;
import com.kenect.homeapi.service.impl.ContactServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

    @Autowired
    ContactServiceImpl contactService;

    @Operation(summary = "Get a list of contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list of contacts",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content),
    @ApiResponse(responseCode = "404", description = "Contacts not found",
            content = @Content) })
    @GetMapping
    public ResponseEntity<Page<Contacts>> getContact(@SortDefault(sort = "name") @PageableDefault(size = 20) final Pageable pageable){
        try {
            Logger logger = Logger.getLogger(ContactController.class);
            logger.info("Getting the values from contact");
            List<Contacts> contacts = contactService.getAllContacts();
            Page<Contacts> pages = new PageImpl<>(contacts, pageable, contacts.size());
            return ResponseEntity.ok().body(pages);
        }catch (KenectApiException kenectApiException){
            throw new KenectApiException(kenectApiException.getMessage());
        }
    }

    @Operation(summary = "Get a list of contact by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the contact by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@SortDefault(sort = "name") @PageableDefault(size = 20) final Pageable pageable, @Parameter(description = "id of contact to be searched") @PathVariable Integer id){
        try {
            Logger logger = Logger.getLogger(ContactController.class);
            logger.info("Getting the values from contact by Id");
            Contact contacts = contactService.getContactById(id);
            return ResponseEntity.ok().body(contacts);
        }catch (KenectApiException kenectApiException){
            throw new KenectApiException(kenectApiException.getMessage());
        }
    }
}

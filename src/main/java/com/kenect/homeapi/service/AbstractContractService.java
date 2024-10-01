package com.kenect.homeapi.service;

import com.kenect.homeapi.dto.Contact;
import com.kenect.homeapi.dto.Contacts;
import com.kenect.homeapi.repository.AbstractContactRepository;

import java.util.List;

public abstract class AbstractContractService extends AbstractContactRepository {

    public List<Contacts> getAllContactsByService(){
            return getAllContactsByRepository();
    }

    public Contact getContactsById(Integer id){
        return getContactsByIdExternalConnection(id);
    }

    public abstract List<Contacts> getAllContacts();

    public abstract Contact getContactById(Integer id);
}

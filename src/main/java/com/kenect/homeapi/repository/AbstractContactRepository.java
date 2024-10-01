package com.kenect.homeapi.repository;

import com.kenect.homeapi.dto.Contact;
import com.kenect.homeapi.dto.Contacts;

import java.util.List;

public abstract class AbstractContactRepository extends AuthRepository{

    public List<Contacts> getAllContactsByRepository() {
        return getContactsByExternalConnection();
    }

    public Contact getContactsByIdRepository(Integer id) {
        return getContactsByIdExternalConnection(id);
    }
}
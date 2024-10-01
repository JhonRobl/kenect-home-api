package com.kenect.homeapi.service.impl;

import com.kenect.homeapi.dto.Contact;
import com.kenect.homeapi.dto.Contacts;
import com.kenect.homeapi.service.AbstractContractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl extends AbstractContractService {
    @Override
    public List<Contacts> getAllContacts() {
        return getAllContactsByService();
    }
    @Override
    public Contact getContactById(Integer id) {
        return getContactsById(id);
    }
}

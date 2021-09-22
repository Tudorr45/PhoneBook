package com.nagarro.service;
import com.nagarro.repo.IContactRepo;

import javax.inject.Inject;

public class ContactService {

    @Inject
    IContactRepo contactRepo;

    public ContactService(IContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }


}

package com.nagarro.service;
import com.nagarro.model.Contact;
import com.nagarro.repo.IContactRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ContactService{

    @Inject
    IContactRepo contactRepo;

    public ContactService(IContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public List<Contact> getContacts() {
        return contactRepo.getContacts();
    }

    public Long getContactsSize() {
        return contactRepo.getContactsSize();
    }

    public Optional<Contact> createContact(Contact contact) {
        return contactRepo.createContact(contact);
    }

    public Contact updateContact(Long id, String firstName, String lastName, String phone) {
        return contactRepo.updateContact(id, firstName, lastName, phone);
    }

    public boolean deleteContact(String id) {
        return contactRepo.deleteContact(id);
    }
}

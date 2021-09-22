package com.nagarro.repo;

import com.nagarro.model.Contact;

import java.util.List;

public interface IContactRepo {
    List<Contact> getContacts();
    Integer getContactsSize();
    Contact createContact(Contact contact);
    Contact updateContact(Long id, String firstName, String lastName, String phone);
    void deleteContact(Long id);
}

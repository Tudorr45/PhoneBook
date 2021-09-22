package com.nagarro.repo;

import com.nagarro.model.Contact;

import java.util.List;
import java.util.Optional;

public interface IContactRepo {
    List<Contact> getContacts();
    Long getContactsSize();
    Optional<Contact> createContact(Contact contact);
    Contact updateContact(Long id, String firstName, String lastName, String phone);
    boolean deleteContact(String id);
}

package com.nagarro.repo;

import com.nagarro.model.Contact;

import java.util.List;
import java.util.Optional;

public interface IContactRepo {
    List<Contact> getContacts();

    Long getContactsSize();

    Optional<Contact> createContact(Contact contact);

    Contact updateContact(String firstName, Optional<String> newFirstName, Optional<String> lastName, Optional<String> phone);

    boolean deleteContact(String id);
}

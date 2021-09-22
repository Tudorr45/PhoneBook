package com.nagarro.repo;

import com.arangodb.ArangoDB;
import com.arangodb.mapping.ArangoJack;
import com.nagarro.model.Contact;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ContactRepo implements IContactRepo{

    private final static String DB_NAME = "PhoneBook";
    private final static String COLLECTION_NAME = "Contacts";

    ArangoDB arangoDB = new ArangoDB.Builder()
            .serializer(new ArangoJack())
            .build();

    @Override
    public List<Contact> getContacts() {
        return null;
    }

    @Override
    public Integer getContactsSize() {
        return null;
    }

    @Override
    public Contact createContact(Contact contact) {
        return null;
    }

    @Override
    public Contact updateContact(Long id, String firstName, String lastName, String phone) {
        return null;
    }

    @Override
    public void deleteContact(Long id) {

    }
}

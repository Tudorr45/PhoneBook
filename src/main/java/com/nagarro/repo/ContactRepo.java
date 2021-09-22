package com.nagarro.repo;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.mapping.ArangoJack;
import com.nagarro.model.Contact;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ContactRepo implements IContactRepo{

    private final static String DB_NAME = "phonebookDB";
    private final static String COLLECTION_NAME = "firstCollection";

    ArangoDB arangoDB = new ArangoDB.Builder()
            .serializer(new ArangoJack())
            .user("root")
            .password("tudor")
            .build();

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
//        MultiDocumentEntity<String> myDocuments = arangoDB.db(DB_NAME).collection(COLLECTION_NAME).getDocuments()
//        if (myDocuments != null) {
//            System.out.println("Key: " + myDocuments.getKey());
//            System.out.println("Attribute a: " + myDocuments.getAttribute("a"));
//            System.out.println("Attribute b: " + myDocuments.getAttribute("b"));
//        } else {
//            System.err.println("Failed to get document: myKey");
//        }
        return contacts;
    }

    @Override
    public Integer getContactsSize() {
        return null;
    }

    @Override
    public Optional<Contact> createContact(Contact contact) {
        BaseDocument myContact = new BaseDocument();
        //myContact.setKey(contact.getId().toString());
        myContact.addAttribute("id", contact.getId());
        myContact.addAttribute("firstName", contact.getFirstName());
        myContact.addAttribute("lastName", contact.getLastName());
        myContact.addAttribute("phone", contact.getPhone());
        myContact.addAttribute("creationDate", contact.getCreationDate().toString());
        try {
            arangoDB.db(DB_NAME).collection(COLLECTION_NAME).insertDocument(myContact);
            System.out.println("Document created");
            return Optional.of(contact);
        } catch(ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Contact updateContact(Long id, String firstName, String lastName, String phone) {
        return null;
    }

    @Override
    public void deleteContact(Long id) {

    }
}

package com.nagarro.repo;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.mapping.ArangoJack;
import com.nagarro.model.Contact;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.resources.IgniteInstanceResource;

import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ContactRepo implements IContactRepo {

    private final static String DB_NAME = "phonebookDB";
    private final static String COLLECTION_NAME = "firstCollection";
    @IgniteInstanceResource
    private static Ignite ignite;
    //private final static IgniteCache<String, Contact> cache = ignite.cache("contactCache");

    ArangoDB arangoDB = new ArangoDB.Builder()
            .serializer(new ArangoJack())
            .user("root")
            .password("tudor")
            .build();

//    @Override
//    public List<Contact> getContacts() {
//
//        List<Contact> contacts = new ArrayList<>();
////        String getAllQuery = "FOR c IN " + COLLECTION_NAME + " RETURN c";
//        try {
//            //trying to retrieve all documents from the cache
//            for (Cache.Entry<String, Contact> stringPersonEntry : cache) {
//                Contact person = stringPersonEntry.getValue();
//                contacts.add(person);
//            }
////            ArangoCursor<BaseDocument> cursor = arangoDB.db(DB_NAME).query(getAllQuery, BaseDocument.class);
////            cursor.forEachRemaining(baseDocument -> contacts.add(convertDocumentToContact(baseDocument)));
//        } catch (ArangoDBException exception) {
//            System.err.println("Failed to execute query " + exception.getMessage());
//        }
//        return contacts;
//    }

    @Override
    public List<Contact> getContacts() {
        return null;
    }

    @Override
    public Long getContactsSize() {
        return arangoDB.db(DB_NAME).collection(COLLECTION_NAME).count().getCount();
    }

    @Override
    public Optional<Contact> createContact(Contact contact) {
        BaseDocument myContact = new BaseDocument();
        myContact.addAttribute("firstName", contact.getFirstName());
        myContact.addAttribute("lastName", contact.getLastName());
        myContact.addAttribute("phone", contact.getPhone());
        myContact.addAttribute("creationDate", contact.getCreationDate().toString());
        try {
            arangoDB.db(DB_NAME).collection(COLLECTION_NAME).insertDocument(myContact);
            System.out.println("Document created");
            return Optional.of(contact);
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Contact updateContact(String firstName, Optional<String> newFirstName, Optional<String> lastName, Optional<String> phone) {
        BaseDocument baseDocument = getDocumentByContactFirstName(firstName);
        newFirstName.ifPresent(s -> baseDocument.updateAttribute("firstName", s));
        lastName.ifPresent(s -> baseDocument.updateAttribute("lastName", s));
        phone.ifPresent(s -> baseDocument.updateAttribute("phone", s));
        try {
            arangoDB.db(DB_NAME).collection(COLLECTION_NAME).updateDocument(baseDocument.getKey(), baseDocument);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteContact(String firstName) {
        BaseDocument baseDocument = getDocumentByContactFirstName(firstName);
        try {
            arangoDB.db(DB_NAME).collection(COLLECTION_NAME).deleteDocument(baseDocument.getKey());
            return true;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    public static Contact convertDocumentToContact(BaseDocument document) {
        Contact contact = new Contact();
        contact.setFirstName(document.getAttribute("firstName").toString());
        contact.setLastName(document.getAttribute("lastName").toString());
        contact.setPhone(document.getAttribute("phone").toString());
        contact.setCreationDate(LocalDate.parse(document.getAttribute("creationDate").toString()));
        return contact;
    }

    private BaseDocument getDocumentByContactFirstName(String firstName) {
        BaseDocument baseDocument = null;
        try {
            String findByFirstNameQuery = "FOR c IN " + COLLECTION_NAME + " FILTER c.firstName == '" + firstName + "' RETURN c";
            ArangoCursor<BaseDocument> cursor = arangoDB.db(DB_NAME).query(findByFirstNameQuery, BaseDocument.class);
            baseDocument = cursor.next();
        } catch (ArangoDBException exception) {
            System.err.println("Error executing query " + exception.getMessage());
        }
        return baseDocument;
    }
}

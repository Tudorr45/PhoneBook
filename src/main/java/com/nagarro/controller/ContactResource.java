package com.nagarro.controller;

import com.nagarro.model.Contact;
import com.nagarro.service.ContactService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/contacts")
public class ContactResource {

    @Inject
    ContactService contactService;

    public static List<Contact> contacts = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContacts() {
        return Response.ok(contacts).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/size")
    public Integer countContacts() {
        return contacts.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContact(Contact newContact) {
        contacts.add(newContact);
        Optional<Contact> addedContact = contactService.createContact(newContact);
        if(addedContact.isPresent()) {
            return Response.ok(addedContact.get()).build();
        } else {
            return Response.noContent().build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateContact(@PathParam("id") Long id,
                                  @QueryParam("firstName") Optional<String> firstName,
                                  @QueryParam("lastName") Optional<String> lastName,
                                  @QueryParam("phone") Optional<String> phone) {
        contacts = contacts.stream().map(contact -> {
            if (contact.getId().equals(id)) {
                firstName.ifPresent(contact::setFirstName);
                lastName.ifPresent(contact::setLastName);
                phone.ifPresent(contact::setPhone);
            }
            return contact;
        }).collect(Collectors.toList());
        return Response.ok(contacts).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") Long id) {
        Optional<Contact> contactToDelete = contacts.stream().filter(contact ->
                contact.getId().equals(id)).findFirst();
        boolean removed = false;
        if (contactToDelete.isPresent()) {
            removed = contacts.remove(contactToDelete.get());
        }
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}

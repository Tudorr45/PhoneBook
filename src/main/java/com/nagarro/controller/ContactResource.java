package com.nagarro.controller;

import com.nagarro.model.Contact;
import com.nagarro.service.ContactService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/contacts")
public class ContactResource {

    @Inject
    ContactService contactService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContacts() {
        return Response.ok(contactService.getContacts()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/size")
    public Long countContacts() {
        return contactService.getContactsSize();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContact(Contact newContact) {
        Optional<Contact> addedContact = contactService.createContact(newContact);
        if (addedContact.isPresent()) {
            return Response.ok(addedContact.get()).build();
        } else {
            return Response.noContent().build();
        }
    }

    @PUT
    @Path("{firstName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateContact(@PathParam("firstName") String firstName,
                                  @QueryParam("newFirstName") Optional<String> newFirstName,
                                  @QueryParam("lastName") Optional<String> lastName,
                                  @QueryParam("phone") Optional<String> phone) {
        return Response.ok(contactService.updateContact(firstName, newFirstName, lastName, phone)).build();
    }

    @DELETE
    @Path("{firstName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("firstName") String firstName) {
        if (contactService.deleteContact(firstName)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

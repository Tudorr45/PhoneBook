package com.nagarro.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Contact implements Serializable {

    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate creationDate;

    public Contact() {

    }

    public Contact(String firstName, String lastName, String phone, LocalDate creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.creationDate = creationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate contactCreationDate) {
        this.creationDate = contactCreationDate;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}

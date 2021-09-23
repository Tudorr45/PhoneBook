package com.nagarro.model;

import java.time.LocalDate;

public class Contact {

    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate creationDate;

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
}

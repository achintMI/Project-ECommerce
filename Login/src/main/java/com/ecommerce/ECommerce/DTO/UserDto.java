package com.ecommerce.ECommerce.DTO;

import com.ecommerce.ECommerce.UserSecurity.HashPassword;

public class UserDto {
    private String id;
    private String firstname;
    private String lastname;
    private String address;
    private String contact;
    private String email;
    private String password;


    public UserDto(String firstname, String lastname, String address, String contact, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.password = HashPassword.MD5(password);
    }

    public UserDto(String email, String password){
        this.email = email;
        this.password = HashPassword.MD5(password);
    }

    public UserDto(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password =HashPassword.MD5(password);
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "fname='" + firstname + '\'' +
                ", lname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

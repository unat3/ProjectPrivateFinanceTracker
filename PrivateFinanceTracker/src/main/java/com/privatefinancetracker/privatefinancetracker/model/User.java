package com.privatefinancetracker.privatefinancetracker.model;

import com.privatefinancetracker.privatefinancetracker.repository.DataManager;

import java.sql.Timestamp;

public class User {
    private Integer userId;
    private String name;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String location;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User(Integer userId, String name, String lastName, String dateOfBirth, String email, String phone, String location, Timestamp createdAt, Timestamp updatedAt) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public User(String name, String lastName, String dateOfBirth, String email, String phone, String location, String password) {
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.password = password;
    }

    public User(String name, String lastName, String dateOfBirth, String email, String phone, String password) {
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Integer getUserId(Integer loggedInUserId) {
        return DataManager.getLoggedInUserId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}



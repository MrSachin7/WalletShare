package com.sachin_himal.walletshare.entity;

import java.util.ArrayList;
import java.util.List;

public class User {


    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> groupIds;

    public User(){
        groupIds = new ArrayList<>();
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        groupIds = new ArrayList<>();

    }

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        groupIds = new ArrayList<>();

    }





    public String getFirstName() {
        return firstName;

    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        this.password = password;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }


    public String retrieveFullName() {
        return firstName + " " + lastName;
    }
}

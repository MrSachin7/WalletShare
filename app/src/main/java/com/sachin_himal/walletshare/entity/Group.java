package com.sachin_himal.walletshare.entity;



import java.util.ArrayList;
import java.util.List;

public class Group {

    private String groupName;
    private List<User> users;
    private int amount;


    public Group(String groupName, List<User> users) {
        this.groupName = groupName;
        this.users = users;amount = 0;
    }

    public Group(String groupName) {
        this.groupName = groupName;
        users = new ArrayList<>();
        amount = 0;
    }

    public Group() {
    users = new ArrayList<>();
        amount = 0;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void  addUser(User user){
        users.add(user);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

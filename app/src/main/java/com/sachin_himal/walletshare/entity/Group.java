package com.sachin_himal.walletshare.entity;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Group {

    private String groupName;
    private List<String> usersIds;
    private double amount;
    private String groupId;
    private List<HashMap<String,Integer>> userDue ;
    private List<GroupUser> groupUsers;

    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public Group() {
    }


    public List<HashMap<String, Integer>> getUserDue() {
        return userDue;
    }

    public void setUserDue(List<HashMap<String, Integer>> userDue) {

        this.userDue = userDue;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName, Double amount) {
        this.groupName = groupName;
        this.amount = amount;
        usersIds = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getUsersId() {
        return usersIds;
    }

    public void setusersIdManual(List<String> usersId) {
        this.usersIds = usersId;
    }

   /**
    * TODO: ask about how to deserialize
    * public void setUsersId(List<String> usersId) {
        this.usersIds = usersId;
    }
**/



    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


}

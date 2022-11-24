package com.sachin_himal.walletshare.entity;



import java.util.ArrayList;
import java.util.List;

public class Group {

    private String groupName;
    private List<String> usersIds;
    private int amount;
    private String groupId;


    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName, int amount) {
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



    public int getAmount() {
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

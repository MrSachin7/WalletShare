package com.sachin_himal.walletshare.entity;

public class GroupUser {
    private String uId;
    private Double amountDue;
    private String firstName;
    private String lastName;
    private Double tempAmount=0.00;



   public void addingMoneyToGroupExpense(Double a ){
        amountDue+=a;
    }

    public void updateAmountDue() {
        this.amountDue -= tempAmount;
        tempAmount=0.00;

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GroupUser() {

    }

    public Double getAmountDue() {
       updateAmountDue();
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String retrieveFullName() {
        return firstName + " " + lastName;
    }

    public Double getTempAmount() {
        return tempAmount;
    }

    public void setTempAmount(Double tempAmount) {
        this.tempAmount = tempAmount;
    }

}

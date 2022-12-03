package com.sachin_himal.walletshare.entity;

import java.util.HashMap;

public class GroupExpense {
    private Double totalExpensesAmount;
    private HashMap<String,Double> amountForUser;

    public GroupExpense() {
    }


    public Double getTotalExpensesAmount() {
        return totalExpensesAmount;
    }

    public void setTotalExpensesAmount(Double totalExpensesAmount) {
        this.totalExpensesAmount = totalExpensesAmount;
    }

    public HashMap<String, Double> getAmountForUser() {
        return amountForUser;
    }

    public void setAmountForUser(HashMap<String, Double> amountForUser) {
        this.amountForUser = amountForUser;
    }
}

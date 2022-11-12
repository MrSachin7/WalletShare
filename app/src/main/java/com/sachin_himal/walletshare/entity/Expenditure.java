package com.sachin_himal.walletshare.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Expenditure {
    private double amount;
    private String timeOfExpenditure;
    private String category;
    private String payee;
    private String note;
    private String paymentType;


    public Expenditure() {

    }

    public Expenditure(double amount, String date, String time, String category, String paymentType, String payee, String note) {
        this.amount = amount;
        this.category = category;
        this.payee = payee;
        this.paymentType = paymentType;
        this.note = note;
        this.timeOfExpenditure = date + " " + time;


    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeOfExpenditure() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timeOfExpenditure, dateTimeFormatter);
    }

    public void setTimeOfExpenditure(String timeOfExpenditure) {
        this.timeOfExpenditure = timeOfExpenditure;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}

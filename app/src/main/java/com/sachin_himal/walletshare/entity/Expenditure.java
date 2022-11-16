package com.sachin_himal.walletshare.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Expenditure {


    private int id;
    private double amount;
    private String timeOfExpenditure;
    private String category;
    private String payee;
    private String note;
    private String expenditureType;
    private String paymentType;


    public Expenditure() {

        // Needed for framework, don't delete the empty constructor
    }

    public Expenditure(double amount, String date, String time, String category, String paymentType, String payee, String note, String expenditureType) {
        this.amount = amount;
        this.category = category;
        this.payee = payee;
        this.paymentType = paymentType;
        this.note = note;
        this.timeOfExpenditure = date + " " + time;
        this.expenditureType = expenditureType;


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

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public String getExpenditureType() {

        return expenditureType;
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



    public String getDateString(){

        LocalDateTime timeOfExpenditure = getTimeOfExpenditure();


        int day = timeOfExpenditure.getDayOfMonth();
        String month = timeOfExpenditure.getMonth().toString();
        month =Character.toUpperCase( month.charAt(0))+ month.substring(1).toLowerCase();
        int year = timeOfExpenditure.getYear();

        return day + " "+ month+ " "+ year;


    }


    public String getTimeString(){

        LocalDateTime timeOfExpenditure = getTimeOfExpenditure();
        int hour = timeOfExpenditure.getHour();
        int minute = timeOfExpenditure.getMinute();

        if (hour >12){
            hour= hour % 12;
            return hour+ "."+ minute+ "PM";
        }
        return hour+ "."+ minute+ "AM";
    }



}

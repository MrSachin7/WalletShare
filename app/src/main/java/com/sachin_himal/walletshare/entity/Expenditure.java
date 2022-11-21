package com.sachin_himal.walletshare.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Expenditure implements Comparable<Expenditure> {


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
        timeOfExpenditure = date + " "+ time;
        this.expenditureType = expenditureType;
    }

    public LocalDateTime retrieveAsLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timeOfExpenditure, dateTimeFormatter);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimeOfExpenditure() {
      return timeOfExpenditure;
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

        LocalDateTime timeOfExpenditure = retrieveAsLocalDateTime();


        int day = timeOfExpenditure.getDayOfMonth();
        String month = timeOfExpenditure.getMonth().toString();
        month =Character.toUpperCase( month.charAt(0))+ month.substring(1).toLowerCase();
        int year = timeOfExpenditure.getYear();

        return day + " "+ month+ " "+ year;


    }


    public String getTimeString(){

        LocalDateTime timeOfExpenditure = retrieveAsLocalDateTime();
        int hour = timeOfExpenditure.getHour();
        int minute = timeOfExpenditure.getMinute();

        if (hour >12){
            hour= hour % 12;
            return hour+ "."+ minute+ "PM";
        }
        return hour+ "."+ minute+ "AM";
    }

    @Override
    public String toString() {
        return "Expenditure{" +
                "id=" + id +
                ", amount=" + amount +
                ", timeOfExpenditure='" + timeOfExpenditure + '\'' +
                ", category='" + category + '\'' +
                ", payee='" + payee + '\'' +
                ", note='" + note + '\'' +
                ", expenditureType='" + expenditureType + '\'' +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }

    @Override
    public int compareTo(Expenditure expenditure) {
        LocalDateTime localDateTime = expenditure.retrieveAsLocalDateTime();
        return localDateTime.compareTo(retrieveAsLocalDateTime());
    }
}

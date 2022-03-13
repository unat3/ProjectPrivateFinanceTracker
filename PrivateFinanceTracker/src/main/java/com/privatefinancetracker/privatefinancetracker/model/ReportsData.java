package com.privatefinancetracker.privatefinancetracker.model;

import java.util.Date;

public class ReportsData {
    //amount, dateAndTimeOfTransaction, description, category
    private double price;
    private Date date;
    private String purchase;
    private String category;

    public ReportsData(double price, Date date, String purchase, String category) {
        this.price = price;
        this.date = date;
        this.purchase = purchase;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

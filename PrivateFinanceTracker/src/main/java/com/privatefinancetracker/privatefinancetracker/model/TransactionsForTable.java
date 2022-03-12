package com.privatefinancetracker.privatefinancetracker.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class TransactionsForTable {
    private int Id;
    private SimpleStringProperty date;
    private SimpleStringProperty currency;
    private Double price;
    private SimpleStringProperty purchase;
    private ComboBox categoryChooser;

    public TransactionsForTable() {}

    public TransactionsForTable(String date, String currency, Double price, String purchase, ObservableList data) {
        this.Id = 1;
        this.date = new SimpleStringProperty(date);
        this.currency = new SimpleStringProperty(currency);
        this.price = price;
        this.purchase = new SimpleStringProperty(purchase);
        this.categoryChooser = new ComboBox(data);
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency.get();
    }

    public void setCurrency(SimpleStringProperty currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double sum) {
        this.price = price;
    }

    public String getPurchase() {
        return purchase.get();
    }

    public void setName(SimpleStringProperty purchase) {
        this.purchase = purchase;
    }

    public ComboBox getCategoryChooser() {
        return categoryChooser;
    }

    public void setCategoryChooser(ComboBox categoryChooser) {
        this.categoryChooser = categoryChooser;
    }

    @Override
    public String toString() {
        return
                "Id: " + Id +
                        ", date: '" + date +
                        ", currency: '" + currency +
                        ", sum: " + price +
                        ", purchase: " + purchase  +
                        ", category: " + categoryChooser +
                        '}';
    }
}


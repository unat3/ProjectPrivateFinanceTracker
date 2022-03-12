package com.privatefinancetracker.privatefinancetracker.model;

import com.privatefinancetracker.privatefinancetracker.repository.DataManager;

public class Transactions {

        private int Id;
        private Integer userId;

        private String date;
        private String currency;
        private Double sum;
        private String name;
        private String category;

        public Transactions() {}

        public Transactions(int Id, Integer userId, String date, String currency, Double sum, String name, String category) {
            this.Id = Id;
            this.userId = userId;
            this.date = date;
            this.currency = currency;
            this.sum = sum;
            this.name = name;
            this.category = category;
        }
    public Transactions( Integer userID,String date, Double sum, String name, String category) {
            this.userId = userID;
        this.date = date;
        this.sum = sum;
        this.name = name;
        this.category = category;
    }

    public Integer getUserId(Integer loggedInUserId) {
        return DataManager.getLoggedInUserId();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getSum() {
            return sum;
        }

        public void setSum(Double sum) {
            this.sum = sum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return
                    "Id: " + Id +
                            ", date: '" + date +
                            ", currency: '" + currency +
                            ", sum: " + sum +
                            ", name: " + name  +
                            ", category: " + category +
                            '}';
        }
    }


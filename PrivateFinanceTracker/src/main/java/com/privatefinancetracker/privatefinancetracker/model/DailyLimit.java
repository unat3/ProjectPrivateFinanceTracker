package com.privatefinancetracker.privatefinancetracker.model;

import com.privatefinancetracker.privatefinancetracker.repository.DataManager;

public class DailyLimit {
    private Integer userId;
    private String dailyLimit;

    public DailyLimit(Integer userId, String dailyLimit) {
        this.userId = userId;
        this.dailyLimit = dailyLimit;
    }

    public DailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Integer getUserId(Integer loggedInUserId) {
        return DataManager.getLoggedInUserId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }
}

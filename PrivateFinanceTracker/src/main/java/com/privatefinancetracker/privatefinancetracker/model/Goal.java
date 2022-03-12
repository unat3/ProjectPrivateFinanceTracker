package com.privatefinancetracker.privatefinancetracker.model;

import com.privatefinancetracker.privatefinancetracker.repository.DataManager;

import java.sql.Date;
import java.time.LocalDate;

public class Goal {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private String amountToSave;
    private LocalDate startDate;
    private LocalDate endDate;
    private String amountSaved;

    public Goal(Integer id, Integer userId, String name, String description, String amountToSave, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amountToSave = amountToSave;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Goal(Integer id, Integer userId, String name, String amountToSave, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amountToSave = amountToSave;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Goal(String name, String description, String amountToSave, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.amountToSave = amountToSave;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Goal(Integer id, Integer userId, String name, String description, String amountToSave, LocalDate startDate, LocalDate endDate, String amountSaved) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amountToSave = amountToSave;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountSaved = amountSaved;
    }



    public Integer getUserId(Integer loggedInUserId) {
        return DataManager.getLoggedInUserId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmountToSave() {
        return amountToSave;
    }

    public void setAmountToSave(String amountToSave) {
        this.amountToSave = amountToSave;
    }

    public Date getStartDate() {
        return java.sql.Date.valueOf(startDate);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return java.sql.Date.valueOf(endDate);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAmountSaved() {
        return amountSaved;
    }

    public void setAmountSaved(String amountSaved) {
        this.amountSaved = amountSaved;
    }

    @Override
    public String toString() {
        return name + '\'' + description + '\'';
    }
}

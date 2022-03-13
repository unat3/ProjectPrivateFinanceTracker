package com.privatefinancetracker.privatefinancetracker.repository;

import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTableList;

public class DataManager {
    private static DataManager dataManagerInstance;
   //private static Integer loggedInUserId;
   private static Integer loggedInUserId = null;
    private static TransactionsForTableList transList = null;
    private static TransactionsForTableList reportsDataList = null;

    DataManager(){
    }

    public static TransactionsForTableList getReportsDataList() {
        return reportsDataList;
    }

    public static void setReportsDataList(TransactionsForTableList reportsDataList) {
        DataManager.reportsDataList = reportsDataList;
    }

    public static TransactionsForTableList getTransList() {
        return transList;
    }

    public static void setTransList(TransactionsForTableList transList) {
        DataManager.transList = transList;
    }

    public static DataManager getInstance() {
        if (dataManagerInstance == null) dataManagerInstance = new DataManager();
        return dataManagerInstance;}

    public static Integer getLoggedInUserId() {return loggedInUserId;}

    public void setLoggedInUserId(Integer loggedInUserId){
        DataManager.loggedInUserId = loggedInUserId;
    }
}

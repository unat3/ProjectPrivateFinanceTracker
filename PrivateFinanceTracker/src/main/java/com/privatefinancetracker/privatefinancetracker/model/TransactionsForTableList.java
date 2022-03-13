package com.privatefinancetracker.privatefinancetracker.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionsForTableList {
    private ObservableList<TransactionsForTable> allTransactionsForTable;
    private ObservableList<ReportsData> reportsDataList;

    public TransactionsForTableList() {
        allTransactionsForTable = FXCollections.observableArrayList();
        reportsDataList = FXCollections.observableArrayList();
    }

    public void addTransactionsForTable(TransactionsForTable newTransactionForTable) {
        this.allTransactionsForTable.add(newTransactionForTable);
    }

    public void addDataForTable(ReportsData newDataforTable) {
        this.reportsDataList.add(newDataforTable);
    }

    public ObservableList<TransactionsForTable> getAllTransactionForTable() {
        return allTransactionsForTable;
    }

    public ObservableList<ReportsData> getDataForTable() {
        return reportsDataList;
    }


    //methods to look for/update/delete transactions in case we want to add these possibilities (for now unused):
    /*
        public TransactionsForTable lookupTransactionForTable(int TransactionForTableId){
            for(int i = 0; i < this.allTransactionsForTable.size(); i++){
                if(this.allTransactionsForTable.get(i).getId() == TransactionForTableId){
                    return this.allTransactionsForTable.get(i);
                }
            }
            return null;    //no TransactionForTable found
        }

        public void updateTransactionForTable(int index, TransactionsForTable selectedTransactionForTable){
            allTransactionsForTable.set(index, selectedTransactionForTable);
        }

        public boolean deleteTransactionForTable(TransactionsForTable selectedTransactionForTable){
            for(int i = 0; i < allTransactionsForTable.size(); i++){
                if(allTransactionsForTable.get(i).equals(selectedTransactionForTable)){
                    allTransactionsForTable.remove(i);
                    return true;
                }
            }
            return false;
        }


*/

}

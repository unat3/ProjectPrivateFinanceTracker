package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTable;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTableList;
import com.privatefinancetracker.privatefinancetracker.repository.DBManager;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class TransactionInputController extends ViewController implements Initializable {

    // For the transaction input view
    @FXML
    private Label filePathLabel;
    @FXML
    private DatePicker dateInput;
    @FXML
    private TextField purchaseInput;
    @FXML
    private TextField priceInput;
    @FXML
    private ChoiceBox currencyInput;
    String transactionInputFilePath;

// For the transaction categories view

    @FXML
    private TableColumn<TransactionsForTable, String> dateColumn;
    @FXML
    private TableColumn<TransactionsForTable, String> currencyColumn;
    @FXML
    private TableColumn<TransactionsForTable, Double> priceColumn;
    @FXML
    private TableColumn<TransactionsForTable, String> purchaseColumn;
    @FXML
    private TableColumn<TransactionsForTable, ComboBox> categoryChooser;


    TransactionsForTableList  transList = new TransactionsForTableList();
    DBManager databaseManager;
    Connection conn;
    ObservableList<String> categoriesList;

    @FXML
    private TableView<TransactionsForTable> tableView;





    public TransactionInputController() {

        categoriesList = FXCollections.observableArrayList();
        categoriesList.add("Food");
        categoriesList.add("Clothes");
        categoriesList.add("Household");
        categoriesList.add("Healthcare");
        categoriesList.add("Housing");
        categoriesList.add("Entertainment");
        categoriesList.add("Transportation");
        categoriesList.add("Utilities");
        categoriesList.add("Savings");
        categoriesList.add("Earnings");
        categoriesList.add("Unsorted");

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        databaseManager = new DBManager();
        conn = databaseManager.getConnection();

        //for the currency input choicebox in transaction input window
        currencyInput.getItems().add("USD");
        currencyInput.getItems().add("GBP");
        currencyInput.getItems().add("EUR");

    }


    @FXML
    private void BrowseForFileClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.CSV"));
        File f = fc.showOpenDialog(null);
        if (f != null) {

            transactionInputFilePath = f.getAbsolutePath();
            filePathLabel.setText(transactionInputFilePath);
        }

    }

    @FXML
    private void AddButtonPressed(ActionEvent event) {

        try {
            String transactionDate = dateInput.getValue().toString();
            String transactionPurchase = purchaseInput.getText();
            Double transactionPrice = Double.parseDouble(priceInput.getText());
            String transactionCurrency = currencyInput.getValue().toString();
            TransactionsForTable newTransaction = new TransactionsForTable(transactionDate, transactionCurrency, transactionPrice, transactionPurchase, categoriesList);
            transList.addTransactionsForTable(newTransaction);
            DataManager.setTransList(transList);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void doneButtonPressed(ActionEvent actionEvent) {
        System.out.println(transactionInputFilePath);
        String line = "";
        String splitBy = ";";
        try {

            BufferedReader br = new BufferedReader(new FileReader(transactionInputFilePath));
            while ((line = br.readLine()) != null) {
                int k = 0;
                int Id = 0;
                while ((line = br.readLine()) != null) { //šī daļa (29-32) ļauj izlaist pirmo rindu CSV failā
                    if (k == 0) {
                        k++;
                        continue;
                    }
                    String[] transactionData = line.split(splitBy);
                    //System.out.println("Date of payment: " + transactionData[1] + ", Currency: " + transactionData[2] + ", Sum: " + transactionData[3] + " Name: " + transactionData[4]);
                    String date = transactionData[1];
                    String currency = transactionData[2];
                    double price = Double.parseDouble(transactionData[3]);
                    String purchase = transactionData[4];
                    TransactionsForTable transaction = new TransactionsForTable(date, currency, price, purchase, categoriesList);
                    transList.addTransactionsForTable(transaction);
                    DataManager.setTransList(transList);


                }
            }

            changeScene(actionEvent, "transactioncategories");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong in reading the CSV file, please try again");
        }



    }

    @FXML
    private void doneButton2Pressed(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "transactioncategories");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void backToMainPage(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "mainpage");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}



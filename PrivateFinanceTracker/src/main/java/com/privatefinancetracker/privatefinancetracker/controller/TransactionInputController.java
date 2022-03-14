package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTable;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTableList;
import com.privatefinancetracker.privatefinancetracker.repository.DBManager;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionInputController extends ViewController implements Initializable {

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

    ObservableList<String> categoriesList;
    TransactionsForTableList transList = new TransactionsForTableList();

    DBManager databaseManager;
    Connection conn;


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
            //read the CSV file
            BufferedReader br = new BufferedReader(new FileReader(transactionInputFilePath));
            while ((line = br.readLine()) != null) {
                int k = 0;
                int Id = 0;
                while ((line = br.readLine()) != null) {
                    if (k == 0) {
                        k++;
                        continue;
                    }
                    String[] transactionData = line.split(splitBy);
                    System.out.println("Date of payment: " + transactionData[1] + ", Currency: " + transactionData[2] + ", Sum: " + transactionData[3] + " Name: " + transactionData[4]);
                    String datePreFormat = transactionData[1];
                    //reformat the date
                    Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(datePreFormat);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    String date = formatter.format(date1);


                    String currency = transactionData[2];
                    double price = Double.parseDouble(transactionData[3]);
                    String purchase = transactionData[4];
                    TransactionsForTable transaction = new TransactionsForTable(date, currency, price, purchase, categoriesList);
                    transList.addTransactionsForTable(transaction);
                    DataManager.setTransList(transList);

                }
            }

            changeScene(actionEvent, "transactioncategories");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Something went wrong in reading the CSV file, please try again");
        }


    }

    @FXML
    private void doneButton2Pressed(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "transactioncategories");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void backToMainPage(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "mainpage");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}



package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.Transactions;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTable;
import com.privatefinancetracker.privatefinancetracker.model.User;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionCategoriesController extends ViewController implements Initializable {

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

    @FXML
    private TableView<TransactionsForTable> tableView;



    public TransactionCategoriesController() {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (dateColumn != null) {
            dateColumn.setCellValueFactory(new PropertyValueFactory<TransactionsForTable, String>("date"));
            currencyColumn.setCellValueFactory(new PropertyValueFactory<TransactionsForTable, String>("currency"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<TransactionsForTable, Double>("price"));
            purchaseColumn.setCellValueFactory(new PropertyValueFactory<TransactionsForTable, String>("purchase"));
            categoryChooser.setCellValueFactory(new PropertyValueFactory<TransactionsForTable, ComboBox>("categoryChooser"));

            tableView.setItems(DataManager.getTransList().getAllTransactionForTable());
        }
    }
    @FXML
    private void saveButtonPressed(ActionEvent event) throws SQLException {
        UserService userService = new UserService();

        int ind = 0;
        while (dateColumn.getCellObservableValue(ind) != null) {

            ObservableValue<ComboBox> comboBoxOBV = categoryChooser.getCellObservableValue(ind);
            ComboBox comboBoxVal = comboBoxOBV.getValue();

            int index1 = comboBoxVal.getSelectionModel().getSelectedIndex();
            ObservableList comboBoxList = comboBoxVal.getItems();
            String selectedCategory = String.valueOf(comboBoxList.get(index1));
            System.out.println(selectedCategory);


            String selectedDate = dateColumn.getCellObservableValue(ind).getValue();
            String selectedCurrency = currencyColumn.getCellObservableValue(ind).getValue();
            Double selectedPrice = priceColumn.getCellObservableValue(ind).getValue();
            String selectedPurchase = purchaseColumn.getCellObservableValue(ind).getValue();
            System.out.println(selectedDate + " " + selectedPurchase + " " + selectedCurrency + " " + selectedPrice + " " + selectedCategory);
            ind++;
            Transactions transactionsDb = new Transactions(0,selectedDate,selectedPrice,selectedPurchase,selectedCategory);
            userService.saveTransactions(transactionsDb);
        }
    }

    public void backToTransactionInput(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "transactioninput");
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

package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.Category;
import com.privatefinancetracker.privatefinancetracker.model.ReportsData;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTable;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTableList;
import com.privatefinancetracker.privatefinancetracker.repository.DBManager;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

//this is the controller
public class ReportsController extends ViewController implements Initializable {

    //table variables
    @FXML
    private TableColumn<ReportsData, String> dateCol;
    @FXML
    private TableColumn<ReportsData, Double> priceCol;
    @FXML
    private TableColumn<ReportsData, String> purchaseCol;
    @FXML
    private TableColumn<ReportsData, String> categoryCol;
    @FXML
    private TableView<ReportsData> reportsTable;

    //button and text field variables
    @FXML
    private DatePicker dateToPicker;
    @FXML
    private DatePicker dateFromPicker;
    @FXML
    private TextArea spentSumText;
    @FXML
    private ChoiceBox<String> categoryPicker;
    TransactionsForTableList reportsDataList = new TransactionsForTableList();
    @FXML
    PieChart pieChart;
    double sum = 0;


    //Database connection
    DBManager databaseManager;
    Connection conn;

    public ReportsController() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        databaseManager = new DBManager();
        conn = databaseManager.getConnection();

        //for the category picker choicebox in transaction input window
        categoryPicker.getItems().add("Food");
        categoryPicker.getItems().add("Clothes");
        categoryPicker.getItems().add("Household");
        categoryPicker.getItems().add("Healthcare");
        categoryPicker.getItems().add("Housing");
        categoryPicker.getItems().add("Entertainment");
        categoryPicker.getItems().add("Transportation");
        categoryPicker.getItems().add("Utilities");
        categoryPicker.getItems().add("Savings");
        categoryPicker.getItems().add("Earnings");
        categoryPicker.getItems().add("Unsorted");


        if (dateCol != null) {
            dateCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("date"));
            priceCol.setCellValueFactory(new PropertyValueFactory<ReportsData, Double>("price"));
            purchaseCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("purchase"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("category"));
        }
    }

    public void goButtonPressed() throws Exception {
        LocalDate dateFrom = dateFromPicker.getValue();
        LocalDate dateTo = dateToPicker.getValue();
        String category = categoryPicker.getValue();

        UserService userService = new UserService();
        int userID = DataManager.getLoggedInUserId();
        //getting data from database for the table:
        ResultSet resultSet = userService.populateTableFromDB(userID, category, dateFrom, dateTo);

        while (resultSet.next()) {

            reportsDataList.addDataForTable(new ReportsData(resultSet.getDouble("amount"), resultSet.getDate("dateAndTimeOfTransaction"), resultSet.getString("description"), resultSet.getString("category")));

        }

        DataManager.setReportsDataList(reportsDataList);
        reportsTable.setItems(DataManager.getReportsDataList().getDataForTable());

        // getting and setting the sum of the transactions in  a category:
        int length = DataManager.getReportsDataList().getDataForTable().size();
        int index = 0;
        double sum = 0;
        while (index < length) {
            double tempSum = DataManager.getReportsDataList().getDataForTable().get(index).getPrice();
            sum = sum + tempSum;
            index++;

            sum = GoalsCalculatorController.withTwoDecimalPlaces(sum);

        }
        spentSumText.setText("Total spendings/earnings between " + dateFrom + " and " + dateTo + " in category " + category + " : " + sum + "eur");
        System.out.println(sum);

    }

    public void backToMainPage(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "mainpage");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

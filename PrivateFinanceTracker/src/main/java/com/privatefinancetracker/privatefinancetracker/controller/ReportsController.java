package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.Category;
import com.privatefinancetracker.privatefinancetracker.model.ReportsData;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTable;
import com.privatefinancetracker.privatefinancetracker.repository.DBManager;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    //Database connection
    DBManager databaseManager;
    Connection conn;

    public ReportsController() {}
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

        ObservableList<ReportsData> reportsDataList;
        if (dateCol != null) {
            dateCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("date"));
            priceCol.setCellValueFactory(new PropertyValueFactory<ReportsData, Double>("currency"));
            purchaseCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("price"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("purchase"));
            reportsTable.setItems(DataManager.getReportsDataList().getDataForTable());

        }
    }
    public void goButtonPressed() throws Exception {
       LocalDate dateFrom = dateFromPicker.getValue();
       LocalDate dateTo = dateToPicker.getValue();
       String category = categoryPicker.getValue();
        System.out.println(" I have correct input for DB: " + dateFrom + " " + dateTo + " " + category);

        UserService userService = new UserService();
        int userID = DataManager.getLoggedInUserId();
        System.out.println(userService.populateTableFromDB(userID,category,dateFrom,dateTo));

        if (dateCol != null) {
            dateCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("date"));
            priceCol.setCellValueFactory(new PropertyValueFactory<ReportsData, Double>("currency"));
            purchaseCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("price"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<ReportsData, String>("purchase"));


            ResultSet resultSet = userService.populateTableFromDB(userID,category,dateFrom,dateTo);
            System.out.println("I have results from DB: " + resultSet);

            while (true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ReportsData listData = null;
                try {
                    listData = new ReportsData(resultSet.getDouble("amount"), resultSet.getDate("dateAndTimeOfTransaction"), resultSet.getString("description"), resultSet.getString("category"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                DataManager.getReportsDataList().addDataForTable(listData);

            }

            System.out.println("I have saved the list" + DataManager.getReportsDataList().getDataForTable());

            reportsTable.setItems(DataManager.getReportsDataList().getDataForTable());
            // tableView.setItems(DataManager.getTransList().getAllTransactionForTable());
        }

    }

}
//reportsTable - tabula
//categoryPicker - kategoriju izvelne
//dateFromPicker - datuma no izvelne // onAction - dateFromPicked
//dateToPicker - datuma lidz izvelne // onAction - dateToPicked
//dateCol;
//priceCol;
//purchaseCol;
//categoryCol;
//spentSumText - text field for displaying sum
//go button - onAction - goButtonPressed
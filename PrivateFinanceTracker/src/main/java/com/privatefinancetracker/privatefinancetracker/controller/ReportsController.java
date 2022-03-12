package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.Category;
import com.privatefinancetracker.privatefinancetracker.model.TransactionsForTable;
import com.privatefinancetracker.privatefinancetracker.repository.DBManager;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportsController extends ViewController implements Initializable {

    //table variables
    @FXML
    private TableColumn<TransactionsForTable, String> dateCol;
    @FXML
    private TableColumn<TransactionsForTable, Double> priceCol;
    @FXML
    private TableColumn<TransactionsForTable, String> purchaseCol;
    @FXML
    private TableColumn<TransactionsForTable, String> categoryCol;
    @FXML
    private TableView<TransactionsForTable> reportsTable;

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
    }

    public void goButtonPressed() throws Exception {
       LocalDate dateFrom = dateFromPicker.getValue();
       LocalDate dateTo = dateToPicker.getValue();
       String category = categoryPicker.getValue();
        System.out.println(dateFrom + " " + dateTo + " " + category);

        UserService userService = new UserService();
        int userID = DataManager.getLoggedInUserId();
        userService.populateTableFromDB(userID,category,dateFrom,dateTo);



        //now need to make a query for database to get out the dates
        //if (dbDate.equals(dateToPicker)){}
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
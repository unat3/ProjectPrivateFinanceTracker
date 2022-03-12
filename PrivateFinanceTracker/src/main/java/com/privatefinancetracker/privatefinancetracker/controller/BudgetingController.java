package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.DailyLimit;
import com.privatefinancetracker.privatefinancetracker.model.MonthlyBudget;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BudgetingController extends ViewController implements Initializable {
    UserService userService = new UserService();

    public TextField dailyLimitField;
    public ChoiceBox chooseCategoryForBudget;
    public TextField budgetAmountField;
    public TextField newDailyLimit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backToMainPage(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "mainpage");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveMonthlyBudget(ActionEvent actionEvent) {

    }

    public void setDailyLimit() throws SQLException {
        DailyLimit limit = new DailyLimit(dailyLimitField.getText());
        userService.saveDailyLimit(limit);
        showAlert(null, "Daily limit saved!", Alert.AlertType.INFORMATION);
    }

    public void editDailyLimitAmount() throws SQLException{
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        try {
            this.userService.editDailyLimit(userId, newDailyLimit.getText());
            showAlert(null, "Daily limit changed successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            showAlert(null, "An error occurred, please try again", Alert.AlertType.ERROR);
        }

    }
}

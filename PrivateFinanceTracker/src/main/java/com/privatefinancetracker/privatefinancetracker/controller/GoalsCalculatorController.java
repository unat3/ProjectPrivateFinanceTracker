package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.Goal;
import com.privatefinancetracker.privatefinancetracker.model.User;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GoalsCalculatorController extends ViewController implements Initializable {
    UserService userService = new UserService();

    public TextField goalNameField;
    public TextArea goalDescriptionField;
    public TextField goalMoneyAmountField;
    public DatePicker startDateField;
    public DatePicker endDateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setBackToMainPageButton(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "mainpage");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void goToViewMyGoals(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "viewgoals");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleGoalCalculation(){
      Goal goal = new Goal(goalNameField.getText(), goalDescriptionField.getText(), goalMoneyAmountField.getText(), startDateField.getValue(), endDateField.getValue());


        if (goalNameField.getText().isEmpty() || goalMoneyAmountField.getText().isEmpty()){
            showAlert(null, "Please provide all necessary fields - goal name, goal amount in €, start date and end date", Alert.AlertType.ERROR);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "In order to save for " + goalNameField.getText() + " until "+
                    endDateField.getValue() + " you will have to save " + calculateFinancialGoalAmountPerMonth(goalMoneyAmountField, startDateField, endDateField)
                    + " € per month. Would you like to save this goal?", ButtonType.YES, ButtonType.NO);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    userService.saveNewGoal(goal);
                    showAlert(null, "Goal saved successfully!", Alert.AlertType.INFORMATION);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            } else {
                alert.close();
            }

        }
    }

    public double calculateFinancialGoalAmountPerMonth(TextField goalMoneyAmountField, DatePicker startDateField, DatePicker endDateField){
        //goalMoneyAmountField = null;
        //startDateField = null;
        //endDateField = null;

        //assert goalMoneyAmountField != null;
        double goalAmount = Double.parseDouble(goalMoneyAmountField.getText());
      LocalDate startDate = startDateField.getValue();
      LocalDate endDate = endDateField.getValue();

     // getDaysFromStartToEndDate(startDate, endDate);
      long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
      double amountToSavePerMonth;
      amountToSavePerMonth = goalAmount / (daysBetween / 30.0);
      amountToSavePerMonth = withTwoDecimalPlaces(amountToSavePerMonth);
        return amountToSavePerMonth;
    }
    public static double withTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(value));
    }
   /* public void showMonthlyGoalCalculation(){

        showAlert(null, "In order to save for " + goalNameField.getText() + " until "+
                endDateField.getValue() + "you will have to save " + calculateFinancialGoalAmountPerMonth(goalMoneyAmountField, startDateField, endDateField)
                + "€ per month", Alert.AlertType.INFORMATION);

    } */

    /* public static List<LocalDate> getDaysFromStartToEndDate(LocalDate startDate, LocalDate endDate){
        return startDate.datesUntil(endDate).collect(Collectors.toList());
    } */


}

package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.Goal;
import com.privatefinancetracker.privatefinancetracker.model.User;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewExistingGoalsController extends ViewController implements Initializable {
    UserService userService = new UserService();
    public Label goalNameLabel;
    public Label descriptionLabel;
    public Label startDateLabel;
    public Label endDateLabel;
    public Label progressLabel;
    public Label howMuchSavedLabel;
    public Label percentLabel;
    public TextField updateSavedAmount;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataManager.getInstance();
        Integer userId = DataManager.getLoggedInUserId();
        Goal goal = null;
        try {
            goal = this.userService.getUserGoal(userId);
            goalNameLabel.setText(goal.getName());
            descriptionLabel.setText(goal.getDescription());
            startDateLabel.setText(goal.getStartDate().toString());
            endDateLabel.setText(goal.getEndDate().toString());
            addAmountSavedText();
            addPercentLabelText();
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  goalNameLabel.setText(goal.getName());
        descriptionLabel.setText(goal.getDescription());
        startDateLabel.setText(goal.getStartDate().toString());
        endDateLabel.setText(goal.getEndDate().toString());
        howMuchSavedLabel.setText(goal.getAmountSaved()); */

    }

    public void backToCalculator(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "goalscalculator");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleDeleteGoal(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this goal?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                int userId = DataManager.getLoggedInUserId();
                Goal goal = this.userService.getUserGoal(userId);
                userService.deleteGoal(goal);
                showAlert(null, "Goal deleted", Alert.AlertType.INFORMATION);
            } catch (Exception exception) {
                showAlert("Error", "Goal not found, please try again", Alert.AlertType.ERROR);
            }
        } else {
            alert.close();
        }

    }

    public void addAmountSavedText(){
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        double amountSaved = 0;
        double amountToSave = 0;

        try {
            amountSaved = userService.getGoalAmountSavedById(userId);
            amountToSave = userService.getGoalAmountToSaveById(userId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        howMuchSavedLabel.setText(amountSaved + " / " + amountToSave + "€");
    }

    public void addPercentLabelText(){
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        double amountSaved = 0;
        double amountToSave = 0;
        double percentage = 0;

        try {
            amountSaved = userService.getGoalAmountSavedById(userId);
            amountToSave = userService.getGoalAmountToSaveById(userId);
            percentage = GoalsCalculatorController.withTwoDecimalPlaces((amountSaved*100)/amountToSave);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        percentLabel.setText(percentage + "%");
    }

    public void handleUpdateGoalProgress() throws Exception {
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        double updatedAmount = userService.getGoalAmountSavedById(userId) + Double.parseDouble(updateSavedAmount.getText());
        System.out.println(updatedAmount);
        try {
            this.userService.updateGoalProgress(userId, updatedAmount);
            showAlert(null, "Goal progress updated successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            showAlert(null, "An error occurred, please try again", Alert.AlertType.ERROR);
        }

    }

    /* public void addGoalsInProgressText(){
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        String text = null;

        try {
            text = userService.findNameById(userId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        getgoalsInProgressLabel().setText(goalAmount + " goal/-s in progress");
    } */
}

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
import java.util.ResourceBundle;

public class ViewExistingGoalsController extends ViewController implements Initializable {
    UserService userService = new UserService();
    public TextField goalToDelete;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        goalNameLabel.setText(goal.getName());
        descriptionLabel.setText(goal.getDescription());
        startDateLabel.setText(goal.getStartDate().toString());
        endDateLabel.setText(goal.getEndDate().toString());
        howMuchSavedLabel.setText(goal.getAmountSaved());

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

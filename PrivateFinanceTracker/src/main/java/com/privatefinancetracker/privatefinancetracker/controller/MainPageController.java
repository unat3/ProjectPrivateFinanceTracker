package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends ViewController implements Initializable {
    UserService userService = new UserService();
    public Label welcomeLabel;
    public Label dailyLimitLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addWelcomeName();
            setDailyLimitLabel();
        }catch (Exception exception){
            showAlert("Alert", exception.getMessage(), Alert.AlertType.ERROR);
            exception.printStackTrace();
        }
    }

    public void handleLogOut(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                DataManager.getInstance().setLoggedInUserId(null);
                changeScene(actionEvent, "login");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            alert.close();
        }
    }

    public void goToGoalsCalculator(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "goalscalculator");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void goToProfileSettings(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "profilesettings");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void goToTransactionInputLog(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "transactioninput");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void goToBudgeting(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "budgeting");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void goToReportsView(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "reports");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void addWelcomeName(){
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        String name = null;

        try {
            name = userService.findNameById(userId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        getWelcomeLabel().setText("Hi, " + name);
    }

    public void setDailyLimitLabel(){
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        double dailyLimit = 0;

        try {
            dailyLimit = userService.getDailyLimitById(userId);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        getDailyLimitLabel().setText(dailyLimit + "€");
    }

    public Label getDailyLimitLabel(){
        return dailyLimitLabel;
    }

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }

    public void setWelcomeLabel(Label welcomeLabel) {
        this.welcomeLabel = welcomeLabel;
    }

}

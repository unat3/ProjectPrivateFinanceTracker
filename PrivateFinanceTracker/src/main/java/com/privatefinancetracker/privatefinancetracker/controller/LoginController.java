package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import com.privatefinancetracker.privatefinancetracker.controller.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends ViewController implements Initializable {
    UserService userService = new UserService();
    @FXML
    public Label notificationLabel;
    @FXML
    public TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    private Button handleUserLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void handleUserLogin(ActionEvent actionEvent){
        // two ways how to show alerts, messages, notifications
        try {
            Integer userId = userService.verifyUserDetails(emailField.getText(), passwordField.getText());
            notificationLabel.setText("Login successful");
           DataManager.getInstance().setLoggedInUserId(userId);
            changeScene(actionEvent, "mainpage");
        } catch (Exception e) {
            e.printStackTrace();
            notificationLabel.setText(e.getMessage());
            showAlert("Login unsuccessful", e.getMessage(), AlertType.ERROR);
        }

        // showAlertExample();




    }

    public void handleRegistration(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "register");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

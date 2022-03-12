package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.User;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController extends ViewController implements Initializable {
    private UserService userService = new UserService();
    public TextField nameField;
    public TextField lastNameField;
    public TextField dateOfBirthField;
    public TextField emailField;
    public TextField phoneField;
    public TextField locationField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void backToLogin(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "login");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void newAccountRegistration(ActionEvent actionEvent){
        User user = new User(nameField.getText(),
                lastNameField.getText(),
                dateOfBirthField.getText(),
                emailField.getText(),
                phoneField.getText(),
                locationField.getText(),
                passwordField.getText());
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert("Registration error", "Please provide mandatory fields - name, email and password", Alert.AlertType.ERROR);
        } if (!(passwordField.getText().equals(confirmPasswordField.getText()))) {
            showAlert("Registration error", "Please re-enter password and confirm password", Alert.AlertType.ERROR);
        } else if (isValidPassword(passwordField)) {
                    try {
                        userService.registerNewUser(user);
                        showAlert(null, "Registration successful! " + nameField.getText() + ", please login to activate your account.", Alert.AlertType.INFORMATION);

                        try {
                            changeScene(actionEvent, "login");
                        } catch (IOException exception){
                            exception.printStackTrace();
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                } else {
            showAlert("Registration error","Please enter a valid password: 8-20 characters, at least one number, symbol and uppercase character", Alert.AlertType.ERROR);
        }
            }


    public static boolean isValidPassword(PasswordField passwordField){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordField.getText());
        return matcher.matches();
    }
}

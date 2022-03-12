package com.privatefinancetracker.privatefinancetracker.controller;

import com.privatefinancetracker.privatefinancetracker.model.User;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;
import com.privatefinancetracker.privatefinancetracker.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileSettingsController extends ViewController implements Initializable {
    private UserService userService = new UserService();
    public Label nameLabel;
    public Label lastNameLabel;
    public Label dateOfBirthLabel;
    public Label emailLabel;
    public Label phoneNumberLabel;
    public Label locationLabel;
    public Label memberSinceLabel;
    public PasswordField newPasswordField;
    public PasswordField confirmNewPasswordField;
    public TextField newEmailField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DataManager.getInstance();
            Integer userId = DataManager.getLoggedInUserId();
            User user = this.userService.getUserProfile(userId);
            nameLabel.setText(user.getName());
            lastNameLabel.setText(user.getLastName());
            dateOfBirthLabel.setText(user.getDateOfBirth());
            emailLabel.setText(user.getEmail());
            phoneNumberLabel.setText(user.getPhone());
            locationLabel.setText(user.getLocation());
            memberSinceLabel.setText(user.getCreatedAt().toString());
        } catch (Exception exception){
            showAlert("Error - failed to load user information", exception.getMessage(), Alert.AlertType.ERROR);
            exception.printStackTrace();
        }

    }

    public void backToMainPage(ActionEvent actionEvent){
        try {
            changeScene(actionEvent, "mainpage");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleChangeEmail(){
        Integer userId = DataManager.getInstance().getLoggedInUserId();
        try {
            this.userService.changeEmail(userId, newEmailField.getText());
            showAlert(null, "Email changed successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            showAlert(null, "An error occurred, please try again", Alert.AlertType.ERROR);
        }
    }

    public void handleChangePassword(){
        if (newPasswordField.getText().equals(confirmNewPasswordField.getText()) && RegisterController.isValidPassword(newPasswordField)){
            Integer userId = DataManager.getInstance().getLoggedInUserId();
            try {
                this.userService.changePassword(userId, newPasswordField.getText());
                showAlert(null, "Password changed successfully!", Alert.AlertType.INFORMATION);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } else if (newPasswordField.getText().equals(confirmNewPasswordField.getText()) && (!RegisterController.isValidPassword(newPasswordField))) {
            showAlert(null,
                    "Password must contain 8-20 characters, at least one number, one symbol and one uppercase character, please try again",
                    Alert.AlertType.ERROR);
        } else {
            showAlert(null, "Password and confirm password fields do not match, please try again", Alert.AlertType.ERROR);
        }

    }

    public void handleDeleteAccount(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete your account?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                int userId = DataManager.getLoggedInUserId();
                User user = this.userService.getUserProfile(userId);
                userService.deleteUserProfile(user);
                showAlert(null, "Account deleted", Alert.AlertType.INFORMATION);
                changeScene(actionEvent, "login");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            alert.close();
        }

    }
}

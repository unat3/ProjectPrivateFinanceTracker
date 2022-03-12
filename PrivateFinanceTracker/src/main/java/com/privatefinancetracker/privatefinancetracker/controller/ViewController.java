package com.privatefinancetracker.privatefinancetracker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewController {

    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        String scenePath = "/view/" + sceneName + ".fxml";
        System.out.println(scenePath);
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(scenePath)));
        stage.setScene(new Scene(parent, 700, 700));
        stage.show();
    }

    public void showAlert(String title, String message, AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }


    }


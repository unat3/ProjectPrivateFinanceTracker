module com.privatefinancetracker.privatefinancetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.configuration;


    opens com.privatefinancetracker.privatefinancetracker to javafx.fxml;
    exports com.privatefinancetracker.privatefinancetracker;
    exports com.privatefinancetracker.privatefinancetracker.controller;
    opens com.privatefinancetracker.privatefinancetracker.controller to javafx.fxml;

    exports com.privatefinancetracker.privatefinancetracker.model;
    opens com.privatefinancetracker.privatefinancetracker.model to javafx.fxml;
}
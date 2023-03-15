module com.example.vacanta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.vacanta to javafx.fxml;
    exports com.example.vacanta;

    exports com.example.vacanta.controller;
    opens com.example.vacanta.controller to javafx.fxml;

    exports com.example.vacanta.domain;
    opens com.example.vacanta.domain to javafx.fxml;

    exports com.example.vacanta.repository;
    opens com.example.vacanta.repository to javafx.fxml;

    exports com.example.vacanta.service;
    opens com.example.vacanta.service to javafx.fxml;
}
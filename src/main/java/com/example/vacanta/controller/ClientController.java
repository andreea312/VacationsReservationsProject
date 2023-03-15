package com.example.vacanta.controller;

import com.example.vacanta.Main;
import com.example.vacanta.domain.Client;
import com.example.vacanta.domain.Hotel;
import com.example.vacanta.domain.Reservation;
import com.example.vacanta.domain.SpecialOffer;
import com.example.vacanta.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class ClientController {
    Service service;
    Client client;

    @FXML
    TableView<SpecialOffer> offersTable;

    @FXML
    TableColumn<SpecialOffer, String> locationNameColumn;
    @FXML
    TableColumn<SpecialOffer, String> hotelNameColumn;
    @FXML
    TableColumn<SpecialOffer, String> startDateColumn;

    @FXML
    TableColumn<SpecialOffer, String> endDateColumn;

    @FXML
    TableColumn<SpecialOffer, String> percentsColumn;

    @FXML
    Button vreauRezervareButton;

    @FXML
    ObservableList<SpecialOffer> offersForClient = FXCollections.observableArrayList();

    @FXML
    ObservableList<Reservation> reservationsObservable;

    public void setReservationsObservable(ObservableList<Reservation> reservationsObservable){
        this.reservationsObservable = reservationsObservable;
    }


    public void setClient(Client client){
        this.client = client;
    }
    public void setService(Service service){
        this.service = service;
        LocalDate now = LocalDate.now();
        Date azi = java.sql.Date.valueOf(now);
        offersForClient.setAll(service.getAllSpecialOffersForClient(azi, this.client));
        initializeaza();
    }
    public void initializeaza(){
        offersTable.getColumns().clear();

        locationNameColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<SpecialOffer, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<SpecialOffer, String> p) {
                        double hotelId = p.getValue().getHotelId();
                        double locationId = service.findHotelById(hotelId).getLocationId();
                        String locationName = service.findLocationById(locationId).getLocationName();
                        return new SimpleStringProperty(locationName);
                    }
                }
        );

        hotelNameColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<SpecialOffer, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<SpecialOffer, String> p) {
                        double hotelId = p.getValue().getHotelId();
                        String hotelName = service.findHotelById(hotelId).getHotelName();
                        return new SimpleStringProperty(hotelName);
                    }
                }
        );
        startDateColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, String>("endDate"));
        percentsColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, String>("percents"));

        offersTable.getColumns().add(locationNameColumn);
        offersTable.getColumns().add(hotelNameColumn);
        offersTable.getColumns().add(startDateColumn);
        offersTable.getColumns().add(endDateColumn);
        offersTable.getColumns().add(percentsColumn);

        offersTable.setItems(offersForClient);
    }

    public void clickVreauRezervareButton(){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("locations.fxml"));
        try {
            scene = new Scene(loader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LocationsController ctrl = loader.getController();
        ctrl.setClient(this.client);
        ctrl.setOffersForClient(this.offersForClient);
        ctrl.setReservationsObservable(this.reservationsObservable);
        ctrl.setService(service);

        stage.setTitle("Locations");
        stage.setScene(scene);
        stage.show();
    }

}

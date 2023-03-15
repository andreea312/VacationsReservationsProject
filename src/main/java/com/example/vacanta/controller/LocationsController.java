package com.example.vacanta.controller;

import com.example.vacanta.Main;
import com.example.vacanta.domain.*;
import com.example.vacanta.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationsController {
    Service service;

    Client client;

    @FXML
    ComboBox<String> selectLocation;

    @FXML
    TableView<Hotel> hotelsTable;
    @FXML
    TableColumn<Hotel, String> hotelNameColumn;
    @FXML
    TableColumn<Hotel, String> hotelNoRoomsColumn;
    @FXML
    TableColumn<Hotel, String> hotelPricePerNightColumn;
    @FXML
    TableColumn<Hotel, String> hotelTypeColumn;

    @FXML
    Button selectHotel;

    @FXML
    List<Location> locations = new ArrayList<>();

    @FXML
    ObservableList<Hotel> hotelsFromLocationObservable = FXCollections.observableArrayList();

    @FXML
    ObservableList<SpecialOffer> offersForClient;

    Location location = new Location(0, "");

    @FXML
    ObservableList<Reservation> reservationsObservable;

    public void setReservationsObservable(ObservableList<Reservation> reservationsObservable){
        this.reservationsObservable = reservationsObservable;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public void setOffersForClient(ObservableList<SpecialOffer> offersForClient){
        this.offersForClient = offersForClient;
    }

    public void setService(Service service){
        this.service = service;
        locations.addAll(service.getAllLocations());
        initializeaza();
    }
    public void initializeaza(){
        setComboBox();
        setTableView();
    }
    public void setComboBox(){
        selectLocation.getItems().clear();
        List<String> locationNames = new ArrayList<>();
        for(Location l: locations){
            locationNames.add(l.getLocationName());
        }
        selectLocation.getItems().addAll(locationNames);
    }
    public void setTableView(){
        hotelsTable.getColumns().clear();
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        hotelNoRoomsColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("noRooms"));
        hotelPricePerNightColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("pricePerNight"));
        hotelTypeColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("type"));

        hotelsTable.getColumns().add(hotelNameColumn);
        hotelsTable.getColumns().add(hotelNoRoomsColumn);
        hotelsTable.getColumns().add(hotelPricePerNightColumn);
        hotelsTable.getColumns().add(hotelTypeColumn);
    }
    public void clickSelectLocation(){
        String locationName = selectLocation.getValue();
        this.location = service.findLocationByName(locationName);
        hotelsFromLocationObservable.setAll(service.getAllHotelsFromLocation(this.location));
        hotelsTable.setItems(hotelsFromLocationObservable);
    }
    public void clickSelectHotel(){
        Hotel selectedHotel = (Hotel)hotelsTable.getSelectionModel().getSelectedItem();

        if(selectedHotel == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Niciun hotel selectat!");
            alert.setContentText("Va rog sa selectati un hotel din tabel!");
            alert.showAndWait();
        }
        else {
            Stage stage = new Stage();
            Scene scene;
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("offers.fxml"));
            try {
                scene = new Scene(loader.load(), 600, 400);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            OffersController ctrl = loader.getController();
            ctrl.setClient(this.client);
            ctrl.setOffersForClient(this.offersForClient);
            ctrl.setHotel(selectedHotel);
            ctrl.setReservationsObservable(this.reservationsObservable);
            ctrl.setService(this.service);

            stage.setTitle("Locations");
            stage.setScene(scene);
            stage.show();
        }
    }
}
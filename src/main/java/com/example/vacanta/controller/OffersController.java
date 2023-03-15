package com.example.vacanta.controller;

import com.example.vacanta.domain.Client;
import com.example.vacanta.domain.Hotel;
import com.example.vacanta.domain.Reservation;
import com.example.vacanta.domain.SpecialOffer;
import com.example.vacanta.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OffersController {
    Service service;
    Hotel hotel;

    Client client;

    Date startDate;
    Date endDate;

    @FXML
    DatePicker startDatePicker;

    @FXML
    DatePicker endDatePicker;

    @FXML
    TableView<SpecialOffer> specialOffersTable;

    @FXML
    TableColumn<SpecialOffer, String> startDateColumn;

    @FXML
    TableColumn<SpecialOffer, String> endDateColumn;

    @FXML
    TableColumn<SpecialOffer, String> percentsColumn;

    @FXML
    Button searchButton;

    @FXML
    Button rezervaButton;

    @FXML
    ObservableList<SpecialOffer> specialOffersForPeriodObservable = FXCollections.observableArrayList();

    @FXML
    ObservableList<SpecialOffer> offersForClient;

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

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public void setService(Service service) {
        this.service = service;
        initializeaza();
    }

    public void initializeaza(){
        setTableView();
    }

    public void setTableView(){
        specialOffersTable.getColumns().clear();
        startDateColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, String>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, String>("endDate"));
        percentsColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, String>("percents"));

        specialOffersTable.getColumns().add(startDateColumn);
        specialOffersTable.getColumns().add(endDateColumn);
        specialOffersTable.getColumns().add(percentsColumn);
    }

    public void clickStartDatePicker(){
        LocalDate start = startDatePicker.getValue();
        this.startDate = java.sql.Date.valueOf(start);
        System.out.println(startDate);
    }

    public void clickEndDatePicker(){
        LocalDate end = endDatePicker.getValue();
        this.endDate = java.sql.Date.valueOf(end);
        System.out.println(endDate);
    }

    public void clickSearchButton(){
        if(startDate == null || endDate == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty choice!");
            alert.setContentText("No data was selected!!!");
            alert.showAndWait();
        }
        else {
            Date aux;
            if (startDate.compareTo(endDate) > 0) {
                aux = startDate;
                startDate = endDate;
                endDate = aux;
            }
            specialOffersForPeriodObservable.setAll(service.getAllSpecialOffersForPeriod(this.hotel, startDate, endDate));
            specialOffersTable.setItems(specialOffersForPeriodObservable);
        }
    }

    public void clickRezervaButton(){
        SpecialOffer selected = (SpecialOffer) specialOffersTable.getSelectionModel().getSelectedItem();
        if(!offersForClient.contains(selected) ||
                !((this.startDate.compareTo(selected.getStartDate()) >= 0 && this.startDate.compareTo(selected.getEndDate()) <= 0)
                        && (this.endDate.compareTo(selected.getStartDate()) > 0 && this.endDate.compareTo(selected.getEndDate()) <= 0)
                )
           ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Oferta indisponibila!");
            alert.setContentText("Ne pare rau...Nu indepliniti cerintele corespunzatoare pentru a putea beneficia de aceasta oferta!!!");
            alert.showAndWait();
        }
        else {
            int noRooms = service.getNoRoomsForHotel(this.hotel);
            if(noRooms > 0) {
                long time_difference = this.endDate.getTime() - this.startDate.getTime();
                long days_difference = TimeUnit.MILLISECONDS.toDays(time_difference) % 365;
                int noNights = (int) days_difference;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Multumim pentru rezervare!");
                alert.setContentText("Detalii rezervare: \n clientId = " + this.client.getId() + ", " +
                        "\n hotelId = " + this.hotel.getId() + ", " +
                        "\n startDate = " + this.startDate + ", " +
                        "\n noNights = " + noNights);
                alert.showAndWait();
                LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.startDate.getTime()), ZoneId.systemDefault());
                System.out.println("hyyyyyyyy");
                Reservation reservation = new Reservation(service.generateIdReservation(), this.client.getId(), this.hotel.getId(), localDateTime, noNights);
                service.addReservation(reservation);
                this.reservationsObservable.add(reservation);
            }
            else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Camere insuficiente!");
                alerta.setContentText("Ne pare rau...Nu mai avem camere!!!");
                alerta.showAndWait();
            }
        }
    }
}

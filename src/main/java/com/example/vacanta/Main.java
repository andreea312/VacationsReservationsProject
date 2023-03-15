package com.example.vacanta;

import com.example.vacanta.controller.ClientController;
import com.example.vacanta.controller.LocationsController;
import com.example.vacanta.controller.OffersController;
import com.example.vacanta.domain.*;
import com.example.vacanta.repository.Repository;
import com.example.vacanta.repository.factory.Factory;
import com.example.vacanta.service.Service;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main extends Application {
    private static final Scanner scanner = new Scanner(System.in);

    public ObservableList<Reservation> reservationsObservable = FXCollections.observableArrayList();

    public int nr_rezervari = reservationsObservable.size();

    @Override
    public void start(Stage stage) throws IOException {
        Factory factory = new Factory();
        Repository<Double, Location> locationRepo = factory.createLocationRepository();
        Repository<Double, Hotel> hotelRepo = factory.createHotelRepository();
        Repository<Double, SpecialOffer> specialOfferRepo = factory.createSpecialOfferRepository();
        Repository<Long, Client> clientRepo = factory.createClientRepository();
        Repository<Double, Reservation> reservationRepo = factory.createReservationRepository();
        Service service = new Service(locationRepo, hotelRepo, specialOfferRepo, clientRepo, reservationRepo);

        ids.forEach(id->{
            if(service.findClientById(id) == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Client inexistent!");
                alert.setContentText("Nu s-a gasit client cu id-ul!" + id);
                alert.showAndWait();
            }
            else {
                Stage cstage = new Stage();
                Scene cscene;
                FXMLLoader cloader = new FXMLLoader(Main.class.getResource("client.fxml"));
                try {
                    cscene = new Scene(cloader.load(), 600, 400);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ClientController cctrl = cloader.getController();
                Client client = service.findClientById(id);

                cctrl.setClient(client);
                this.reservationsObservable.setAll(service.getAllReservations());
                cctrl.setReservationsObservable(this.reservationsObservable);
                cctrl.setService(service);

                cstage.setTitle("Client " + id);
                cstage.setScene(cscene);
                cstage.show();

                if(reservationsObservable.size() > nr_rezervari){
                    nr_rezervari = reservationsObservable.size();
                    List<Hobby> hobbies = service.getHobbiesFromReservations();
                    for(Hobby h: hobbies){
                        System.out.println(h);
                    }
                    if(hobbies.contains(client.getHobbies())){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText("Am gasit");
                        alert.setContentText("Mai exista clienti aici cu hobby-urile dvs!");
                        alert.showAndWait();
                    }
                }
            }
        });
    }

    private static String readLine(String helper) {
        System.out.print(helper);
        return scanner.nextLine();
    }

    static List<Long> ids = new ArrayList<>();

    public static void main(String[] args) {
        String clientsIds = readLine("Enter clients Ids: ");
        String[] arrayOfClientsIds = clientsIds.split(" ");
        for (String clientId : arrayOfClientsIds){
            long id = Long.parseLong(clientId);
            ids.add(id);
        }
        launch();
    }
}
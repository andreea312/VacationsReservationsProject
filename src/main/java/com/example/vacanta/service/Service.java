package com.example.vacanta.service;

import com.example.vacanta.domain.*;
import com.example.vacanta.repository.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    Repository<Double, Location> locationRepo;
    Repository<Double, Hotel> hotelRepo;
    Repository<Double, SpecialOffer> specialOfferRepo;
    Repository<Long, Client> clientRepo;
    Repository<Double, Reservation> reservationRepo;

    public Service(Repository<Double, Location> locationRepo,
                   Repository<Double, Hotel> hotelRepo,
                   Repository<Double, SpecialOffer> specialOfferRepo,
                   Repository<Long, Client> clientRepo,
                   Repository<Double, Reservation> reservationRepo){
        this.locationRepo = locationRepo;
        this.hotelRepo = hotelRepo;
        this.specialOfferRepo = specialOfferRepo;
        this.clientRepo = clientRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Location> getAllLocations(){
        return locationRepo.getAll();
    }
    public List<Hotel> getAllHotels(){
        return hotelRepo.getAll();
    }
    public List<SpecialOffer> getAllSpecialOffers(){
        return specialOfferRepo.getAll();
    }
    public List<Client> getAllClients(){
        return clientRepo.getAll();
    }

    public List<Reservation> getAllReservations(){
        return reservationRepo.getAll();
    }

    public double generateIdLocation(){
        double max = 0;
        for(Location location: getAllLocations()) {
            if(location.getId() > max)
                max = location.getId();
        }
        return max + 1;
    }

    public double generateIdHotel(){
        double max = 0;
        for(Hotel hotel: getAllHotels()) {
            if(hotel.getId() > max)
                max = hotel.getId();
        }
        return max + 1;
    }

    public long generateIdClient(){
        long max = 0;
        for(Client client: getAllClients()) {
            if(client.getId() > max)
                max = client.getId();
        }
        return max + 1;
    }

    public double generateIdReservation(){
        double max = 0;
        for(Reservation reservation: getAllReservations()) {
            if(reservation.getId() > max)
                max = reservation.getId();
        }
        return max + 1;
    }

    public void addReservation(Reservation reservation){
        reservationRepo.add(reservation);
    }

    public Location findLocationByName(String locationName){
        for(Location location: getAllLocations()){
            if(location.getLocationName().equals(locationName)){
                return location;
            }
        }
        return null;
    }

    public Client findClientById(Long id){
        for(Client client: getAllClients()){
            if(client.getId() == id){
                return client;
            }
        }
        return null;
    }

    public Hotel findHotelById(Double id){
        for(Hotel hotel: getAllHotels()){
            if(hotel.getId().equals(id)){
                return hotel;
            }
        }
        return null;
    }

    public Location findLocationById(Double id){
        for(Location location: getAllLocations()){
            if(location.getId().equals(id)){
                return location;
            }
        }
        return null;
    }

    public List<Hotel> getAllHotelsFromLocation(Location location){
        List<Hotel> hotels = new ArrayList<>();
        for(Hotel hotel: getAllHotels()){
            if(hotel.getLocationId() == location.getId()){
                hotels.add(hotel);
            }
        }
        return hotels;
    }
    public List<SpecialOffer> getAllSpecialOffersForPeriod(Hotel hotel, Date S, Date E){
        List<SpecialOffer> specialOffers = new ArrayList<>();
        for(SpecialOffer specialOffer: getAllSpecialOffers()){
            Date s = specialOffer.getStartDate();
            Date e = specialOffer.getEndDate();
            if(specialOffer.getHotelId() == hotel.getId()){
                if(s.compareTo(S)<0 && e.compareTo(S)>0 || s.compareTo(S)>0 && s.compareTo(E)<0){
                    specialOffers.add(specialOffer);
                }
            }
        }
        return specialOffers;
    }

    public List<SpecialOffer> getAllSpecialOffersForClient(Date now, Client client){
        List<SpecialOffer> specialOffers = new ArrayList<>();
        for(SpecialOffer specialOffer: getAllSpecialOffers()){
            Date s = specialOffer.getStartDate();
            Date e = specialOffer.getEndDate();
            if(specialOffer.getPercents() < client.getFidelityGrade()){
                if(e.compareTo(now)>0){
                    specialOffers.add(specialOffer);
                }
            }
        }
        return specialOffers;
    }

    public List<Hobby> getHobbiesFromReservations(){
        List<Hobby> hobbies = new ArrayList<>();
        for(Reservation r: getAllReservations()){
            Client client = findClientById(r.getClientId());
            hobbies.add(client.getHobbies());
        }
        return hobbies.stream().distinct().toList();
    }

    public int getNoRoomsForHotel(Hotel hotel){
        return hotel.getNoRooms();
    }
}

package com.example.vacanta.repository.factory;

import com.example.vacanta.repository.DBRepository.*;

public class Factory {
    private static Factory instance = new Factory();

    public LocationRepository createLocationRepository() {
        return new LocationRepository("jdbc:postgresql://localhost:5432/vacante", "postgres", "postgres");
    }
    public HotelRepository createHotelRepository() {
        return new HotelRepository("jdbc:postgresql://localhost:5432/vacante", "postgres", "postgres");
    }
    public SpecialOfferRepository createSpecialOfferRepository() {
        return new SpecialOfferRepository("jdbc:postgresql://localhost:5432/vacante", "postgres", "postgres");
    }
    public ClientRepository createClientRepository() {
        return new ClientRepository("jdbc:postgresql://localhost:5432/vacante", "postgres", "postgres");
    }

    public ReservationRepository createReservationRepository() {
        return new ReservationRepository("jdbc:postgresql://localhost:5432/vacante", "postgres", "postgres");
    }
}

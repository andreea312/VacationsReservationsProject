package com.example.vacanta.domain;

public class Location extends Entity<Double>{
    private String locationName;

    public Location(double locationId, String locationName) {
        super(locationId);
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId='" + super.getId() + '\'' +
                "locationName='" + locationName + '\'' +
                '}';
    }
}

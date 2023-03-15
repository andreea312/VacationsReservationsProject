package com.example.vacanta.domain;

import java.util.Date;

public class SpecialOffer extends Entity<Double>{
    private double hotelId;
    private Date startDate;
    private Date endDate;
    private int percents;


    public SpecialOffer(Double specialOfferId, double hotelId, Date startDate, Date endDate, int percents) {
        super(specialOfferId);
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;
    }

    public double getHotelId() {
        return hotelId;
    }

    public void setHotelId(double hotelId) {
        this.hotelId = hotelId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPercents() {
        return percents;
    }

    public void setPercents(int percents) {
        this.percents = percents;
    }

    @Override
    public String toString() {
        return "SpecialOffer{" +
                "specialOfferId=" + super.getId() +
                "hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", percents=" + percents +
                '}';
    }
}

package com.example.vacanta.repository.DBRepository;

import com.example.vacanta.domain.Reservation;
import com.example.vacanta.repository.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository implements Repository<Double, Reservation> {
    private String url;
    private String username;
    private String password;

    public ReservationRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservationss = new ArrayList<>();
        String sql = "SELECT * FROM \"reservations\"";
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                double reservationId = rs.getDouble("reservationid");
                long clientId = rs.getLong("clientid");
                double hotelId = rs.getDouble("hotelid");

                Date start = rs.getDate("startdate");
                LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(start.getTime()), ZoneId.systemDefault());

                int noNights = rs.getInt("nonights");

                Reservation reservation = new Reservation(reservationId, clientId, hotelId, startDate, noNights);
                reservationss.add(reservation);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return reservationss;
    }

    @Override
    public void add(Reservation reservation){
        String sql = "INSERT INTO reservations(reservationid, clientid, hotelid, startdate, nonights) VALUES(?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDouble(1, reservation.getId());
            ps.setLong(2, reservation.getClientId());
            ps.setDouble(3, reservation.getHotelId());
            ps.setTimestamp(4, Timestamp.valueOf(reservation.getStartDate()));
            ps.setInt(5, reservation.getNoNights());
            ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

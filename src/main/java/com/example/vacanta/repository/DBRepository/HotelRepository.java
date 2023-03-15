package com.example.vacanta.repository.DBRepository;

import com.example.vacanta.domain.Hotel;
import com.example.vacanta.domain.Type;
import com.example.vacanta.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository implements Repository<Double, Hotel> {
    private String url;
    private String username;
    private String password;

    public HotelRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<Hotel> getAll() {
        List<Hotel> hotelss = new ArrayList<>();
        String sql = "SELECT * FROM \"hotels\"";
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                double hotelId = rs.getDouble("hotelid");
                double locationId = rs.getDouble("locationid");
                String hotelName = rs.getString("hotelname");
                int noRooms = rs.getInt("norooms");
                double pricePerNight = rs.getDouble("pricepernight");
                Type type = Type.valueOf(rs.getString("type"));

                Hotel hotel = new Hotel(hotelId, locationId, hotelName, noRooms, pricePerNight, type);
                hotelss.add(hotel);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return hotelss;
    }

    @Override
    public void add(Hotel hotel){

    }
}


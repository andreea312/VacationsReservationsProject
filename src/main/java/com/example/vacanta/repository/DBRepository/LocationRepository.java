package com.example.vacanta.repository.DBRepository;

import com.example.vacanta.domain.Location;
import com.example.vacanta.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository implements Repository<Double, Location> {
    private String url;
    private String username;
    private String password;

    public LocationRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<Location> getAll() {
        List<Location> locationss = new ArrayList<>();
        String sql = "SELECT * FROM \"locations\"";
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                double locationId = rs.getDouble("locationid");
                String locationName = rs.getString("locationname");

                Location location = new Location(locationId, locationName);
                locationss.add(location);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return locationss;
    }

    @Override
    public void add(Location location){

    }
}

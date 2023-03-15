package com.example.vacanta.repository.DBRepository;

import com.example.vacanta.domain.SpecialOffer;
import com.example.vacanta.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialOfferRepository implements Repository<Double, SpecialOffer> {
    private String url;
    private String username;
    private String password;

    public SpecialOfferRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<SpecialOffer> getAll() {
        List<SpecialOffer> specialOfferss = new ArrayList<>();
        String sql = "SELECT * FROM \"specialoffers\"";
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                double specialOfferId = rs.getDouble("specialofferid");
                double hotelId = rs.getDouble("hotelid");
                Date startDate = rs.getDate("startdate");
                Date endDate = rs.getDate("enddate");
                int percents = rs.getInt("percents");

                SpecialOffer specialOffer = new SpecialOffer(specialOfferId, hotelId, startDate, endDate, percents);
                specialOfferss.add(specialOffer);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return specialOfferss;
    }

    @Override
    public void add(SpecialOffer specialOffer){
    }
}



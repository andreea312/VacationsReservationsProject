package com.example.vacanta.repository.DBRepository;

import com.example.vacanta.domain.Client;
import com.example.vacanta.domain.Hobby;
import com.example.vacanta.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements Repository<Long, Client> {
    private String url;
    private String username;
    private String password;

    public ClientRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<Client> getAll() {
        List<Client> clientss = new ArrayList<>();
        String sql = "SELECT * FROM \"clients\"";
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                long clientId = rs.getLong("clientid");
                String name = rs.getString("name");
                int fidelityGrade = rs.getInt("fidelitygrade");
                int varsta = rs.getInt("varsta");
                Hobby hobbies = Hobby.valueOf(rs.getString("hobbies"));

                Client client = new Client(clientId, name, fidelityGrade, varsta, hobbies);
                clientss.add(client);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return clientss;
    }

    @Override
    public void add(Client client){
    }
}


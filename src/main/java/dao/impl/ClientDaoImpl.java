package dao.impl;

import config.DbConnection;
import dao.ClientDao;
import entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    @Override
    public void deleteOne(int id){
        String sql = "DELETE FROM client WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    @Override
    public List<Client> findMany(){
        ArrayList<Client> clients = new ArrayList<>();

        String sql = "SELECT id, username FROM client";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                Integer id =rs.getObject("id",Integer.class);
                String username =  rs.getObject("username",String.class);
                clients.add(new Client(id,username));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return clients;
    }

    @Override
    public Integer insertOne(Client client){
        String sql = "INSERT INTO client(username,password) VALUES(?,?)";
        Integer id=null;
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1,client.getUsername());
            stmt.setString(2,client.getPassword());

            int records = stmt.executeUpdate();

            if (records>0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        id=rs.getInt(1);
                    }
                }
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }
        return id;
    }

    @Override
    public Client findOne(int id){

        Client client = null;

        String sql="SELECT id, username, password FROM client WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Integer clientId = rs.getObject("id",Integer.class);
                String username = rs.getObject("username",String.class);
                String password = rs.getObject("password",String.class);
                client = new Client(clientId,username,password);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return client;

    }
}

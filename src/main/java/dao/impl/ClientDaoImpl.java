package dao.impl;

import config.DbConnection;
import dao.ClientDao;
import entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    @Override
    public void deleteOne(int id) throws Exception{
        String sql = "DELETE FROM client WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }catch (Exception e){
            throw new Exception("Ocurrio un error");
        }

    }

    @Override
    public List<Client> findMany() throws Exception{
        ArrayList<Client> clients = new ArrayList<>();

        String sql = "SELECT id, dni, email, first_name, last_name FROM client";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                clients.add(new Client(rs.getObject("id",Integer.class),
                        rs.getObject("dni",String.class),
                        rs.getObject("firstName",String.class),
                        rs.getObject("lastName",String.class),
                        rs.getObject("email",String.class)));
            }
        }catch (Exception e){
            return Collections.emptyList();
        }

        return clients;
    }

    @Override
    public Integer insertOne(Client client) throws Exception{
        String sql = "INSERT INTO client(dni,password,first_name,last_name,email) VALUES(?,?,?,?,?)";
        Integer id=null;
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1,client.getDni());
            stmt.setString(2,client.getPassword());
            stmt.setString(3,client.getFirstName());
            stmt.setString(4,client.getLastName());
            stmt.setString(5,client.getEmail());

            int records = stmt.executeUpdate();

            if (records>0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        id=rs.getInt(1);
                    }
                }
            }

        }catch (Exception e){
            return null;
        }
        return id;
    }

    @Override
    public Client findOne(int id) throws Exception{

        Client client = null;

        String sql="SELECT dni,first_name,last_name,email FROM client WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                client = new Client(id,
                        rs.getObject("dni",String.class),
                        rs.getObject("first_name",String.class),
                        rs.getObject("last_name",String.class),
                        rs.getObject("email",String.class));
            }

        }catch (Exception e){
            return null;
        }
        return client;
    }

    @Override
    public Client findOne(String dni) throws Exception{
        Client client = null;
        String sql = "SELECT id,first_name,last_name,email FROM client WHERE dni = ?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                client = new Client(rs.getObject("id",Integer.class),
                        dni,
                        rs.getObject("first_name",String.class),
                        rs.getObject("last_name",String.class),
                        rs.getObject("email",String.class));
            }

        }catch (Exception e){
            return null;
        }
        return client;
    }

    @Override
    public Client findOneDb(String dni) throws Exception{

        Client client = null;

        String sql="SELECT id,password,first_name,last_name,email FROM client WHERE dni=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                client = new Client(rs.getObject("id",Integer.class),
                        dni,
                        rs.getObject("password",String.class),
                        rs.getObject("first_name",String.class),
                        rs.getObject("last_name",String.class),
                        rs.getObject("email",String.class));
            }
        }catch (Exception e){
            return null;
        }
        return client;
    }

}

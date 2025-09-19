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

        String sql = "SELECT id, dni FROM client";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                Integer id =rs.getObject("id",Integer.class);
                String dni =  rs.getObject("dni",String.class);
                String firstName = rs.getObject("firstName",String.class);
                String lastName = rs.getObject("lastName",String.class);
                clients.add(new Client(id,dni,firstName,lastName));
            }
        }catch (Exception e){
            return clients;
        }

        return clients;
    }

    @Override
    public Integer insertOne(Client client) throws Exception{
        String sql = "INSERT INTO client(dni,password,firstName,lastName) VALUES(?,?,?,?)";
        Integer id=null;
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1,client.getDni());
            stmt.setString(2,client.getPassword());
            stmt.setString(3,client.getFirstName());
            stmt.setString(4,client.getLastName());

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

        String sql="SELECT id, dni, firstName, lastName FROM client WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String dni = rs.getObject("dni",String.class);
                String firstName = rs.getObject("firstName",String.class);
                String lastName = rs.getObject("lastName",String.class);
                client = new Client((Integer) id,dni,firstName,lastName);
            }

        }catch (Exception e){
            return null;
        }

        return client;

    }

    @Override
    public Client findOne(String dni) throws Exception{
        Client client = null;
        String sql = "SELECT id,firstName,lastName FROM client WHERE dni = ?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Integer id=rs.getObject("id",Integer.class);
                String firstName = rs.getObject("firstName",String.class);
                String lastName = rs.getObject("lastName",String.class);
                client = new Client(id,dni,firstName,lastName);
            }

        }catch (Exception e){
            return null;
        }
        return client;
    }

    @Override
    public Client findOneDb(String dni) throws Exception{

        Client client = null;

        String sql="SELECT id, password, firstName, lastName FROM client WHERE dni=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Integer id = rs.getObject("id",Integer.class);
                String password = rs.getObject("password",String.class);
                String firstName = rs.getObject("firstName",String.class);
                String lastName = rs.getObject("lastName",String.class);
                client = new Client(id,dni,password,firstName,lastName);
            }

        }catch (Exception e){
            return null;
        }

        return client;
    }

}

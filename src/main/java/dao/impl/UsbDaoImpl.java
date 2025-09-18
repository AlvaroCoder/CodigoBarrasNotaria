package dao.impl;

import config.DbConnection;
import dao.UsbDao;
import entities.Usb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsbDaoImpl implements UsbDao {
    @Override
    public void deleteOne(int id){
        String sql = "DELETE FROM usb WHERE id=?";

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
    public List<Usb> findMany(){
        ArrayList<Usb> usbs = new ArrayList<>();

        String sql = "SELECT id, clientId, creationDate, lastModifiedDate FROM usb";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                String id =rs.getObject("id",String.class);
                Integer clientId =  rs.getObject("clientId",Integer.class);
                LocalDateTime creationDate=rs.getObject("creationDate",LocalDateTime.class);
                LocalDateTime lastModifiedDate = rs.getObject("lastModifiedDate",LocalDateTime.class);
                usbs.add(new Usb(id,clientId,creationDate,lastModifiedDate));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return usbs;
    }

    @Override
    public void insertOne(Usb usb){
        String sql = "INSERT INTO usb(id,clientId,creationDate, lastModifiedDate, pdfPassword) VALUES(?,?,?,?,?)";
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

        ) {
            stmt.setString(1,usb.getId());
            stmt.setInt(2,usb.getClientId());
            stmt.setTimestamp(3, Timestamp.valueOf(usb.getCreationDate()));
            stmt.setTimestamp(4,Timestamp.valueOf(usb.getLastModifiedDate()));
            stmt.setString(5,usb.getPdfPassword());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("USB guardado correctamente en la base de datos.");
            } else {
                System.out.println("No se insertó ningún registro.");
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }

    }

    @Override
    public Usb findOne(String id){

        Usb usb = null;

        String sql="SELECT id, clientId, creationDate, lastModifiedDate, pdfPassword FROM usb WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String usbId = rs.getObject("id",String.class);
                Integer clientId = rs.getObject("clientId",Integer.class);
                LocalDateTime creationDate = rs.getObject("creationDate",LocalDateTime.class);
                LocalDateTime lastModifiedDate = rs.getObject("lastModifiedDate",LocalDateTime.class);
                String pdfPassword = rs.getObject("pdfPassword",String.class);
                usb = new Usb(usbId,clientId,creationDate,lastModifiedDate,pdfPassword);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return usb;
    }

    @Override
    public List<Usb> findByIdClient(int idClient) {
        List<Usb> usbs = new ArrayList<>();
        String sql = "SELECT usb.id, c.username, usb.creationDate, usb.lastModifiedDate " +
                "FROM usb " +
                "INNER JOIN client c ON usb.clientId = c.id " +
                "WHERE c.id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String username = rs.getString("username");
                    LocalDateTime creationDate = rs.getTimestamp("creationDate").toLocalDateTime();
                    LocalDateTime lastModifiedDate = rs.getTimestamp("lastModifiedDate").toLocalDateTime();

                    usbs.add(new Usb(id, username, creationDate, lastModifiedDate));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en findByIdClient: " + e.getMessage());
            e.printStackTrace();
        }
        return usbs;
    }
}

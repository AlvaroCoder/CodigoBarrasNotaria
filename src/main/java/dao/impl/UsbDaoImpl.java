package dao.impl;

import config.DbConnection;
import dao.UsbDao;
import dto.UsbClientDto;
import entities.Usb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        String sql = "SELECT id, description, client_id, creation_date, last_modified_date,path FROM usb";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                Integer id =rs.getObject("id",Integer.class);
                String description=rs.getObject("description",String.class);
                Integer clientId =  rs.getObject("client_id",Integer.class);
                LocalDateTime creationDate=rs.getObject("creation_date",LocalDateTime.class);
                LocalDateTime lastModifiedDate = rs.getObject("last_modified_date",LocalDateTime.class);
                String path = rs.getObject("path",String.class);
                usbs.add(new Usb(id,description,clientId,creationDate,lastModifiedDate,path));
            }
        }catch (Exception e){
            return Collections.emptyList();
        }
        return usbs;
    }

    @Override
    public Integer insertOne(Usb usb){
        Integer id = null;
        String sql = "INSERT INTO usb(description,client_id,creation_date, last_modified_date, pdf_password,path) VALUES(?,?,?,?,?,?)";
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ) {
            stmt.setString(1,usb.getDescription());
            stmt.setInt(2,usb.getClientId());
            stmt.setTimestamp(3, Timestamp.valueOf(usb.getCreationDate()));
            stmt.setTimestamp(4,Timestamp.valueOf(usb.getLastModifiedDate()));
            stmt.setString(5,usb.getPdfPassword());
            stmt.setString(6,usb.getPath());

            int records = stmt.executeUpdate();

            if (records > 0) {
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if (rs.next()){
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
    public Usb findOne(int id){

        Usb usb = null;

        String sql="SELECT description,client_id, creation_date, last_modified_date, pdf_password,path FROM usb WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                usb = new Usb(id,
                        rs.getObject("description",String.class),
                        rs.getObject("client_id",Integer.class),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("last_modified_date").toLocalDateTime(),
                        rs.getObject("pdf_password",String.class),
                        rs.getObject("path",String.class));
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return usb;
    }

    @Override
    public List<UsbClientDto> findByIdClient(int clientId) {
        List<UsbClientDto> usbs = new ArrayList<>();
        String sql = "SELECT u.id, u.description, u.client_id, u.creation_date, u.last_modified_date, " +
                "u.pdf_password, u.path, c.dni, c.first_name, c.last_name, c.email FROM usb AS u, client AS c " +
                "WHERE u.client_id=c.id AND c.id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Integer usbId = rs.getInt("id");
                    String description = rs.getString("description");
                    String path = rs.getString("path");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email= rs.getString("email");
                    String dni = rs.getString("dni");
                    LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
                    LocalDateTime lastModifiedDate = rs.getTimestamp("last_modified_date").toLocalDateTime();

                    usbs.add(new UsbClientDto(usbId,description, creationDate, lastModifiedDate,clientId,dni,email,firstName,lastName,path));
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return Collections.emptyList();
        }
        return usbs;
    }
}

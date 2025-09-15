package dao.impl;

import config.DbConnection;
import dao.RecordDao;
import entities.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    @Override
    public void deleteOne(int id){
        String sql = "DELETE FROM records WHERE id=?";

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
    public List<Record> findMany(){

        ArrayList<Record> records = new ArrayList<>();

        String sql = "SELECT id, name, description, clientId, usbId FROM client";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){

                String id =rs.getObject("id",String.class);
                String name =  rs.getObject("name",String.class);
                String description = rs.getObject("description",String.class);
                Integer clientId = rs.getObject("clientId",Integer.class);
                String usbId= rs.getObject("usbId",String.class);
                records.add(new Record(id,name,description,clientId,usbId));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return records;
    }

    @Override
    public void insertOne(Record record){
        String sql = "INSERT INTO records(id,name,description,clientId,usbId) VALUES(?,?,?,?,?)";
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1,record.getId());
            stmt.setString(2,record.getName());
            stmt.setString(3,record.getDescription());
            stmt.setInt(4,record.getClientId());
            stmt.setString(5,record.getUsbId());

        }catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.toString());
        }
    }

    @Override
    public Record findOne(int id){

        Record record = null;

        String sql="SELECT id, name, description, clientId, usbId FROM records WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String recordId = rs.getObject("id",String.class);
                String name = rs.getObject("name",String.class);
                String description = rs.getObject("description",String.class);
                Integer clientId = rs.getObject("clientId",Integer.class);
                String usbId = rs.getObject("usbId",String.class);
                record = new Record(recordId,name,description, clientId, usbId);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return record;

    }
}

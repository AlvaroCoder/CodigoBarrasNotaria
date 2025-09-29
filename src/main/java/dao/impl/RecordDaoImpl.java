package dao.impl;
import config.DbConnection;
import dao.RecordDao;
import entities.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    @Override
    public Integer insertOne(Record record){
        String sql = "INSERT record(name,description,usb_id,creation_date,last_modified_date,path) VALUES(?,?,?,?,?,?)";
        Integer id = null;
        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1,record.getName());
            stmt.setString(2,record.getDescription());
            stmt.setInt(3,record.getUsbId());
            stmt.setTimestamp(4, Timestamp.valueOf(record.getCreationDate()));
            stmt.setTimestamp(5,Timestamp.valueOf(record.getLastModifiedDate()));
            stmt.setString(6,record.getPath());
            int records = stmt.executeUpdate();
            if (records>0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if (rs.next()){
                        id = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return id;
    }

    @Override
    public Record findOne(int id){
        String sql = "SELECT name,description,usb_id,creation_date,last_modified_date,path FROM record WHERE id=?";
        Record record = null;
        try(Connection conn = DbConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                record = new Record(id,
                        rs.getObject("name",String.class),
                        rs.getObject("description",String.class),
                        rs.getObject("usb_id",Integer.class),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("last_modified_date").toLocalDateTime(),
                        rs.getObject("path",String.class));
            }
        } catch (Exception e){
            return null;
        }
        return record;
    }

    @Override
    public List<Record> findMany(int usbId){
        ArrayList<Record> records = new ArrayList<>();
        String sql ="SELECT id,name,description,creation_date,last_modified_date,path FROM record WHERE usb_id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,usbId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                records.add(new Record(rs.getObject("id",Integer.class),
                        rs.getObject("name",String.class),
                        rs.getObject("description",String.class),
                        usbId,
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("last_modified_date").toLocalDateTime(),
                        rs.getObject("path",String.class)));
            }
        } catch (Exception e){
            return Collections.emptyList();
        }
        return records;
    }





}

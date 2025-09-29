package dao.impl;

import config.DbConnection;
import dao.PageDao;
import entities.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageDaoImpl implements PageDao {
    @Override
    public void deleteOne(int id) throws Exception{
        String sql = "DELETE FROM page WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }catch (Exception e){
            throw new Exception("Hubo un error para eliminar la pagina.");
        }

    }

    @Override
    public List<Page> findMany(int sectionId){
        ArrayList<Page> clients = new ArrayList<>();

        String sql = "SELECT id, serial_number, path FROM page WHERE section_id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,sectionId);
            ResultSet rs =stmt.executeQuery();

            while (rs.next()){
                clients.add(new Page(rs.getObject("id",Integer.class),
                        rs.getObject("serial_number",String.class),
                        sectionId,
                        rs.getObject("path",String.class)));
            }
        }catch (Exception e){
            return Collections.emptyList();
        }
        return clients;
    }

    @Override
    public String insertOne(Page page){
        String sql = "INSERT INTO page(serial_number,section_id,path) VALUES(?,?,?)";
        String id=null;
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1,page.getSerialNumber());
            stmt.setInt(2,page.getSectionId());
            stmt.setString(3,page.getPath());
            int records = stmt.executeUpdate();
            if (records>0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        id=rs.getString(1);
                    }
                }
            }
        }catch (Exception e){
            return null;
        }
        return id;
    }

    @Override
    public Page findOne(String serialNumber){

        Page page = null;

        String sql="SELECT id, section_id, path FROM page WHERE serial_number=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,serialNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                page = new Page(rs.getObject("id",Integer.class),
                        serialNumber,
                        rs.getObject("section_id",Integer.class),
                        rs.getObject("path",String.class));
            }

        }catch (Exception e){
            return null;
        }
        return page;

    }
}

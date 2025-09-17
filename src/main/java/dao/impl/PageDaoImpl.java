package dao.impl;

import config.DbConnection;
import dao.PageDao;
import entities.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PageDaoImpl implements PageDao {
    @Override
    public void deleteOne(int id){
        String sql = "DELETE FROM pages WHERE id=?";

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
    public List<Page> findMany(){
        ArrayList<Page> clients = new ArrayList<>();

        String sql = "SELECT id, serialNumber, recordId, path FROM pages";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                String id =rs.getObject("id",String.class);
                String serialNumber =  rs.getObject("serialNumber",String.class);
                String recordId=rs.getObject("recordId",String.class);
                String path = rs.getObject("path",String.class);
                clients.add(new Page(id,serialNumber,recordId,path));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return clients;
    }

    @Override
    public String insertOne(Page page){
        String sql = "INSERT INTO pages(id,serialNumber,recordId,path) VALUES(?,?,?,?)";
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1,page.getId());
            stmt.setString(2,page.getSerialNumber());
            stmt.setString(3,page.getRecordId());
            stmt.setString(4,page.getPath());

            int records = stmt.executeUpdate();

        }catch (Exception e){
            System.out.println(e.toString());
        }
        return "";
    }

    @Override
    public Page findOne(String id){

        Page page = null;

        String sql="SELECT id, serialNumber, recordId, path FROM pages WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String pageId = rs.getObject("id",String.class);
                String serialNumber = rs.getObject("serialNumber",String.class);
                String recordId = rs.getObject("recordId",String.class);
                String path = rs.getObject("path",String.class);
                page = new Page(pageId,serialNumber,recordId,path);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return page;

    }
}

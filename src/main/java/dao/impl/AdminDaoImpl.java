package dao.impl;

import config.DbConnection;
import dao.AdminDao;
import entities.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    @Override
    public void deleteOne(int id){
        String sql = "DELETE FROM admin WHERE id=?";

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
    public List<Admin> findMany(){
        ArrayList<Admin> admins = new ArrayList<>();

        String sql = "SELECT id,username,email FROM admin";

        try(Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while (rs.next()){
                admins.add(new Admin(rs.getObject("id",Integer.class),
                        rs.getObject("username",String.class),
                        rs.getObject("email",String.class)));
            }
        }catch (Exception e){
            return Collections.emptyList();
        }
        return admins;
    }

    @Override
    public Integer insertOne(Admin admin){
        String sql = "INSERT INTO admin(username,password,email) VALUES(?,?,?)";
        Integer id = null;
        try(Connection conn= DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1,admin.getUsername());
            stmt.setString(2,admin.getPassword());
            stmt.setString(3,admin.getEmail());

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
    public Admin findOne(int id){

        Admin admin = null;

        String sql="SELECT username,email FROM admin WHERE id=?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String username = rs.getObject("username",String.class);
                String password = rs.getObject("email",String.class);
                admin = new Admin(id,username,password);
            }

        }catch (Exception e){
            return null;
        }
        return admin;
    }

    @Override
    public Admin findOne(String username) throws Exception{
        Admin admin = null;
        String sql = "SELECT id, email FROM admin WHERE username = ?";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                admin = new Admin(rs.getObject("id",Integer.class),
                        username,
                        rs.getObject("email",String.class));
            }

        } catch (Exception e){
            return null;
        }

        return admin;
    }

    @Override
    public Admin findOneDb(String username) throws Exception{
        Admin admin = null;
        String sql = "SELECT id, password, email FROM admin WHERE username = ?";
        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt =  conn.prepareStatement(sql);
        ){
            stmt.setString(1,username);
            ResultSet rs=stmt.executeQuery();

            if (rs.next()){
                admin = new Admin(rs.getObject("id",Integer.class),
                        username,
                        rs.getObject("password",String.class),
                        rs.getObject("email",String.class));
            } else{
                return null;
            }
        }
        return admin;
    }
}

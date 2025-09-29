package dao.impl;
import config.DbConnection;
import dao.SectionDao;
import entities.Section;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectionDaoImpl implements SectionDao {
    @Override
    public Integer insertOne(Section section){
        Integer id = null;
        String sql = "INSERT section(name,record_id,creation_date,last_modified_date,path) VALUES(?,?,?,?,?)";

        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1,section.getName());
            stmt.setInt(2,section.getRecordId());
            stmt.setTimestamp(3, Timestamp.valueOf(section.getCreationDate()));
            stmt.setTimestamp(4,Timestamp.valueOf(section.getLastModifiedDate()));
            stmt.setString(5,section.getPath());
            int results = stmt.executeUpdate();
            if (results>0){
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
    public Section findOne(int id){
        String sql = "SELECT name,file_id,creation_date,last_modified_date,path FROM section WHERE id=?";
        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1,id);
            ResultSet rs =stmt.executeQuery();
            return new Section(id,
                    rs.getObject("name",String.class),
                    rs.getObject("file_id",Integer.class),
                    rs.getTimestamp("creation_date").toLocalDateTime(),
                    rs.getTimestamp("last_modified_date").toLocalDateTime(),
                    rs.getObject("path",String.class));
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Section> findMany(){
        ArrayList<Section> sections = new ArrayList<>();
        String sql = "SELECT id,name,file_id,creation_date,last_modified_date,path FROM section";
        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                sections.add(new Section(rs.getObject("id",Integer.class),
                        rs.getObject("name",String.class),
                        rs.getObject("file_id",Integer.class),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("last_modified_date").toLocalDateTime(),
                        rs.getObject("path",String.class)));
            }
        } catch (Exception e){
            return Collections.emptyList();
        }
        return sections;
    }

    @Override
    public void deleteOne(int id) throws Exception{
        String sql = "DELETE FROM section WHERE id = ?";
        try(Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.executeUpdate();
        } catch (Exception e){
            throw new Exception("Ocurrio un error eliminando la seccion");
        }
    }


}

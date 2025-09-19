package dao;

import entities.Admin;

import java.util.List;

public interface AdminDao {
    //POST
    Integer insertOne(Admin admin);
    //GET
    Admin findOne(int id);

    Admin findOne(String username) throws Exception;

    Admin findOneDb(String username) throws Exception;

    List<Admin> findMany();
    //DELETE
    void deleteOne(int id);
}

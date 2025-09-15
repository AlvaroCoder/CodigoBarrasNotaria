package dao;

import entities.Admin;

import java.util.List;

public interface AdminDao {
    //POST
    Integer insertOne(Admin admin);
    //GET
    Admin findOne(int id);
    List<Admin> findMany();
    //DELETE
    void deleteOne(int id);
}

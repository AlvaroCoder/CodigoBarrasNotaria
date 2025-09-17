package dao;

import entities.Page;

import java.util.List;

public interface PageDao {
    //POST
    String insertOne(Page page);
    //GET
    Page findOne(String id);
    List<Page> findMany();
    //DELETE
    void deleteOne(int id);
}

package dao;

import entities.Page;

import java.util.List;

public interface PageDao {
    //POST
    String insertOne(Page page);
    //GET
    Page findOne(String serialNumber);
    List<Page> findMany(int sectionId);//DELETE
    void deleteOne(int id) throws Exception;
}

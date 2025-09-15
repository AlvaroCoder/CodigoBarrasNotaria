package dao;

import entities.Record;

import java.util.List;

public interface RecordDao {
    //POST
    void insertOne(Record record);
    //GET
    Record findOne(int id);
    List<Record> findMany();
    //DELETE
    void deleteOne(int id);
}

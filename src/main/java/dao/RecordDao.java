package dao;

import entities.Record;

import java.util.List;

public interface RecordDao {
    Integer insertOne(Record record);
    Record findOne(int id);
    List<Record> findMany(int usbId);
}

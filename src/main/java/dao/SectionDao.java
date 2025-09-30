package dao;

import entities.Section;

import java.util.List;

public interface SectionDao {
    Integer insertOne(Section section);
    Section findOne(int id);
    List<Section> findMany();
    List<Section> findMany(int recordId);
    void deleteOne(int id) throws Exception;
}

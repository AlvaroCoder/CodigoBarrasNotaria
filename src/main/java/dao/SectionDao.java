package dao;

import entities.Section;

import java.util.List;

public interface SectionDao {
    Integer insertOne(Section section);
    Section findOne(int id);
    List<Section> findMany();
    void deleteOne(int id) throws Exception;
}

package dao;
import entities.Client;

import java.util.List;

public interface ClientDao {
    //POST
    Integer insertOne(Client client);
    //GET
    Client findOne(int id);
    List<Client> findMany();
    //DELETE
    void deleteOne(int id);
}

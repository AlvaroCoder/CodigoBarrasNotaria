package dao;
import entities.Client;

import java.util.List;

public interface ClientDao {
    //POST
    Integer insertOne(Client client) throws Exception;
    //GET
    Client findOne(int id) throws Exception;

    Client findOne(String dni) throws Exception;

    Client findOneDb(String dni) throws Exception;

    List<Client> findMany() throws Exception;
    //DELETE
    void deleteOne(int id) throws Exception;
}

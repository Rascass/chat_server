package dao;

import model.Client;

import java.util.List;

public interface ClientDao {

    void create(Client client);
    Client getById(int id);
    List<Client> get();
    void update(Client client);
    void delete(int id);
}

package com.solvd.automation.lab.fall.dao;

import com.solvd.automation.lab.fall.model.Client;
import com.solvd.automation.lab.fall.model.message.LogInMessage;

import java.util.List;

public interface ClientDao {

    void create(Client client);
    Client getById(int id);
    List<Client> get();
    void update(Client client);
    void delete(int id);
    int getLastClientId();
    Client getByLoginAndHash(LogInMessage logInMessage);
}

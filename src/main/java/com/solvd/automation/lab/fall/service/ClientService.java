package com.solvd.automation.lab.fall.service;

import com.solvd.automation.lab.fall.dao.ClientDao;
import com.solvd.automation.lab.fall.dao.impl.ClientDaoImpl;
import com.solvd.automation.lab.fall.model.Client;
import com.solvd.automation.lab.fall.model.message.LogInMessage;
import com.solvd.automation.lab.fall.model.message.SearchMessage;

import java.util.List;

public class ClientService {

    ClientDao clientDao = new ClientDaoImpl();

    public Client getClientById(int id) {
        return clientDao.getById(id);
    }

    public List<Client> getAllClients() {
        return clientDao.get();
    }

    public void createClient(Client client) {
        clientDao.create(client);
    }

    public void deleteClientById(int id) {
        clientDao.delete(id);
    }

    public void updateClient(Client client) {
        clientDao.update(client);
    }

    public int getLastClientId() {
        return clientDao.getLastClientId();
    }

    public Client getClientByLoginAndHash(LogInMessage logInMessage) {
        return clientDao.getByLoginAndHash(logInMessage);
    }

    public Client getClientByLogin(String login) {
        return clientDao.getByLogin(login);
    }
}

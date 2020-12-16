package service;

import dao.ClientDao;
import dao.impl.ClientDaoImpl;
import model.Client;

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
}

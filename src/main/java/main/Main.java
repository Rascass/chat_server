package main;

import dao.impl.ClientDaoImpl;
import dao.impl.SessionDaoImpl;
import model.Client;
import model.Session;
import service.ClientService;
import service.SessionService;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ClientDaoImpl clientDaoImpl = new ClientDaoImpl();
        int clientCounter = clientDaoImpl.getMax();
        Client.setCounter(clientCounter);
        SessionDaoImpl sessionDaoImpl = new SessionDaoImpl();
        int sessionCounter = sessionDaoImpl.getMax();
        Session.setCounter(sessionCounter);
        System.out.println(clientCounter);
        System.out.println(sessionCounter);
        Client client = new Client(123, "sdf", "12332", null);
        Session session = new Session(null, 6666, true, "45.0.2.0",
                "localhost",1, null);
        session.setClients(Arrays.asList(client));
        ClientService clientService = new ClientService();
        clientService.createClient(client);
        SessionService sessionService = new SessionService();
        sessionService.createSession(session);
    }
}

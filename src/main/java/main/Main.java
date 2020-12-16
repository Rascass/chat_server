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
        int clientCounter = clientDaoImpl.getLastClient();
        Client.setCounter(clientCounter);
        SessionDaoImpl sessionDaoImpl = new SessionDaoImpl();
        int sessionCounter = sessionDaoImpl.getLastSessionId();
        Session.setCounter(sessionCounter);
        Session session = new Session(null, 6666, true, "45.0.2.0",
                "localhost",1, null);
        Client client = new Client(123, "sdf", "12332", null);
        Client client2 = new Client(132, "jack333", "030303", null);
        Client client3 = new Client(111, "rix1", "12345", null);
        SessionService sessionService = new SessionService();
        ClientService clientService = new ClientService();
        for (Session s: sessionService.getAllSessions()) {
            System.out.println(s);
        }
        for (Client c: clientService.getAllClients()) {
            System.out.println(c);
        }
        clientService.createClient(client);
        clientService.createClient(client2);
        clientService.createClient(client3);
        session.setClients(Arrays.asList(client, client2));
        sessionService.createSession(session);
        for (Session s: sessionService.getAllSessions()) {
            System.out.println(s);
        }
        for (Client c: clientService.getAllClients()) {
            System.out.println(c);
        }
        sessionService.updateSession(session);
        client.setSessionList(Arrays.asList(session));
        clientService.updateClient(client);
    }
}

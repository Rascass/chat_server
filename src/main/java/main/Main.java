package main;

import model.Client;
import util.ConnectionUtil;

public class Main {
    public static void main(String[] args) {
        ConnectionUtil clientUtil = new ConnectionUtil("127.0.0.1", 8000,
                new Client(0, "sdf", "12332", null));
        clientUtil.createConnection();

        /*
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

         */
    }
}

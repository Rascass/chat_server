package main;




import constant.ServerConstant;
import model.*;
import service.ClientService;
import service.SessionService;
import util.LogInParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(ServerConstant.PORT);
             Phone phone = new Phone(serverSocket)) {
            while (true) {
                new Thread(()->{
                    Client currentClient = findClient(phone.readLine());
                    Session session = createSession("", currentClient);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static Client findClient(String request) {
        Client currentClient;
        ClientService clientService = new ClientService();
        int token = (1 + (int) (Math.random() * 100000));

        for (Client c: clientService.getAllClients()) {
            if (request.contains(c.getLogin()) && request.contains(c.getPassword() + "")) {
                currentClient = c;
                currentClient.setClientToken(token);
                clientService.updateClient(currentClient);
                return currentClient;
            }
        }
        currentClient = new Client(token, LogInParser.parseLogin(request), LogInParser.parsePassword(request), null);
        clientService.createClient(currentClient);
        return currentClient;
    }
/*
    public static Session updateSession(Client client, Session session){
        SessionService sessionService = new SessionService();
        List<Client> clients = new ArrayList<>();
        for (Session s: sessionService.getAllSessions()) {
            if (s.equals(session)) {
                return session;
            }
        }
        clients = session.getClients();
        clients.add(client);
        session.setClients(clients);
        sessionService.createSession(session);
        return session;
    }

 */

    public static Session createSession(String host, Client client) {
        int token = (1 + (int) (Math.random() * 100000));
        SessionService sessionService = new SessionService();
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        Session session = new Session(new Date(), ServerConstant.PORT, true, ServerConstant.IP,
                host, token, clients);
        sessionService.createSession(session);
        return session;
    }

}

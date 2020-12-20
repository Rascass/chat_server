package com.solvd.automation.lab.fall.main;

import com.solvd.automation.lab.fall.constant.ServerConstant;
import com.solvd.automation.lab.fall.listener.Listener;
import com.solvd.automation.lab.fall.model.*;
import com.solvd.automation.lab.fall.model.message.LogInMessage;
import com.solvd.automation.lab.fall.model.message.Response;
import com.solvd.automation.lab.fall.service.ClientService;
import com.solvd.automation.lab.fall.service.SessionService;
import com.solvd.automation.lab.fall.util.LogInParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    public static Client currentClient;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(ServerConstant.PORT)) {
            while (true) {
                SocketConnector socketConnector = new SocketConnector(serverSocket);
                new Thread(()->{
                    String request = socketConnector.readLine();
                    Response response = Listener.getResponse(request);
                    socketConnector.writeLine(response.toString());
                    response = createSession("", currentClient);
                    socketConnector.writeLine(response.toString());
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static Response findClient(String request) {
        ClientService clientService = new ClientService();
        LogInMessage logInMessage = new LogInParser().parse(request);
        int token = (1 + (int) (Math.random() * 100000));
            if (clientService.getClientByLoginAndHash(logInMessage) != null) {
                authenticate(logInMessage, token);
                return new Response(0,"authentication and token set is successful");
            } else {
                return new Response(1,"client was not found");
            }
    }

    public static void authenticate(LogInMessage logInMessage, int token) {
        ClientService clientService = new ClientService();
        currentClient = clientService.getClientByLoginAndHash(logInMessage);
        currentClient.setClientToken(token);
        clientService.updateClient(currentClient);
    }

    public static void authorizate(int token, String login, int passwordHash) {
        ClientService clientService = new ClientService();
        int clientCounter = clientService.getLastClientId();
        Client.setCounter(clientCounter);
        currentClient = new Client(token, login, passwordHash, null);
        clientService.createClient(currentClient);
    }

    public static Response createSession(String host, Client client) {
        int token = (1 + (int) (Math.random() * 100000));
        SessionService sessionService = new SessionService();
        ClientService clientService = new ClientService();
        int sessionCounter = sessionService.getLastSessionId();
        int clientCounter = clientService.getLastClientId();
        System.out.println(sessionCounter);
        System.out.println(clientCounter);
        Session.setCounter(sessionCounter);
        Client.setCounter(clientCounter);
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        Session session = new Session(new Date(), ServerConstant.PORT, true, ServerConstant.IP,
                host, token, clients);
        sessionService.createSession(session);
        return new Response(0,"session established");
    }
}

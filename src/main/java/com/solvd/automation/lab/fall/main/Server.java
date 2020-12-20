package com.solvd.automation.lab.fall.main;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;
import com.solvd.automation.lab.fall.listener.Listener;
import com.solvd.automation.lab.fall.model.*;
import com.solvd.automation.lab.fall.model.message.LogInMessage;
import com.solvd.automation.lab.fall.model.message.Response;
import com.solvd.automation.lab.fall.model.message.SearchMessage;
import com.solvd.automation.lab.fall.service.ClientService;
import com.solvd.automation.lab.fall.service.SessionService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    public static Client currentClient;
    public static void main(String[] args) {
        int port = Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.PORT_KEY));
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                SocketConnector socketConnector = new SocketConnector(serverSocket);
                new Thread(()->{
                    String request = socketConnector.readLine();
                    Response response = Listener.getResponse(request);
                    socketConnector.writeLine(response.toString());
                    createSession("", currentClient, port);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static Response authenticate(LogInMessage logInMessage) {
        int token = (1 + (int) (Math.random() * 100000));
        ClientService clientService = new ClientService();
        if (clientService.getClientByLoginAndHash(logInMessage) == null) {
            return new Response(2,"client was not found");
        }
        currentClient = clientService.getClientByLoginAndHash(logInMessage);
        currentClient.setClientToken(token);
        clientService.updateClient(currentClient);
        return new Response(0,"authentication and token set is successful");
    }

    public static void registration(int token, String login, int passwordHash) {
        ClientService clientService = new ClientService();
        int clientCounter = clientService.getLastClientId();
        Client.setCounter(clientCounter);
        currentClient = new Client(token, login, passwordHash, null);
        clientService.createClient(currentClient);
    }

    public static void createSession(String host, Client client, int port) {
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
        String ip = PropertyReader.getInstance().getValue(PropertyConstant.IP_KEY);
        Session session = new Session(new Date(), port, true, ip,
                host, token, clients);
        sessionService.createSession(session);
    }
}

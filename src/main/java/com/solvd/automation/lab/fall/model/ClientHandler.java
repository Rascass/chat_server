package com.solvd.automation.lab.fall.model;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.constant.TimeConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;
import com.solvd.automation.lab.fall.listener.Listener;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.message.*;
import com.solvd.automation.lab.fall.service.ClientService;
import com.solvd.automation.lab.fall.service.SessionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientHandler implements Runnable {
    private static Client currentClient;
    private SocketConnector socketConnector;
    private volatile Date tokenDate;
    private static ClientHandler instance;

    public ClientHandler(SocketConnector socketConnector) {
        this.socketConnector = socketConnector;
    }

    @Override
    public void run() {
        String request = socketConnector.readLine();
        System.out.println(request);
        IMessage response = Listener.getResponse(request);
        socketConnector.writeLine(response.toString());
    }

    public LogInResponse authenticate(LogInMessage logInMessage) {
        int token = (1 + (int) (Math.random() * 100000));
        ClientService clientService = new ClientService();
        if (clientService.getClientByLogin(logInMessage.getLogin()) == null) {
            return new LogInResponse(2,"client was not found");
        }
        currentClient = clientService.getClientByLogin(logInMessage.getLogin());
        if (currentClient.getPasswordHash() != logInMessage.getPasswordHash()) {
            return new LogInResponse(1,"wrong password");
        }
        currentClient.setClientToken(token);
        currentClient.setClientIp(socketConnector.getIp());
        currentClient.setLastLogin(new Date());
        System.out.println(currentClient.getLastLogin());
        clientService.updateClient(currentClient);
        System.out.println(clientService.getClientByLogin(logInMessage.getLogin()).getLastLogin());
        return new LogInResponse(0,"authentication and token set is successful");
    }

    public LogInResponse registration(RegistrationMessage registrationMessage) {
        int token = (1 + (int) (Math.random() * 100000));
        ClientService clientService = new ClientService();
        if (clientService.getClientByLogin(registrationMessage.getLogin()) != null) {
            return new LogInResponse(1,"client already exists!");
        }
        int clientCounter = clientService.getLastClientId();
        Client.setCounter(clientCounter);
        currentClient = new Client(socketConnector.getIp(), new Date(), token, registrationMessage.getLogin(), registrationMessage.getRegPassword(), null);
        clientService.createClient(currentClient);
        return new LogInResponse(0, "successful registration");
    }

    public void createSession(String host, Client client, int port) {
        int token = (1 + (int) (Math.random() * 100000));
        SessionService sessionService = new SessionService();
        ClientService clientService = new ClientService();
        int sessionCounter = sessionService.getLastSessionId();
        int clientCounter = clientService.getLastClientId();
        Session.setCounter(sessionCounter);
        Client.setCounter(clientCounter);
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        String ip = PropertyReader.getInstance().getValue(PropertyConstant.IP_KEY);
        Session session = new Session(new Date(), port, true, ip,
                host, token, clients);
        sessionService.createSession(session);
    }

    public SearchResponse findClient (SearchMessage searchMessage) {
        System.out.println(searchMessage.getSearchLogin());
        ClientService clientService = new ClientService();
        currentClient = clientService.getClientByLogin(searchMessage.getSearchLogin());
        System.out.println(currentClient.getLastLogin());
        if ((new Date().getTime() - currentClient.getLastLogin().getTime()) > (TimeConstant.LIFETIME)) {
            return new SearchResponse(3, "you are lodged out");
        }
        if (clientService.getClientByLogin(searchMessage.getSearchLogin()) == null) {
            return new SearchResponse(2,"can't find user with such login");
        }
        currentClient = clientService.getClientByLogin(searchMessage.getSearchLogin());
        if (currentClient.getClientToken() == 0) {
            return new SearchResponse(1,"user offline");
        }
        System.out.println(new SearchResponse(0, "" + currentClient.getClientIp()).toString());
        return new SearchResponse(0, "" + currentClient.getClientIp());
    }

    public ChecksumFromResponse checksumFrom (ChecksumMessage checksumMessage) {
        ClientService clientService = new ClientService();
        Client currentClient = clientService.getClientByLogin(checksumMessage.getLoginOfSender());
        return new ChecksumFromResponse(0, "checksum to "
                + checksumMessage.getLoginOfRecipient() + " was sent");

    }


    public ChecksumFromResponse checksumTo (ChecksumMessage checksumMessage) {
        for (ClientHandler c: Server.clientHandlers) {
            if (c.currentClient.getLogin().equals(checksumMessage.getLoginOfRecipient())) {
                c.socketConnector.writeLine(new ChecksumToResponse(0, "checksum from " +
                        checksumMessage.getLoginOfSender() + " ", checksumMessage.getChecksum()).toString());
                return checksumFrom(checksumMessage);
            }
        }
        return new ChecksumFromResponse(1, "Your checksum was not sent. User fos not found");
    }

}
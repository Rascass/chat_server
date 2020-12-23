package com.solvd.automation.lab.fall.model;


import com.solvd.automation.lab.fall.exception.UnknownRequestType;
import com.solvd.automation.lab.fall.constant.TimeConstant;

import com.solvd.automation.lab.fall.listener.MyParser;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.message.*;
import com.solvd.automation.lab.fall.service.ClientService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class ClientHandler implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private Client currentClient;
    private final SocketConnector socketConnector;
    private final MyParser parser;

    public ClientHandler(SocketConnector socketConnector) {
        LOGGER.info("Created Client handler");
        parser = new MyParser(this);
        this.socketConnector = socketConnector;
    }

    @Override
    public void run() {
        Thread thread = new Thread(new ClientListener());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IResponse authenticate(LogInMessage logInMessage) {

        ClientService clientService = new ClientService();
        Client logInClient = clientService.getClientByLogin(logInMessage.getLogin());
        if (logInClient == null) {
            return new LogInResponse(2, "client was not found", "null");
        } else if (logInClient.getPasswordHash() != logInMessage.getPasswordHash()) {
            return new LogInResponse(1, "wrong password", "null");
        } else {
            currentClient = logInClient;
            this.setUpClient(currentClient);
            clientService.updateClient(currentClient);
            LOGGER.info(clientService.getClientByLogin(logInMessage.getLogin()).getLastLogin());
            return new LogInResponse(0, "authentication and token set is successful", currentClient.getLogin());
        }
    }

    private void setUpClient(Client client) {
        int token = (1 + (int) (Math.random() * 100000));
        client.setClientToken(token);
        client.setClientIp(socketConnector.getIp());
        client.setLastLogin(new Date());
        LOGGER.info("Client's address: " + currentClient.getLastLogin());
    }

    public IResponse registration(RegistrationMessage registrationMessage) {
        ClientService clientService = new ClientService();
        Client clientToRegister = clientService.getClientByLogin(registrationMessage.getLogin());
        if (clientToRegister != null) {
            return new RegistrationResponse(1, "client already exists!");
        } else {
            int clientCounter = clientService.getLastClientId();
            Client.setCounter(clientCounter);
            Client RegisterClient = new Client(null, null, 0, registrationMessage.getLogin(),
                    registrationMessage.getRegPassword(), null);
            clientService.createClient(RegisterClient);
            return new RegistrationResponse(0, "successful registration");
        }
    }

    public boolean isOnline(Client client) {
        return (new Date().getTime() - client.getLastLogin().getTime()) < (TimeConstant.LIFETIME);
    }

    public IResponse findClient(SearchMessage searchMessage) {
        ClientService clientService = new ClientService();
        String searchingLogin = searchMessage.getSearchLogin();
        LOGGER.info("Searching for " + searchingLogin);
        Client searchingClient = clientService.getClientByLogin(searchingLogin);
        LOGGER.info("Searched client: " + searchingClient);
        if (!isOnline(currentClient)) {
            return new SearchResponse(3, "you've been logged out", "null");
        }
        if (searchingClient == null) {
            return new SearchResponse(2, "can't find user with such login", "null");
        }
        if (searchingClient.getClientToken() == 0) {
            return new SearchResponse(1, "user is not authenticated", searchingClient.getLogin());
        }
        if (!isOnline(searchingClient)) {
            return new SearchResponse(3, "user have been logged out", searchingClient.getLogin());
        }
        return new SearchResponse(0, searchingClient.getClientIp(), searchingClient.getLogin());
    }

    public IResponse checksumFrom(ChecksumMessage checksumMessage) {
        return new ChecksumFromResponse(0, "checksum to "
                + checksumMessage.getLoginOfRecipient() + " was sent");
    }


    public IResponse checksumTo(ChecksumMessage checksumMessage) {
        if (!isOnline(currentClient)) {
            return new ChecksumToResponse(3, "you've been logged out", 0);
        }
        for (ClientHandler c : Server.clientHandlers) {
            System.out.print(c.currentClient.getLogin() + "     ");
            System.out.println(checksumMessage.getLoginOfRecipient());
            if (c.currentClient.getLogin().equals(checksumMessage.getLoginOfRecipient())) {

                if (!isOnline(c.currentClient)) {
                    return new ChecksumToResponse(3, "you've been logged out", 0);
                }
                String response = new ChecksumToResponse(0, "checksum from " +
                        checksumMessage.getLoginOfSender() + " ", checksumMessage.getChecksum()).toString();
                LOGGER.info("ChecksumTo for existing user: " + response);
                c.socketConnector.writeLine(response);
                return checksumFrom(checksumMessage);
            }
        }
        return new ChecksumFromResponse(1, "Your checksum was not sent. User was not found");
    }

    private class ClientListener implements Runnable {
        @Override
        public void run() {
            LOGGER.info("Started listener for client handler");
            String request;
            while ((request = socketConnector.readLine()) != null) {
                if (request.equals("Client disconnected!")) {
                    break;
                }
                LOGGER.info("Got a request from client" + request);
                IMessage response = null;
                try {
                    response = parser.getResponse(request);
                } catch (UnknownRequestType ex) {
                    ex.printStackTrace();
                }
                LOGGER.info("Writing response: " + response.toString());
                socketConnector.writeLine(response.toString());
            }
        }
    }

}
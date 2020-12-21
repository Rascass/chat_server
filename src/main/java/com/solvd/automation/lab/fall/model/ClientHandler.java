package com.solvd.automation.lab.fall.model;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.exception.UnknownRequestType;
import com.solvd.automation.lab.fall.io.PropertyReader;
import com.solvd.automation.lab.fall.listener.MyParser;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.message.*;
import com.solvd.automation.lab.fall.service.ClientService;
import com.solvd.automation.lab.fall.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    private Client currentClient;
    private final SocketConnector socketConnector;
    private Date tokenDate;
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
    }

    public LogInResponse authenticate(LogInMessage logInMessage) {
        int token = (1 + (int) (Math.random() * 100000));
        ClientService clientService = new ClientService();
        if (clientService.getClientByLogin(logInMessage.getLogin()) == null) {
            return new LogInResponse(2, "client was not found");
        }
        currentClient = clientService.getClientByLogin(logInMessage.getLogin());
        if (currentClient.getPasswordHash() != logInMessage.getPasswordHash()) {
            return new LogInResponse(1, "wrong password");
        }
        currentClient.setClientToken(token);
        tokenDate = new Date();
        System.out.println(tokenDate);
        currentClient.setClientIp(socketConnector.getIp());
        clientService.updateClient(currentClient);
        return new LogInResponse(0, "authentication and token set is successful");
    }

    public RegistrationResponse registration(RegistrationMessage registrationMessage) {
        int token = (1 + (int) (Math.random() * 100000));
        ClientService clientService = new ClientService();
        if (clientService.getClientByLogin(registrationMessage.getLogin()) != null) {
            return new RegistrationResponse(1, "client already exists!");
        }
        int clientCounter = clientService.getLastClientId();
        Client.setCounter(clientCounter);
        currentClient = new Client(socketConnector.getIp(), token, registrationMessage.getLogin(), registrationMessage.getRegPassword(), null);
        clientService.createClient(currentClient);
        return new RegistrationResponse(0, "successful registration");
    }

    public SearchResponse findClient(SearchMessage searchMessage) {
        System.out.println(searchMessage.getSearchLogin());
        ClientService clientService = new ClientService();
        if (clientService.getClientByLogin(searchMessage.getSearchLogin()) == null) {
            return new SearchResponse(2, "can't find user with such login");
        }
        currentClient = clientService.getClientByLogin(searchMessage.getSearchLogin());
        if (currentClient.getClientToken() == 0) {
            return new SearchResponse(1, "user offline");
        }
        System.out.println(new SearchResponse(0, "" + currentClient.getClientIp()).toString());
        return new SearchResponse(0, "" + currentClient.getClientIp());
    }

    public ChecksumFromResponse checksumFrom(ChecksumMessage checksumMessage) {
        ClientService clientService = new ClientService();
        currentClient = clientService.getClientByLogin(checksumMessage.getLoginOfSender());
        return new ChecksumFromResponse(0, "checksum to "
                + checksumMessage.getLoginOfRecipient() + " was sent");

    }

    public ChecksumFromResponse checksumTo(ChecksumMessage checksumMessage) {
        for (ClientHandler c : Server.clientHandlers) {
            if (c.currentClient.getLogin().equals(checksumMessage.getLoginOfRecipient())) {
                c.socketConnector.writeLine(new ChecksumToResponse(0, "checksum from " +
                        checksumMessage.getLoginOfSender() + " ", checksumMessage.getChecksum()).toString());
                return checksumFrom(checksumMessage);
            }
        }
        return new ChecksumFromResponse(1, "Your checksum was not sent. User fos not found");
    }

    private class ClientListener implements Runnable {

        @Override
        public void run() {

            LOGGER.info("Started listener for client handler");
            String request;

            while ((request = socketConnector.readLine()) != null) {

                LOGGER.info("Got a request from client" + request);

                IMessage response = null; // da da, eto null. Prostite

                try {
                    response = parser.getResponse(request);
                } catch (UnknownRequestType ex) {
                    ex.printStackTrace();
                }

                LOGGER.info("Writing response: "+response.toString());
                socketConnector.writeLine(response.toString());
            }

        }
    }

}
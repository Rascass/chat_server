package com.solvd.automation.lab.fall.main;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.io.PropertyReader;
import com.solvd.automation.lab.fall.model.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static SocketConnector socketConnector;
    public static List<ClientHandler> clientHandlers;
    public static void main(String[] args) {
        int port = Integer.parseInt(PropertyReader.getInstance().getValue(PropertyConstant.PORT_KEY));
        clientHandlers = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                socketConnector = new SocketConnector(serverSocket);
                ClientHandler clientHandler = new ClientHandler(socketConnector);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

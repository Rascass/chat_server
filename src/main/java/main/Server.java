package main;




import model.Client;
import model.Connection;
import model.ConnectionListener;
import model.Phone;
import service.ClientService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server started");

            while (true) {
                Phone phone = new Phone(serverSocket);
                new Thread(()->{
                    String request = phone.readLine();
                    System.out.println("Request: " + request);
                    String response = "";
                    Client currentClient;
                    ClientService clientService = new ClientService();
                    for (Client c: clientService.getAllClients()) {
                        if (request.contains(c.getLogin()) && request.contains(c.getPassword() + "")) {
                            response = "true";
                            currentClient = c;
                        } else {
                            response = "false";
                        }
                    }

                    phone.writeLine(response);
                    System.out.println("Response: " + response);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

}

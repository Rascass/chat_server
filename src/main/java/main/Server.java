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
                    boolean notFind = true;
                    int token = 1 + (int) (Math.random() * 100000);

                    for (Client c: clientService.getAllClients()) {
                        if (request.contains(c.getLogin()) && request.contains(c.getPassword() + "")) {
                            currentClient = c;
                            currentClient.setClientToken(token);
                            notFind = false;
                            break;
                        }
                    }

                    if (notFind) {
                        int from = request.indexOf("\":\"") + 3;
                        int to = request.lastIndexOf("\",\"");
                        String login = request.substring(from, to);

                        from = request.lastIndexOf(":") + 1;
                        to = request.lastIndexOf("}");
                        String password = request.substring(from, to);

                        clientService.createClient(new Client(token, login, password, null));
                    }
                    phone.writeLine(response);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

}

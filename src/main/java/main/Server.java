package main;




import model.*;
import service.ClientService;
import service.SessionService;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                    SessionService sessionService = new SessionService();

                    boolean notFind = true;
                    int token = 1 + (int) (Math.random() * 100000);
                    List<Client> clients = new ArrayList<>();
                    Session session = new Session(new Date(), 8000, true, "127.0.0.1",
                            "127.0.0.1", token, clients);


                    for (Client c: clientService.getAllClients()) {
                        if (request.contains(c.getLogin()) && request.contains(c.getPassword() + "")) {
                            currentClient = c;
                            currentClient.setClientToken(token);
                            clients.add(currentClient);
                            token = 1 + (int) (Math.random() * 100000);

                            sessionService.createSession(session);
                            clientService.updateClient(currentClient);
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

                        currentClient = new Client(token, login, password, null);
                        clients.add(currentClient);
                        clientService.createClient(currentClient);
                        sessionService.createSession(session);
                    }
                    phone.writeLine(response);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

}

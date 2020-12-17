package util;

import model.Client;

public class ClientUtil {
    private String ip;
    private int port;
    private Client client;

    public ClientUtil(String ip, int port, Client client) {
        this.ip = ip;
        this.port = port;
        this.client = client;
    }

    public void createConnection() {
        client.logIn(ip, port);
    }
}

package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.model.Client;

public class ConnectionUtil {
    private String ip;
    private int port;
    private Client client;

    public ConnectionUtil(String ip, int port, Client client) {
        this.ip = ip;
        this.port = port;
        this.client = client;
    }
}

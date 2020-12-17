package main;

import listener.Listener;
import model.Client;
import util.ConnectionUtil;

public class Main {
    public static void main(String[] args) {
        ConnectionUtil clientUtil = new ConnectionUtil("127.0.0.1", 8000,
                new Client(0, "sdf", "12332", null));
        clientUtil.createConnection();
        String a = "{\"login\":\"user123\",\"password\":1111}";
        System.out.println(Listener.getParser(a));
    }
}

package com.solvd.automation.lab.fall.main;

import com.solvd.automation.lab.fall.listener.Listener;
import com.solvd.automation.lab.fall.model.Client;
import com.solvd.automation.lab.fall.util.ConnectionUtil;
import com.solvd.automation.lab.fall.util.LogInParser;

public class Main {
    public static void main(String[] args) {

        ConnectionUtil clientUtil = new ConnectionUtil("127.0.0.1", 8002,
                new Client(0, "sdf", 12332,null));
        String a = "{\"login\":\"user123\",\"password\":1111}";
        System.out.println(Listener.getResponse(a).toString());
        System.out.println(LogInParser.parsePassword(a).toString());
    }
}

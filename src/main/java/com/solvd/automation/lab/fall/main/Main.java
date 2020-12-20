package com.solvd.automation.lab.fall.main;

import com.solvd.automation.lab.fall.listener.Listener;
import com.solvd.automation.lab.fall.model.Client;

public class Main {
    public static void main(String[] args) {
        String a = "{\"login\":\"user123\",\"password\":1111}";
        System.out.println(Listener.getResponse(a).toString());
    }
}

package com.solvd.automation.lab.fall.interfaces;

import com.solvd.automation.lab.fall.model.Connection;

public interface ConnectionListener {
    void onConnectionReady(Connection connection);
    void onReceiveString(Connection connection, String value);
    void onDisconnect(Connection connection);
    void onException(Connection connection, Exception e);
}

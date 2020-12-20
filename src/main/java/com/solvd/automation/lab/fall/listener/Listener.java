package com.solvd.automation.lab.fall.listener;

import com.solvd.automation.lab.fall.constant.ServerConstant;
import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.SocketConnector;
import com.solvd.automation.lab.fall.model.message.Response;
import com.solvd.automation.lab.fall.util.LogInParser;

public class Listener {

    private Listener() {}

    public static Parser getParser(String request) {
        for (Pattern pattern: Pattern.values()) {
            if (request.matches(pattern.getPatternName())) {
                return pattern.getParser();
            }
        }
        return null;
    }
    public static Response getResponse(String request) {
        Parser parser = Listener.getParser(request);
        if (parser == null) {
            return null;
        }
        if (parser.getClass() == LogInParser.class) {
            return Server.findClient(request);
        }
        return null;
    }
}

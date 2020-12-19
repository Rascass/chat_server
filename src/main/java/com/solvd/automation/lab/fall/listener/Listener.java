package com.solvd.automation.lab.fall.listener;

import com.solvd.automation.lab.fall.constant.ServerConstant;
import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.model.SocketConnector;
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
    public static String setResponse(String request) {
        Parser parser = Listener.getParser(request);
        if (parser.getClass() == LogInParser.class) {
//            login & make response
            SocketConnector socketConnector = new SocketConnector(ServerConstant.IP, ServerConstant.PORT);
            LogInParser logInParser = new LogInParser();
            String response = logInParser.parse(request).toString();
            socketConnector.writeLine(response);
            return response;
        }
//        if (ChecksumMessage.class) {
//    }
        return null;
    }
}

package com.solvd.automation.lab.fall.listener;

import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.ClientHandler;
import com.solvd.automation.lab.fall.model.message.*;
import com.solvd.automation.lab.fall.util.LogInParser;
import com.solvd.automation.lab.fall.util.RegistrationParser;
import com.solvd.automation.lab.fall.util.SearchParser;

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
    public static IMessage getResponse(String request) {
        Parser parser = Listener.getParser(request);
        if (parser == null) {
            return null;
        }
        if (parser.getClass() == LogInParser.class) {
            LogInMessage logInMessage = new LogInParser().parse(request);
            return new ClientHandler(Server.socketConnector).authenticate(logInMessage);
        }
        if (parser.getClass() == RegistrationParser.class) {
            RegistrationMessage registrationMessage = new RegistrationParser().parse(request);
            return new ClientHandler(Server.socketConnector).registration(registrationMessage);
        }
        if (parser.getClass() == SearchParser.class) {
            SearchMessage searchMessage = new SearchParser().parse(request);
            return new ClientHandler(Server.socketConnector).findClient(searchMessage);
        }
        return new LogInResponse(5,"wrong request!!!!!!!!!!!!!!!!!!!");
    }
}

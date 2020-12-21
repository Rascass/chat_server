package com.solvd.automation.lab.fall.listener;

import com.solvd.automation.lab.fall.exception.UnknownRequestType;
import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.ClientHandler;
import com.solvd.automation.lab.fall.model.message.*;
import com.solvd.automation.lab.fall.util.LogInParser;
import com.solvd.automation.lab.fall.util.RegistrationParser;
import com.solvd.automation.lab.fall.util.SearchParser;

public class MyParser {

    private ClientHandler client;

    public MyParser(ClientHandler clientHandler) {
        this.client = clientHandler;
    }

    private Parser getParser(String request) {
        for (Pattern pattern : Pattern.values()) {
            if (request.matches(pattern.getPatternName())) {
                return pattern.getParser();
            }
        }
        return null;
    }

    public IMessage getResponse(String request) throws UnknownRequestType {
        Parser parser = this.getParser(request);
        if (parser == null) {
            return null;
        } else if (parser.getClass() == LogInParser.class) {
            LogInMessage logInMessage = new LogInParser().parse(request);
            return client.authenticate(logInMessage);
        } else if (parser.getClass() == RegistrationParser.class) {
            RegistrationMessage registrationMessage = new RegistrationParser().parse(request);
            return client.registration(registrationMessage);
        } else if (parser.getClass() == SearchParser.class) {
            SearchMessage searchMessage = new SearchParser().parse(request);
            return client.findClient(searchMessage);
        } else {
            throw new UnknownRequestType("Can't find format to parse for request: " + request);
        }

    }
}

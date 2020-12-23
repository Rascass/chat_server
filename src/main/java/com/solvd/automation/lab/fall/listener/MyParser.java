package com.solvd.automation.lab.fall.listener;

import com.solvd.automation.lab.fall.exception.UnknownRequestType;
import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.main.Server;
import com.solvd.automation.lab.fall.model.ClientHandler;
import com.solvd.automation.lab.fall.model.message.*;
import com.solvd.automation.lab.fall.util.ChecksumParser;
import com.solvd.automation.lab.fall.util.LogInParser;
import com.solvd.automation.lab.fall.util.RegistrationParser;
import com.solvd.automation.lab.fall.util.SearchParser;

import java.util.Objects;

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
        parser = Objects.requireNonNull(parser, "parser was not recognized");

        if (parser.getClass().equals(LogInParser.class)) {
            LogInMessage logInMessage = new LogInParser().parse(request);
            return client.authenticate(logInMessage);
        }
        if (parser.getClass().equals(RegistrationParser.class)) {
            RegistrationMessage registrationMessage = new RegistrationParser().parse(request);
            return client.registration(registrationMessage);
        }
        if (parser.getClass().equals(SearchParser.class)) {
            SearchMessage searchMessage = new SearchParser().parse(request);
            return client.findClient(searchMessage);
        }
        if (parser.getClass().equals(ChecksumParser.class)) {
            ChecksumMessage checksumMessage = new ChecksumParser().parse(request);
            return client.checksumTo(checksumMessage);
        }
        throw new UnknownRequestType("Can't find format to parse for request: " + request);

    }
}

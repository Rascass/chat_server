package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.model.message.LogInMessage;
import com.solvd.automation.lab.fall.model.message.RegistrationMessage;

import static com.solvd.automation.lab.fall.util.LogInParser.parseLogin;
import static com.solvd.automation.lab.fall.util.LogInParser.parsePassword;

public class RegistrationParser implements Parser<RegistrationMessage> {
    @Override
    public RegistrationMessage parse(String request) {
        return new RegistrationMessage(parseLogin(request), parsePassword(request));
    }
}

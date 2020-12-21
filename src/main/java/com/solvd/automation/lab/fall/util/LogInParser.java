package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.model.message.LogInMessage;

public class LogInParser implements Parser<LogInMessage> {

    public static String parseLogin(String request) {
        int from = request.indexOf("\":\"") + 3;
        int to = request.lastIndexOf("\",\"");
        return request.substring(from, to);
    }

    public static Integer parsePassword(String request) {
        int from = request.lastIndexOf(":") + 1;
        int to = request.lastIndexOf("}");
        return Integer.valueOf(request.substring(from, to));
    }

    @Override
    public LogInMessage parse(String request) {
        return new LogInMessage(parseLogin(request), parsePassword(request));
    }
}

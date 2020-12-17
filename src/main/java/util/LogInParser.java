package util;

import interfaces.Parser;

public class LogInParser implements Parser {

    public static String parseLogin(String request) {
        int from = request.indexOf("\":\"") + 3;
        int to = request.lastIndexOf("\",\"");
        return request.substring(from, to);
    }

    public static String parsePassword(String request) {
        int from = request.lastIndexOf(":") + 1;
        int to = request.lastIndexOf("}");
        return request.substring(from, to);
    }
}

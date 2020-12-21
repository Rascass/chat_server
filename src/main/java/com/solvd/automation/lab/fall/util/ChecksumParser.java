package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.model.message.ChecksumMessage;
import com.solvd.automation.lab.fall.model.message.LogInMessage;

public class ChecksumParser implements Parser<ChecksumMessage> {
    //"{"loginFrom":"loginOfSender","loginTo":"loginOfRecipient","checksum":25}"
    public static String parseLoginFrom(String request) {
        int from = request.indexOf("\":\"") + 3;
        int to = request.indexOf("\",\"");
        return request.substring(from, to);
    }

    public static String parseLoginTo(String request) {
        int from = request.lastIndexOf("\":\"") + 3;
        int to = request.lastIndexOf("\",\"");
        return request.substring(from, to);
    }

    public static Integer parseChecksum(String request) {
        int from = request.lastIndexOf(":") + 1;
        int to = request.lastIndexOf("}");
        return Integer.valueOf(request.substring(from, to));
    }

    @Override
    public ChecksumMessage parse(String request) {
        return new ChecksumMessage(parseLoginFrom(request), parseLoginTo(request), parseChecksum(request));
    }
}

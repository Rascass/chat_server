package com.solvd.automation.lab.fall.util;

import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.model.message.RegistrationMessage;
import com.solvd.automation.lab.fall.model.message.SearchMessage;

public class SearchParser implements Parser<SearchMessage> {

    public static String parseLogin(String request) {
        int from = request.indexOf("\":\"") + 3;
        int to = request.lastIndexOf("\"");
        return request.substring(from, to);
    }
    @Override
    public SearchMessage parse(String request) {
        return new SearchMessage(parseLogin(request));
    }
}

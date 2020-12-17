package listener;

import interfaces.Parser;

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
}

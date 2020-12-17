package listener;

import interfaces.Parser;
import util.LogInParser;

public enum Pattern {
    LOGINPATTERN("(\\{\"login\":)\"[a-zA-Z0-9]+\"(,\"password\":)[0-9]+\\}", new LogInParser());

    private String patternName;
    private Parser parser;

    Pattern(String patternName, Parser parser) {
        this.patternName = patternName;
        this.parser = parser;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}

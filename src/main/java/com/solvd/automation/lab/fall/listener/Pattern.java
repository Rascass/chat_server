package com.solvd.automation.lab.fall.listener;

import com.solvd.automation.lab.fall.interfaces.Parser;
import com.solvd.automation.lab.fall.util.ChecksumParser;
import com.solvd.automation.lab.fall.util.LogInParser;
import com.solvd.automation.lab.fall.util.RegistrationParser;
import com.solvd.automation.lab.fall.util.SearchParser;

public enum Pattern {
    LOGINPATTERN("(\\{\"login\":)\"[a-zA-Z0-9]+\"(,\"password\":)[-]?[0-9]+\\}", new LogInParser()),
    REGISTRATIONPATTERN("(\\{\"regLogin\":)\"[a-zA-Z0-9]+\"(,\"regPassword\":)[-]?[0-9]+\\}", new RegistrationParser()),
    SEARCHPATTERN("(\\{\"contactLogin\":)\"[a-zA-Z0-9]+\"\\}", new SearchParser()),
    CHECKSUM("\\{\"loginFrom\":\"[a-zA-Z0-9]+\",\"loginTo\":\"[a-zA-Z0-9]+\",\"checksum\":\\d+}", new ChecksumParser());

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

package com.solvd.automation.lab.fall.model.message;

public class SearchMessage {
    String login;

    public SearchMessage(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "{\"login\":\""+login+"\"}";
    }
}

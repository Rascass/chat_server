package com.solvd.automation.lab.fall.model.message;

public class LogInResponse implements IResponse {
    private int code;
    private String logInDescription;
    private String login;

    public LogInResponse(int code, String logInDescription, String login) {
        this.code = code;
        this.logInDescription = logInDescription;
        this.login = login;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLogInDescription() {
        return logInDescription;
    }

    public void setLogInDescription(String logInDescription) {
        this.logInDescription = logInDescription;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"logInDescription\":" + logInDescription + ",\"login\":" + login + '}';
    }
}

package com.solvd.automation.lab.fall.model.message;

public class SearchResponse implements IResponse {
    private int code;
    private String connection;
    private String login;

    public SearchResponse(int code, String connection, String login) {
        this.code = code;
        this.connection = connection;
        this.login = login;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"connection\":" + connection + ",\"login\":" + login + "}";
    }
}

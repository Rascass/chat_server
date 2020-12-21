package com.solvd.automation.lab.fall.model.message;

public class SearchResponse implements IResponse {
    private int code;
    private String connection;

    public SearchResponse(int code, String connection) {
        this.code = code;
        this.connection = connection;
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

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"connection\":" + connection +'}';
    }
}

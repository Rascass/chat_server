package com.solvd.automation.lab.fall.model.message;

public class LogInResponse implements IResponse {
    private int code;
    private String logInDescription;

    public LogInResponse(int code, String logInDescription) {
        this.code = code;
        this.logInDescription = logInDescription;
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

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"logInDescription\":" + logInDescription +'}';
    }
}

package com.solvd.automation.lab.fall.model.message;

public class RegistrationMessage implements AbstractRequest{
    private String regLogin;
    private int regPassword;

    public RegistrationMessage(String login, int regPassword) {
        this.regLogin = login;
        this.regPassword = regPassword;
    }

    public String getLogin() {
        return regLogin;
    }

    public void setLogin(String regLogin) {
        this.regLogin = regLogin;
    }

    public int getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(int regPassword) {
        this.regPassword = regPassword;
    }

    @Override
    public String toString() {
        return "{\"regLogin\":\""+regLogin+"\",\"regPassword\":" + regPassword +'}';
    }
}

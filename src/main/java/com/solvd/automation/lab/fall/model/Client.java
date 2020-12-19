package com.solvd.automation.lab.fall.model;

import com.solvd.automation.lab.fall.dao.AbstractModel;

import java.util.List;
import java.util.Objects;

public class Client extends AbstractModel {

    private int clientToken;
    private String login;
    private int password;
    private List<Session> sessionList;
    private static int counter;

    public Client() {
    }

    public Client(int clientToken, String login, String password, List<Session> sessionList) {
        this.clientToken = clientToken;
        this.login = login;
        this.setPassword(password);
        this.setSessionList(sessionList);
        this.setId(++this.counter);
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Client.counter = counter;
    }

    public int getClientToken() {
        return clientToken;
    }

    public void setClientToken(int clientToken) {
        this.clientToken = clientToken;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return Objects.hash(password);
    }

    public void setPassword(String password) {
        this.password = Objects.hash(password);
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientToken == client.clientToken &&
                Objects.equals(login, client.login) &&
                Objects.equals(password, client.password) &&
                Objects.equals(sessionList, client.sessionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientToken, login, password, sessionList);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientToken=" + clientToken +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", sessionList=" + sessionList +
                '}';
    }
}
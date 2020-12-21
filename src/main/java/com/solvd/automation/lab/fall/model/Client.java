package com.solvd.automation.lab.fall.model;

import com.solvd.automation.lab.fall.dao.AbstractModel;

import java.util.List;
import java.util.Objects;

public class Client extends AbstractModel {

    private String clientIp;
    private int clientToken;
    private String login;
    private int passwordHash;
    private List<Session> sessionList;
    private static int counter;

    public Client() {
    }

    public Client(String clientIp, int clientToken, String login, int passwordHash, List<Session> sessionList) {
        this.clientIp = clientIp;
        this.clientToken = clientToken;
        this.login = login;
        this.setPasswordHash(passwordHash);
        this.setSessionList(sessionList);
        this.setId(++this.counter);
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
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
                Objects.equals(passwordHash, client.passwordHash) &&
                Objects.equals(sessionList, client.sessionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientToken, login, passwordHash, sessionList);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientIp='" + clientIp + '\'' +
                ", clientToken=" + clientToken +
                ", login='" + login + '\'' +
                ", passwordHash=" + passwordHash +
                ", sessionList=" + sessionList +
                '}';
    }
}
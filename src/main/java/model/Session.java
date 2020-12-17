package model;

import dao.AbstractModel;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Session extends AbstractModel {

    private Date start;
    private int port;
    private boolean isActive;
    private String ip;
    private String host;
    private int sessionToken;
    private List<Client> clients;
    private static int counter;

    public Session() {
    }

    public Session(Date start, int port, boolean isActive, String ip, String host, int sessionToken, List<Client> clients) {
        this.start = start;
        this.port = port;
        this.isActive = isActive;
        this.ip = ip;
        this.host = host;
        this.sessionToken = sessionToken;
        this.clients = clients;
        this.setId(++this.counter);
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Session.counter = counter;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(int sessionToken) {
        this.sessionToken = sessionToken;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return port == session.port &&
                isActive == session.isActive &&
                sessionToken == session.sessionToken &&
                Objects.equals(start, session.start) &&
                Objects.equals(ip, session.ip) &&
                Objects.equals(host, session.host) &&
                Objects.equals(clients, session.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, port, isActive, ip, host, sessionToken, clients);
    }

    @Override
    public String toString() {
        return "Session{" +
                "start=" + start +
                ", port=" + port +
                ", isActive=" + isActive +
                ", ip='" + ip + '\'' +
                ", host='" + host + '\'' +
                ", sessionToken=" + sessionToken +
                ", clients=" + clients +
                '}';
    }
}

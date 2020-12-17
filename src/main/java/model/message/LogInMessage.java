package model.message;

public class LogInMessage {
    private String login;
    private int passwordHash;

    public LogInMessage(String login, int passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
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

    @Override
    public String toString() {
        return "{\"login\":\""+login+"\",\"password\":" + passwordHash +'}';
    }
}

package dao;

import model.Session;

import java.util.List;

public interface SessionDao {

    void create(Session session);
    Session getById(int id);
    List<Session> get();
    void update(Session session);
    void delete(int id);
    int getLastSessionId();
}

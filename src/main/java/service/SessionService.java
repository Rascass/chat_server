package service;

import dao.SessionDao;
import dao.impl.SessionDaoImpl;
import model.Session;

import java.util.List;

public class SessionService {
    SessionDao sessionDao = new SessionDaoImpl();

    public Session getSessionById(int id) {
        return sessionDao.getById(id);
    }

    public List<Session> getAllSessions() {
        return sessionDao.get();
    }

    public void createSession(Session session) {
        sessionDao.create(session);
    }

    public void deleteSessionById(int id) {
        sessionDao.delete(id);
    }

    public void updateSession(Session session) {
        sessionDao.update(session);
    }

    public int getLastSessionId() {
        return sessionDao.getLastSessionId();
    }
}

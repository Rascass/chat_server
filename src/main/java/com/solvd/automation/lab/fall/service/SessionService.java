package com.solvd.automation.lab.fall.service;

import com.solvd.automation.lab.fall.dao.SessionDao;
import com.solvd.automation.lab.fall.dao.impl.SessionDaoImpl;
import com.solvd.automation.lab.fall.model.Session;

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

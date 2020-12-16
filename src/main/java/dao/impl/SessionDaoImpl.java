package dao.impl;

import config.SessionFactory;
import dao.AbstractModel;
import dao.SessionDao;
import model.Client;
import model.Session;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SessionDaoImpl implements SessionDao {

    private String namespace = "sessions_mapper";
    @Override
    public void create(Session session) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.insert(namespace + ".create", session);
        if (session.getClients() != null) {
            sqlSession.insert(namespace + ".bind", session);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Session getById(int id) {
        SqlSession sqlSession = SessionFactory.getSession();
        Session session = sqlSession.selectOne(namespace + ".getById", id);
        sqlSession.close();
        return session;
    }

    @Override
    public List<Session> get() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<Session> sessions = sqlSession.selectList(namespace + ".get");
        sqlSession.close();
        return sessions;
    }

    @Override
    public void update(Session session) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.update(namespace + ".update", session);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(int id) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.delete(namespace + ".deleteFromJunctionById", id);
        sqlSession.delete(namespace + ".deleteById", id);
        sqlSession.commit();
        sqlSession.close();
    }
    public int getLastSessionId() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<AbstractModel> values = sqlSession.selectList(namespace + ".get");
        int id = 0;
        if (values.size() > 0) {
            id = values.get(values.size() - 1).getId();
        }
        sqlSession.commit();
        sqlSession.close();
        return id;
    }
}

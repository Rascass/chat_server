package dao.impl;

import config.SessionFactory;
import dao.AbstractModel;
import dao.ClientDao;
import model.Client;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ClientDaoImpl implements ClientDao {

    private String namespace = "clients_mapper";
    @Override
    public void create(Client client) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.insert(namespace + ".create", client);
        if (client.getSessionList() != null) {
            sqlSession.insert(namespace + ".bind", client);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Client getById(int id) {
        SqlSession sqlSession = SessionFactory.getSession();
        Client client = sqlSession.selectOne(namespace + ".getById", id);
        sqlSession.close();
        return client;
    }

    @Override
    public List<Client> get() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<Client> clients = sqlSession.selectList(namespace + ".get");
        sqlSession.close();
        return clients;
    }

    @Override
    public void update(Client client) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.update(namespace + ".update", client);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(int id) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.delete(namespace + ".deleteById", id);
        sqlSession.commit();
        sqlSession.close();
    }
    public int getMax() {
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
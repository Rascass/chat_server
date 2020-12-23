package com.solvd.automation.lab.fall.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class SessionFactory {

    public static final Logger LOGGER = LogManager.getLogger();
    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        try {
            String resource = "mybatis-config.xml";
            Reader is = Resources.getResourceAsReader(resource);
            System.out.println(is);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }
}
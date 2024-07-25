package com.javarush.ikolybaba.util;

import com.javarush.ikolybaba.domain.City;
import com.javarush.ikolybaba.domain.Country;
import com.javarush.ikolybaba.domain.CountryLanguage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class WorldSessionFactory {
    private final SessionFactory sessionFactory;
    private static WorldSessionFactory instance;

    private WorldSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/world");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(CountryLanguage.class)
                .buildSessionFactory();
    }

    public static SessionFactory prepareRelationalDb() {
        if (instance == null) {
            instance = new WorldSessionFactory();
        }

        return instance.sessionFactory;
    }
}

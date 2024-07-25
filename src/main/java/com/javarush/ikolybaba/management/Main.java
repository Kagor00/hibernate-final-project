package com.javarush.ikolybaba.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.ikolybaba.dao.CityDAO;
import com.javarush.ikolybaba.dao.CountryDAO;
import com.javarush.ikolybaba.domain.City;
import com.javarush.ikolybaba.domain.Country;
import com.javarush.ikolybaba.domain.CountryLanguage;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.util.Objects.nonNull;

public class Main {
    private final SessionFactory sessionFactory;
//    private final RedisClient redisClient;
    private final ObjectMapper mapper;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;

    public Main() {
        sessionFactory = prepareRelationalDb();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
//        redisClient = prepareRedisClient();
        mapper = new ObjectMapper();
    }
    public static void main(String[] args) {
        Main main = new Main();
        List<City> cities = main.fetchData(main);



        for (int i = 0; i < 5; i++) {
            System.out.println(cities.get(i));
        }


    }



    private SessionFactory prepareRelationalDb() {
        final SessionFactory sessionFactory;
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
        return sessionFactory;
    }

    private List<City> fetchData(Main main) {
        try (Session session = main.sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();
            List<Country> countries = main.countryDAO.getAll();

            int totalCount = main.cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(main.cityDAO.getItems(i, step));
            }
            session.getTransaction().commit();
            return allCities;
        }
    }

//    private RedisClient prepareRedisClient() {
//        RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
//        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
//            System.out.println("\nConnected to Redis\n");
//        }
//        return redisClient;
//    }

//    private void shutdown() {
//        if (nonNull(sessionFactory)) {
//            sessionFactory.close();
//        }
//        if (nonNull(redisClient)) {
//            redisClient.shutdown();
//        }
//    }
}
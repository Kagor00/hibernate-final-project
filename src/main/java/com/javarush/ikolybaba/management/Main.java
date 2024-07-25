package com.javarush.ikolybaba.management;

import com.javarush.ikolybaba.domain.Country;
import com.javarush.ikolybaba.util.WorldSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Country> countries = new ArrayList<>();
        Country country = null;
        SessionFactory sessionFactory = WorldSessionFactory.prepareRelationalDb();

        try (Session session = sessionFactory.openSession()) {
            country = session.get(Country.class, 7);
            Query<Country> query =  session.createQuery("select c from Country c where c.id >: id", Country.class);
            query.setParameter("id", 5);
            query.setFirstResult(10);
            query.setMaxResults(5);
            countries = query.getResultList();
        }

        System.out.println(country);
        countries.forEach(System.out::println);


    }
}
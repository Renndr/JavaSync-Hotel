package com.hotel.Factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hotel.Models.Huespedes;

public class HuespedesFactory {
    
    private SessionFactory sessionFactory;

    public HuespedesFactory() {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Huespedes.class);
        return configuration.buildSessionFactory();
    }

    public void save(Huespedes huespedes) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(huespedes);
            session.getTransaction().commit();
            session.close();
        }
    }
}

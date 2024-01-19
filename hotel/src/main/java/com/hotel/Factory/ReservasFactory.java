package com.hotel.Factory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hotel.Models.Reservas;

public class ReservasFactory {
    
    private SessionFactory sessionFactory;
    private Session currentSession;
    private Transaction currentTransaction;

    public ReservasFactory() {
        sessionFactory = buildSessionFactory();
        currentSession = sessionFactory.openSession();
    }
    
    public Session getCurrentSession() {
        return currentSession;
    }

    private  SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Reservas.class);
        return configuration.buildSessionFactory();
    }

    public void openSession() {
        currentSession = sessionFactory.openSession();
        currentTransaction = currentSession.beginTransaction();
    }

    public void closeSession() {
        if (currentTransaction != null && currentTransaction.isActive()) {
            currentTransaction.commit();
        }
        if (currentSession != null) {
            currentSession.close();
        }
    }

    public void rollback() {
        if (currentTransaction != null && currentTransaction.isActive()) {
            currentTransaction.rollback();
        }
        if (currentSession != null) {
            currentSession.close();
        }
    }

    public void persist(Reservas reservas) {
        openSession();
        currentSession.persist(reservas);
        currentSession.close();
    }

    public void save(Reservas reservas) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(reservas);
            session.getTransaction().commit();
            session.close();
        }
    }

    public void update(Reservas reserva) {
    try {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(reserva);

        transaction.commit();
        session.close();
    } catch (HibernateException e) {
        throw new RuntimeException("Error al actualizar la reserva: " + e.getMessage(), e);
    }
}

}

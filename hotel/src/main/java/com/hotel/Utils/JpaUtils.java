package com.hotel.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtils {
	
	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("hotel-alura");
	
	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}

	public void iniciarTransaccion() {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	}
}

package com.hotel.Dao;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hotel.Models.Reservas;

public class ReservasDao {
	
	private EntityManager em;

	public ReservasDao(EntityManager em) {
		this.em = em;
	}
	
	public void guardar(Reservas reservas) {
		this.em.persist(reservas);
	}
	
	public void actualizar(Reservas reservas) {
		this.em.merge(reservas);
	}
	
	public void remover(Reservas reservas) {
		reservas=this.em.merge(reservas);
		this.em.remove(reservas);
	}
	
	public Reservas consultaPorId(Integer id) {
		return em.find(Reservas.class, id);
	}

	public void update(Reservas reserva) {
        em.getTransaction().begin();
        actualizar(reserva);
        em.getTransaction().commit();
    }

	public Integer consultaPorMaxId() {
		try {
			String jpql = "SELECT MAX(r.numeroReserva) FROM Reservas r";
			TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new NullPointerException("No existe ningun id en la base de datos.");
		}
	}
	
	public void removerPorId(Integer id) {
		Reservas reserva = consultaPorId(id);

		if (reserva != null) {
			EntityTransaction et = em.getTransaction();
			et.begin();
			remover(reserva);
			et.commit();
			em.close();
		} else {
			throw new IllegalArgumentException("No se encontró ninguna reserva con el ID: " + id);
		}
		
	}
	
	public List<Reservas> consultarTodos(){
		String jpql = "SELECT r FROM Reservas r";
        TypedQuery<Reservas> query = em.createQuery(jpql, Reservas.class);
        return query.getResultList();
	}

	public DefaultTableModel construirModeloTabla(List<Reservas> reservasList) {
		DefaultTableModel tableModel = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 1 || columnIndex == 2) {
					return LocalDate.class;
				}
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1 || column == 2 || column == 3 || column ==4;
			}
		};
		
		tableModel.addColumn("Número de Reserva");
		tableModel.addColumn("Fecha Check-in");
		tableModel.addColumn("Fecha Check-out");
		tableModel.addColumn("Valor de Reserva USD");
		tableModel.addColumn("Forma de Pago");

		for (Reservas reserva : reservasList) {
			Object[] row = new Object[5];
			row[0] = reserva.getNumeroReserva();
			row[1] = reserva.getCheckIn();
			row[2] = reserva.getCheckOut();
			row[3] = reserva.getValorReserva();
			row[4] = reserva.getFormaPago();
			tableModel.addRow(row);
		}
		
		return tableModel;
	}

	public int actualizar(Integer id, String valor, String pago, Date checkIn, Date checkOut) {
		try {
			String hql = "UPDATE Reservas r "
					+ "SET r.valorReserva = :valor, "
					+ "r.formaPago = :pago, "
					+ "r.checkIn = :checkIn, "
					+ "r.checkOut = :checkOut "
					+ "WHERE r.numeroReserva = :id ";

			EntityTransaction tx = em.getTransaction();
			tx.begin();

			Query query = em.createQuery(hql);

			query.setParameter("valor", valor);
			query.setParameter("id", id);
			query.setParameter("pago", pago);
			query.setParameter("checkIn", checkIn);
			query.setParameter("checkOut", checkOut);

			int updateCount = query.executeUpdate();

			tx.commit();
			em.close();

			return updateCount;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar el registro.");
			throw new RuntimeException(e);
		}
	}

}
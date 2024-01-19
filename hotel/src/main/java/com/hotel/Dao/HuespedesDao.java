package com.hotel.Dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hotel.Models.Huespedes;

public class HuespedesDao {
	
	private EntityManager em;

	public HuespedesDao(EntityManager em) {
		this.em = em;
	}
	
	public void guardar(Huespedes huespedes) {
		this.em.persist(huespedes);
	}
	
	
	public void actualizar(Huespedes huespedes) {
		this.em.merge(huespedes);
	}
	
	public void remover(Huespedes huespedes) {
		huespedes=this.em.merge(huespedes);
		this.em.remove(huespedes);
	}

	public DefaultTableModel construirModeloTabla(List<Huespedes> huespedesList) {
		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0 || column == 6) { //Hacer las columnas 0 y 6 no editables.
					return false;
				} else {
					return true;
				}
			}
		};
		tableModel.addColumn("Número de Huesped");
		tableModel.addColumn("Nombre");
		tableModel.addColumn("Apellido");
		tableModel.addColumn("Fecha de Nacimiento");
		tableModel.addColumn("Nacionalidad");
		tableModel.addColumn("Telefono");
		tableModel.addColumn("Número de Reserva");

		for (Huespedes huespedes : huespedesList) {
			Object[] row = new Object[7];
			row[0] = huespedes.getNumeroHuesped();
			row[1] = huespedes.getNombre();
			row[2] = huespedes.getApellido();
			row[3] = huespedes.getFechaNacimiento();
			row[4] = huespedes.getNacionalidad();
			row[5] = huespedes.getTelefono();
			row[6] = huespedes.getNumeroReserva();

			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public Huespedes consultaPorId(Integer id) {
		return em.find(Huespedes.class, id);
	}

	public void removerPorId(Integer id) {
		Huespedes huespedes = consultaPorId(id);
		if (huespedes != null) {
			EntityTransaction et = em.getTransaction();
			et.begin();
			remover(huespedes);
			et.commit();
			em.close();
		} else {
			throw new IllegalArgumentException("No se encontró ninguna reserva con el ID: " + id);
		}
	}
	
	public List<Huespedes> consultarTodos(){
		String jpql = "SELECT r FROM Huespedes r";
        TypedQuery<Huespedes> query = em.createQuery(jpql, Huespedes.class);
        return query.getResultList();
	}
	
	public List<Huespedes> consultaPorNombre(String nombre){
		String jpql =" SELECT P FROM Cliente AS P WHERE P.nombre=:nombre ";
		return em.createQuery(jpql,Huespedes.class).setParameter("nombre", nombre).getResultList();
	}

    public int actualizar(Integer id, String nombre, String apellido, String nacionalidad, String telefono, LocalDate fecha) {
        try {
			String hql = "UPDATE Huespedes r "
					+ "SET r.nombre = :nombre, "
					+ "r.apellido = :apellido, "
					+ "r.nacionalidad = :nacionalidad, "
					+ "r.telefono = :telefono, "
					+ "r.FechaNacimiento = :fecha "
					+ "WHERE r.numeroHuesped = :id ";

			EntityTransaction tx = em.getTransaction();
			tx.begin();

			Query query = em.createQuery(hql);

			query.setParameter("nombre", nombre);
			query.setParameter("id", id);
			query.setParameter("apellido", apellido);
			query.setParameter("nacionalidad", nacionalidad);
			query.setParameter("telefono", telefono);
			query.setParameter("fecha", fecha);


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
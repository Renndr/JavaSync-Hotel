package com.hotel.Views;

import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.hotel.Dao.ReservasDao;
import com.hotel.Dao.HuespedesDao;
import com.hotel.ErrorHandling.*;
import com.hotel.Utils.*;
import com.hotel.Models.*;
import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private JLabel labelAtras;
	private JLabel labelExit;
	private DefaultTableModel tableModelReservas;
	private DefaultTableModel tableModelHuespedes;
	
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("../images/lupa2.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField(null,"Buscar elementos",10);
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setFont(txtBuscar.getFont().deriveFont(Font.ITALIC));
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 22));
		lblNewLabel_4.setBounds(331, 62, 340, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 14));
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("../images/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
	
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 14));
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("../images/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		// Carga de metodos para las tablas
		soloSeleccionTabla(tbReservas, tbHuespedes);
		cargarTabla();
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(ImageLoader.createImageIcon("../images/Ha-100px.png"));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JButton btnLimpiar = new JButton();
		btnLimpiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtBuscar.setText(null);
			}
			
		});
		
		btnLimpiar.setLayout(null);
		btnLimpiar.setBackground(new Color(12, 138, 199));
		btnLimpiar.setBounds(748, 125, 122, 35);
		btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnLimpiar);
		
		JLabel lblLimpiar = new JLabel("Limpiar");
		lblLimpiar.setBounds(0, 0, 122, 35);
		btnLimpiar.add(lblLimpiar);
		lblLimpiar.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimpiar.setForeground(Color.WHITE);
		lblLimpiar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JButton btnEditar = new JButton();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modificar();
			}
		});
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JButton btnEliminar = new JButton();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		btnEliminar.addMouseListener(new MouseAdapter() {
    		@Override
			public void mouseClicked(MouseEvent e) {
				if (tieneFilaElegida()) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Por favor, seleccione una fila.");
					return;
				}

				int result = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(contentPane), "Se eliminará el registro.", "Confirmación", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					eliminar();
					cargarTabla();
				}
			}
		});
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);

	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	    int x = evt.getXOnScreen();
	    int y = evt.getYOnScreen();
	    this.setLocation(x - xMouse, y - yMouse);
	}

// Codigo que permite ver si hay alguna fila seleccionada.
	private boolean tieneFilaElegida() {
        return tbHuespedes.getSelectedRowCount() == 0 && tbReservas.getSelectedRowCount() == 0;
    }

	private void cargarTabla() {
		EntityManager em = JpaUtils.getEntityManager();
		ReservasDao reservasDao = new ReservasDao(em);
		List<Reservas> reservasList = reservasDao.consultarTodos();

		tableModelReservas = reservasDao.construirModeloTabla(reservasList);
		tbReservas.setModel(tableModelReservas);
		

		HuespedesDao huespedesDao = new HuespedesDao(em);
		List<Huespedes> huespedesList = huespedesDao.consultarTodos();

		tableModelHuespedes = huespedesDao.construirModeloTabla(huespedesList);
		tbHuespedes.setModel(tableModelHuespedes);
		em.close();

		//Metodo para jComboBox en las tablas.
		editarElementos editarTablas = new editarElementos();
		editarTablas.tablaJcomboBox(tbReservas, tbHuespedes);
		editarTablas.centrarContenido(tbReservas, tbHuespedes);
		
		//Editar las celdas en formato fecha.
		editarTablas.editarLocalDate(tbReservas, tbHuespedes);

		//Solo permitir numeros en Jtable
		editarTablas.setNumericColumn(tbHuespedes,5,tbReservas,3);

		//Filtrar datos en la tabla
		editarTablas.buscarTabla(tbReservas, tbHuespedes, tableModelReservas, tableModelHuespedes, txtBuscar);

		//preTexto
		//editarTablas.preTexto(txtBuscar);

	}

	private void soloSeleccionTabla(JTable tabla1, JTable tabla2) {
		ListSelectionModel selectionModelTabla1 = tbReservas.getSelectionModel();
		ListSelectionModel selectionModelTabla2 = tbHuespedes.getSelectionModel();

		selectionModelTabla1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && selectionModelTabla1.getMinSelectionIndex() != -1) {
					selectionModelTabla2.clearSelection();
				}
			}
		});

		selectionModelTabla2.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && selectionModelTabla2.getMinSelectionIndex() != -1) {
					selectionModelTabla1.clearSelection();
				}
			}
		});
	}

	private void eliminar() {
		if (tbHuespedes.getSelectedRowCount() > 0) {
			eliminarHuespedes();
		}
		else if (tbReservas.getSelectedRowCount() > 0) {
			eliminarReservas();
		}
	}	

	private void eliminarHuespedes() {
		Integer idHuesped = Integer.valueOf(tableModelHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

		EntityManager em2 = JpaUtils.getEntityManager();
		HuespedesDao huespedesDao = new HuespedesDao(em2);
		huespedesDao.removerPorId(idHuesped);

	}

	private void eliminarReservas() {
		Integer idReservas = Integer.valueOf(tableModelReservas.getValueAt(tbReservas.getSelectedRow(), 0).toString());

		EntityManager em = JpaUtils.getEntityManager();
		ReservasDao reservasDao = new ReservasDao(em);
		reservasDao.removerPorId(idReservas);

	}

	private void modificar() {
		if(tieneFilaElegida()){
			JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
		}
		else {
			modificarReservas();
			modificarHuespedes();
		}
	}

		private void modificarReservas() {
		int fila = tbReservas.getSelectedRow();
		if (fila < 0 ) {
			//JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
		} else {
			EntityManager em = JpaUtils.getEntityManager();
			ReservasDao reservasDao = new ReservasDao(em);

			Integer id = Integer.valueOf(tableModelReservas.getValueAt(fila, 0).toString());
			LocalDate checkIn = (LocalDate) (tableModelReservas.getValueAt(fila, 1));
			LocalDate checkOut = (LocalDate) (tableModelReservas.getValueAt(fila, 2));
			String valor = (String) tableModelReservas.getValueAt(fila, 3);
			String pago = (String) tableModelReservas.getValueAt(fila, 4);

				Reservas reserva = reservasDao.consultaPorId(id);
				if (reserva != null) {
					// Verificar si se han realizado cambios en los datos
					if (!valor.equals(reserva.getValorReserva()) || !pago.equals(reserva.getFormaPago()) || !checkIn.equals(reserva.getCheckIn())
						|| !checkOut.equals(reserva.getCheckOut())) {
						
						reserva.setValorReserva(valor);
						reserva.setFormaPago(pago);
						reserva.setCheckIn(checkIn);
						reserva.setCheckOut(checkOut);
						reservasDao.update(reserva);

						JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente.",
								"Actualización exitosa.", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "No se han realizado cambios, por favor edita un campo.",
								"Sin cambios.", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				em.close();
		}	
	}

	private void modificarHuespedes() {
		int fila = tbHuespedes.getSelectedRow();
		if (fila < 0 ) {
			//JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
		} else {
			EntityManager em = JpaUtils.getEntityManager();
			HuespedesDao huespedesDao = new HuespedesDao(em);


			Integer id = Integer.valueOf(tableModelHuespedes.getValueAt(fila, 0).toString());
			String nombre = (String) tableModelHuespedes.getValueAt(fila, 1);
			String apellido = (String) tableModelHuespedes.getValueAt(fila, 2);
			LocalDate fechaN = (LocalDate) tableModelHuespedes.getValueAt(fila, 3); 
			String nacionalidad = (String) tableModelHuespedes.getValueAt(fila, 4);
			String telefono = (String) tableModelHuespedes.getValueAt(fila, 5);
			
			Huespedes huespedes = huespedesDao.consultaPorId(id);
			if (huespedes != null) {
				// Verificar si se han realizado cambios en los datos
				if (!nombre.equals(huespedes.getNombre()) || !apellido.equals(huespedes.getApellido()) || !nacionalidad.equals(huespedes.getNacionalidad()) 
					 ||!telefono.equals(huespedes.getTelefono()) || !fechaN.equals(huespedes.getFechaNacimiento())) {

					 huespedesDao.actualizar(id, nombre, apellido, nacionalidad, telefono, fechaN);

					JOptionPane.showMessageDialog(
							null,
							"Registro actualizado éxitosamente.",
							"Actualización éxitosa.",
							JOptionPane.INFORMATION_MESSAGE
					);
				} else {
					JOptionPane.showMessageDialog(
							null,
							"No se han realizado cambios, por favor edita un campo.",
							"Sin cambios.",
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
			em.close();
		}
	}
}
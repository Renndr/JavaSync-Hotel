package com.hotel.Views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;

import com.hotel.Dao.ReservasDao;
import com.hotel.ErrorHandling.*;
import com.hotel.Factory.HuespedesFactory;
import com.hotel.Utils.JpaUtils;
import com.hotel.Models.Huespedes;

import javax.swing.JComboBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class RegistroHuesped extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtNreserva;
	private JDateChooser txtFechaN;
	private JComboBox<String> txtNacionalidad;
	private JLabel labelExit;
	private JLabel labelAtras;

	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroHuesped frame = new RegistroHuesped();
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
	public RegistroHuesped() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroHuesped.class.getResource("../images/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 634);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
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
		header.setBackground(SystemColor.text);
		header.setOpaque(false);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
       			 int result = JOptionPane.showConfirmDialog(null, "Se eliminarán todos los datos guardados.",
                "Confirmación", JOptionPane.YES_NO_OPTION);

        		if (result != JOptionPane.YES_OPTION) {
            		return; // Se cancela el evento
        		}				

				// El usuario seleccionó "Sí", se continúa con el evento
				ReservasView reservas = new ReservasView();
				reservas.setVisible(true);
				eliminar();
				dispose();
    	}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(new Color(12, 138, 199));
			     labelAtras.setForeground(Color.white);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(new Color(12, 138, 199));
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setForeground(Color.WHITE);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNombre.setBounds(560, 135, 285, 33);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setColumns(10);
		txtNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtNombre);
		
		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtApellido.setBounds(560, 204, 285, 33);
		txtApellido.setColumns(10);
		txtApellido.setBackground(Color.WHITE);
		txtApellido.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtApellido);
		
		txtFechaN = new JDateChooser();
		txtFechaN.setBounds(560, 278, 289, 36);
		txtFechaN.getCalendarButton().setIcon(new ImageIcon(RegistroHuesped.class.getResource("../images/icon-reservas.png")));
		txtFechaN.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaN.setDateFormatString("yyyy-MM-dd");
		contentPane.add(txtFechaN);
		
		txtNacionalidad = new JComboBox<>();
		txtNacionalidad.setBounds(560, 350, 289, 36);
		txtNacionalidad.setBackground(SystemColor.text);
		txtNacionalidad.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNacionalidad.setModel(new DefaultComboBoxModel<>(new String[] {"Afgano/a", "Alemán/a", "Arabe", "Argentino/a", "Australiano/a", "Belga", "Boliviano-/a", "Brasileño/a", "Camboyano/a", "Canadiense", "Chileno-chilena", "Chino/a", "Colombiano/a", "Coreano/a", "Costarricense", "Cubano/a", "Danés/a", "Ecuatoriano/a", "Egipcio/a", "Salvadoreño/a", "Escocés/a", "Español/a", "Estadounidense", "Estonio/a", "Etiope", "Filipino/a", "Finlandés/a", "Francés/a", "Galés/a", "Griego/a", "Guatemalteco/a", "Haitiano/a", "Holandés/a", "Hondureño/a", "Indonés/a", "Inglés/a", "Iraquí", "Iraní", "Irlandés/a", "Israelí", "Italiano/a", "Japonés/a", "Jordano/a", "Laosiano/a", "Letón/a", "Letonés/a", "Malayo/a", "Marroquí", "Mexicano/a", "Nicaragüense", "Noruego/a", "Neozelandés/a", "Panameño/a", "Paraguayo/a", "Peruano/a", "Polaco/a", "Portugués/a", "Puertorriqueño", "Dominicano/a", "Rumano/a", "Ruso/a", "Sueco/a", "Suizo/a", "Tailandés", "Taiwanes", "Turco/a", "Ucraniano/a", "Uruguayo/a", "Venezolano/a", "Vietnamita"}));
		contentPane.add(txtNacionalidad);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(562, 119, 253, 14);
		lblNombre.setForeground(SystemColor.textInactiveText);
		lblNombre.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setBounds(560, 189, 255, 14);
		lblApellido.setForeground(SystemColor.textInactiveText);
		lblApellido.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		contentPane.add(lblApellido);
		
		JLabel lblFechaN = new JLabel("FECHA DE NACIMIENTO");
		lblFechaN.setBounds(560, 256, 255, 14);
		lblFechaN.setForeground(SystemColor.textInactiveText);
		lblFechaN.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		contentPane.add(lblFechaN);
		
		JLabel lblNacionalidad = new JLabel("NACIONALIDAD");
		lblNacionalidad.setBounds(560, 326, 255, 14);
		lblNacionalidad.setForeground(SystemColor.textInactiveText);
		lblNacionalidad.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		contentPane.add(lblNacionalidad);
		
		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(562, 406, 253, 14);
		lblTelefono.setForeground(SystemColor.textInactiveText);
		lblTelefono.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		contentPane.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtTelefono.setBounds(560, 424, 285, 33);
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtTelefono.setDocument(new PhoneDocument());
		contentPane.add(txtTelefono);
		
		JLabel lblTitulo = new JLabel("REGISTRO HUÉSPED");
		lblTitulo.setBounds(606, 55, 234, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 21));
		contentPane.add(lblTitulo);
		
		JLabel lblNumeroReserva = new JLabel("NÚMERO DE RESERVA");
		lblNumeroReserva.setBounds(560, 474, 253, 14);
		lblNumeroReserva.setForeground(SystemColor.textInactiveText);
		lblNumeroReserva.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		contentPane.add(lblNumeroReserva);
		
		txtNreserva = new JTextField();
		txtNreserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNreserva.setBounds(560, 495, 285, 33);
		txtNreserva.setColumns(10);
		txtNreserva.setBackground(Color.WHITE);
		txtNreserva.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtNreserva.setEditable(false);
		consultaID();
		contentPane.add(txtNreserva);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(560, 170, 289, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);
		
		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(560, 240, 289, 2);
		separator_1_2_1.setForeground(new Color(12, 138, 199));
		separator_1_2_1.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_1);
		
		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(560, 314, 289, 2);
		separator_1_2_2.setForeground(new Color(12, 138, 199));
		separator_1_2_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_2);
		
		JSeparator separator_1_2_3 = new JSeparator();
		separator_1_2_3.setBounds(560, 386, 289, 2);
		separator_1_2_3.setForeground(new Color(12, 138, 199));
		separator_1_2_3.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_3);
		
		JSeparator separator_1_2_4 = new JSeparator();
		separator_1_2_4.setBounds(560, 457, 289, 2);
		separator_1_2_4.setForeground(new Color(12, 138, 199));
		separator_1_2_4.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_4);
		
		JSeparator separator_1_2_5 = new JSeparator();
		separator_1_2_5.setBounds(560, 529, 289, 2);
		separator_1_2_5.setForeground(new Color(12, 138, 199));
		separator_1_2_5.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_5);

		JButton btnguardar = new JButton();
		btnguardar.setLayout(null);
		btnguardar.setBackground(SystemColor.textHighlight);
		btnguardar.setBounds(723, 560, 122, 35);
		contentPane.add(btnguardar);
		btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnguardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtNombre.getText() != null && txtApellido.getText() != null && txtTelefono.getText() != null && txtFechaN.getDate() != null ) {		
					//Guardar cambios en tabla huespedes.
					guardaDatos();

					MenuUsuario menuUsuario = new MenuUsuario();
					menuUsuario.setVisible(true);
					dispose();
					JOptionPane.showMessageDialog(null, "Datos guardados con exito.");
				} else {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
					consultaID();
				}
			}						
		});
		
		JLabel labelGuardar = new JLabel("GUARDAR");
		labelGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		labelGuardar.setForeground(Color.WHITE);
		labelGuardar.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelGuardar.setBounds(0, 0, 122, 35);
		btnguardar.add(labelGuardar);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 489, 634);
		panel.setBackground(new Color(12, 138, 199));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 121, 479, 502);
		panel.add(imagenFondo);
		imagenFondo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("../images/registro.png")));
		
		JLabel logo = new JLabel("");
		logo.setBounds(194, 39, 104, 107);
		panel.add(logo);
		logo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("../images/Ha-100px.png")));
		
		JPanel btnexit = new JPanel();
		btnexit.setBounds(857, 0, 53, 36);
		contentPane.add(btnexit);
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainWindow principal = new MainWindow();
				principal.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.white);
		
		labelExit = new JLabel("X");
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(SystemColor.black);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
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

	private void guardaDatos() {
		
		HuespedesFactory huespedesFactory = new HuespedesFactory();
		Huespedes huespedes = new Huespedes();

		EntityManager em = JpaUtils.getEntityManager();
		ReservasDao reservasDao = new ReservasDao(em);
		Integer maxId = reservasDao.consultaPorMaxId();

		LocalDate fechaNacimiento = getSelectedDate(txtFechaN);

		huespedes.setNombre(txtNombre.getText());
		huespedes.setApellido(txtApellido.getText());
		huespedes.setFechaNacimiento(fechaNacimiento);
		huespedes.setNacionalidad(txtNacionalidad.getSelectedItem().toString());
		huespedes.setTelefono(txtTelefono.getText());
		huespedes.setNumeroReserva(maxId);
		huespedesFactory.save(huespedes);
	}

	private LocalDate getSelectedDate(JDateChooser dateChooser) {
		Date selectedDate = dateChooser.getDate();
		Instant instant = selectedDate.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}

	private void consultaID() {
		EntityManager em = JpaUtils.getEntityManager();
		ReservasDao reservasDao = new ReservasDao(em);
    	Integer maxId = reservasDao.consultaPorMaxId();
		
		//Agrega el numero id reserva al txt para ser guardado en la tabla huespedes.
		try {
			String idMax = maxId.toString();
			txtNreserva.setText(idMax);
			em.close();
		} catch (Exception e) {
			return;
		}

	}

	private void eliminar() {
		EntityManager em = JpaUtils.getEntityManager();
		ReservasDao reservasDao = new ReservasDao(em);
		EntityTransaction tx = em.getTransaction();
        tx.begin();
    	Integer maxId = reservasDao.consultaPorMaxId();
		reservasDao.removerPorId(maxId);
		tx.commit();
		em.close();
	}								
}

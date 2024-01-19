package com.hotel.ErrorHandling;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;


public class editarElementos {
    
    public void tablaJcomboBox(JTable tabla1, JTable tabla2) {
        String[] boxReservas = {"Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo"};
		JComboBox<String> comboBox = new JComboBox<>(boxReservas);
		comboBox.setFont(new Font("Roboto", Font.PLAIN, 14));
		tabla1.setRowHeight(comboBox.getPreferredSize().height);
		tabla1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox));

		String[] nacionalidad = {"Afgano/a", "Alemán/a", "Arabe", "Argentino/a", "Australiano/a", "Belga", "Boliviano-/a", "Brasileño/a", "Camboyano/a", "Canadiense", "Chileno-chilena", "Chino/a", "Colombiano/a", "Coreano/a", "Costarricense", "Cubano/a", "Danés/a", "Ecuatoriano/a", "Egipcio/a", "Salvadoreño/a", "Escocés/a", "Español/a", "Estadounidense", "Estonio/a", "Etiope", "Filipino/a", "Finlandés/a", "Francés/a", "Galés/a", "Griego/a", "Guatemalteco/a", "Haitiano/a", "Holandés/a", "Hondureño/a", "Indonés/a", "Inglés/a", "Iraquí", "Iraní", "Irlandés/a", "Israelí", "Italiano/a", "Japonés/a", "Jordano/a", "Laosiano/a", "Letón/a", "Letonés/a", "Malayo/a", "Marroquí", "Mexicano/a", "Nicaragüense", "Noruego/a", "Neozelandés/a", "Panameño/a", "Paraguayo/a", "Peruano/a", "Polaco/a", "Portugués/a", "Puertorriqueño", "Dominicano/a", "Rumano/a", "Ruso/a", "Sueco/a", "Suizo/a", "Tailandés", "Taiwanes", "Turco/a", "Ucraniano/a", "Uruguayo/a", "Venezolano/a", "Vietnamita"};
		JComboBox<String> boxHuesped = new JComboBox<>(nacionalidad);
		boxHuesped.setFont(new Font("Roboto", Font.PLAIN, 14));
		tabla2.setRowHeight(boxHuesped.getPreferredSize().height);
		tabla2.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(boxHuesped));

    }

    public void centrarContenido(JTable table1, JTable table2) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table1.setDefaultRenderer(Object.class, centerRenderer);
        table2.setDefaultRenderer(Object.class, centerRenderer);
    }

    public void setNumericColumn(JTable table, int columnNumber, JTable table2, int columnNumber2) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
        
        table.getColumnModel().getColumn(columnNumber).setCellEditor(new DefaultCellEditor(new JFormattedTextField(factory)));
        table2.getColumnModel().getColumn(columnNumber2).setCellEditor(new DefaultCellEditor(new JFormattedTextField(factory)));
    }

    public void editarLocalDate(JTable table1, JTable table2) {
        TableColumn column1 = table1.getColumnModel().getColumn(1);
		column1.setCellEditor(new LocalDateCellEditor());

		TableColumn column2 = table1.getColumnModel().getColumn(2);
		column2.setCellEditor(new LocalDateCellEditor());

        TableColumn column3 = table2.getColumnModel().getColumn(3);
		column3.setCellEditor(new LocalDateCellEditor());

    }

    public void buscarTabla(JTable t1, JTable t2, DefaultTableModel tb1, DefaultTableModel tb2, JTextField txtBuscar) {

      	TableRowSorter<TableModel> sorter = new TableRowSorter<>(tb1);
		t1.setRowSorter(sorter);

		txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(txtBuscar.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				search(txtBuscar.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				search(txtBuscar.getText());
			}
			public void search(String str) {
				if (str.length() == 0) {
				sorter.setRowFilter(null);
				} else {
				sorter.setRowFilter(RowFilter.regexFilter(str));
				}
			}
      });

        TableRowSorter<TableModel> sorter2 = new TableRowSorter<>(tb2);
		t2.setRowSorter(sorter2);

		txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(txtBuscar.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				search(txtBuscar.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				search(txtBuscar.getText());
			}
			public void search(String str) {
				if (str.length() == 0) {
				sorter2.setRowFilter(null);
				} else {
				sorter2.setRowFilter(RowFilter.regexFilter(str));
				}
			}
      });
    }

	public void preTexto(JTextField textField) {
		        
		textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> handleTextChange());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> handleTextChange());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> handleTextChange());
            }

            private void handleTextChange() {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setFont(textField.getFont().deriveFont(Font.ITALIC));
                    textField.setText("Enviar mensaje...");
                } else {
                    textField.setForeground(Color.BLACK);
                    textField.setFont(textField.getFont().deriveFont(Font.PLAIN));
                }
            }
        });
	}
    
}

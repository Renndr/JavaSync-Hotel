package com.hotel.ErrorHandling;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.toedter.calendar.JDateChooser;

public class LocalDateCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JDateChooser dateChooser;

    public LocalDateCellEditor() {
        dateChooser = new JDateChooser();
        
        // Agregar escuchador de teclado al JDateChooser 
        dateChooser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Verificar si la fecha se ha modificado antes de cerrar la celda
                    if (!dateChooser.getDate().equals(getCellEditorValue())) {
                        stopCellEditing();
                    }
                }
            }
        });
    }
    
    @Override
    public Object getCellEditorValue() {
        Date selectedDate = dateChooser.getDate();
        return selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        LocalDate localDate = (LocalDate) value;
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateChooser.setDate(date);
        return dateChooser;
    }
}
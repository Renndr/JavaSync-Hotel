package com.hotel.ErrorHandling;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class PhoneNumberTextField extends JTextField {

    public PhoneNumberTextField() {
        super();
        agregarPatronTelefono();
    }

    private void agregarPatronTelefono() {
        AbstractDocument document = (AbstractDocument) getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            private static final String PATRON_TELEFONO = "\\d{3}-\\d{3}-\\d{4}";

            @Override
            public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr)
                    throws BadLocationException {
                StringBuilder builder = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                builder.insert(offset, str);

                if (!validarTelefono(builder.toString())) {
                    return;
                }

                super.insertString(fb, offset, str, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
                    throws BadLocationException {
                StringBuilder builder = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                builder.replace(offset, offset + length, str);

                if (!validarTelefono(builder.toString())) {
                    return;
                }

                super.replace(fb, offset, length, str, attrs);
            }

            private boolean validarTelefono(String telefono) {
                return telefono.matches(PATRON_TELEFONO);
            }
        });
    }
}

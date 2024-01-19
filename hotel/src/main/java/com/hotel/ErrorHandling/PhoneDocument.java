package com.hotel.ErrorHandling;

import javax.swing.text.*;

public  class PhoneDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            // Verifica que el nuevo texto solo contenga dígitos y formato numero telefono
            String newStr = getText(0, offs) + str + getText(offs, getLength() - offs);
            if (newStr.matches("\\d{0,12}")) {
                super.insertString(offs, str, a);
            }
        }
}
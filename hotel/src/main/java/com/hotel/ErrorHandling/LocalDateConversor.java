package com.hotel.ErrorHandling;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

public class LocalDateConversor {
    
    public LocalDate getSelectedDate(JDateChooser dateChooser) {
    Date selectedDate = dateChooser.getDate();
    Instant instant = selectedDate.toInstant();
    LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate;
    }
}

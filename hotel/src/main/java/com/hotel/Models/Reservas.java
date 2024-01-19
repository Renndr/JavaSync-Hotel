package com.hotel.Models;

import java.time.LocalDate;

import javax.persistence.*;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Reservas")
public class Reservas {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Numero_reserva") private Integer numeroReserva;
    
    @NotBlank
    @Column(name = "Fecha_check_in") private LocalDate checkIn;

    @NotBlank
    @Column(name = "Fecha_check_out") private LocalDate checkOut;

    @NotBlank
    @Column(name = "Valor_reserva") private String valorReserva;

    @NotBlank
    @Column(name = "Forma_pago") private String formaPago;

    public Reservas(){
    
    }
    
    public Integer getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(Integer numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public String getValorReserva() {
        return valorReserva;
    }

    public void setValorReserva(String valorReserva) {
        this.valorReserva = valorReserva;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

}


 

 
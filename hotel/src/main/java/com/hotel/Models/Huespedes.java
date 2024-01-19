package com.hotel.Models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Huespedes")
public class Huespedes {
    
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Numero_huesped") private Integer numeroHuesped;
 
    @NotBlank
    @Column(name = "Nombre") private String nombre;
 
    @NotBlank
    @Column(name = "Apellido") private String apellido;

    @NotBlank
    @Column(name = "Fecha_nacimiento") private LocalDate FechaNacimiento;

    @NotBlank
    @Column(name = "Nacionalidad") private String nacionalidad;

    @NotBlank
    @Column(name = "Telefono") private String telefono;

    @NotBlank
    @Column(name = "Numero_reserva") private Integer numeroReserva;

    public Huespedes() {

    }

    public Integer getNumeroHuesped() {
        return numeroHuesped;
    }

    public void setNumeroHuesped(Integer numeroHuesped) {
        this.numeroHuesped = numeroHuesped;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(Integer numeroReserva) {
        this.numeroReserva = numeroReserva;
    }
}

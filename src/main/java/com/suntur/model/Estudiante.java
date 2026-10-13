package com.suntur.model;

import java.time.LocalDate;

/** Estudiante de la institución (tabla estudiante). */
public class Estudiante {
    private int id;
    private String dni;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String apoderado;
    private String telefonoApoderado;
    private String estado;

    public Estudiante() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getApoderado() { return apoderado; }
    public void setApoderado(String apoderado) { this.apoderado = apoderado; }
    public String getTelefonoApoderado() { return telefonoApoderado; }
    public void setTelefonoApoderado(String telefonoApoderado) { this.telefonoApoderado = telefonoApoderado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNombreCompleto() { return apellidos + ", " + nombres; }
}

package com.suntur.dto;

import java.time.LocalDate;

/** Matrícula con datos del estudiante y la sección, lista para mostrar en pantalla. */
public class MatriculaDTO {
    private int id;
    private int anio;
    private int seccionId;
    private String dni;
    private String estudiante;
    private String seccion;
    private String estado;
    private LocalDate fecha;

    public MatriculaDTO() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getSeccionId() { return seccionId; }
    public void setSeccionId(int seccionId) { this.seccionId = seccionId; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}

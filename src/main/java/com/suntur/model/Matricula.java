package com.suntur.model;

import java.time.LocalDate;

/** Matrícula de un estudiante en una sección para un año (tabla matricula). */
public class Matricula {
    private int id;
    private int estudianteId;
    private int seccionId;
    private int anio;
    private LocalDate fecha;
    private String estado;

    public Matricula() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }
    public int getSeccionId() { return seccionId; }
    public void setSeccionId(int seccionId) { this.seccionId = seccionId; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

package com.suntur.model;

import java.time.LocalDate;

/** Asistencia diaria. Estado: P presente, T tardanza, F falta (tabla asistencia). */
public class Asistencia {
    private int id;
    private int matriculaId;
    private LocalDate fecha;
    private String estado;

    public Asistencia() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMatriculaId() { return matriculaId; }
    public void setMatriculaId(int matriculaId) { this.matriculaId = matriculaId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

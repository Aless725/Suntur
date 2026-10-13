package com.suntur.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Actividad complementaria con un monto a cobrar (tabla actividad). */
public class Actividad {
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal monto;
    private LocalDate fecha;

    public Actividad() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}

package com.suntur.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Cobro de una actividad a una matrícula. Estado: PENDIENTE o PAGADO (tabla pago). */
public class Pago {
    private int id;
    private int matriculaId;
    private int actividadId;
    private BigDecimal monto;
    private LocalDate fechaPago;
    private String estado;

    public Pago() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMatriculaId() { return matriculaId; }
    public void setMatriculaId(int matriculaId) { this.matriculaId = matriculaId; }
    public int getActividadId() { return actividadId; }
    public void setActividadId(int actividadId) { this.actividadId = actividadId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

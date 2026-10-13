package com.suntur.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Pago con nombre de estudiante y actividad, listo para mostrar. */
public class PagoDTO {
    private int id;
    private String estudiante;
    private String seccion;
    private String actividad;
    private BigDecimal monto;
    private String estado;
    private LocalDate fechaPago;

    public PagoDTO() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
}

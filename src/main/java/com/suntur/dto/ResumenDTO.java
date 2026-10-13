package com.suntur.dto;

import java.math.BigDecimal;

/** Indicadores generales del tablero (dashboard). */
public class ResumenDTO {
    private int estudiantes;
    private int matriculas;
    private int usuarios;
    private int pagosPendientesCantidad;
    private BigDecimal pagosPendientes;
    private BigDecimal pagosCobrados;
    private int presentesHoy;
    private int tardanzasHoy;
    private int faltasHoy;

    public ResumenDTO() {}

    public int getEstudiantes() { return estudiantes; }
    public void setEstudiantes(int estudiantes) { this.estudiantes = estudiantes; }
    public int getMatriculas() { return matriculas; }
    public void setMatriculas(int matriculas) { this.matriculas = matriculas; }
    public int getUsuarios() { return usuarios; }
    public void setUsuarios(int usuarios) { this.usuarios = usuarios; }
    public int getPagosPendientesCantidad() { return pagosPendientesCantidad; }
    public void setPagosPendientesCantidad(int pagosPendientesCantidad) { this.pagosPendientesCantidad = pagosPendientesCantidad; }
    public BigDecimal getPagosPendientes() { return pagosPendientes; }
    public void setPagosPendientes(BigDecimal pagosPendientes) { this.pagosPendientes = pagosPendientes; }
    public BigDecimal getPagosCobrados() { return pagosCobrados; }
    public void setPagosCobrados(BigDecimal pagosCobrados) { this.pagosCobrados = pagosCobrados; }
    public int getPresentesHoy() { return presentesHoy; }
    public void setPresentesHoy(int presentesHoy) { this.presentesHoy = presentesHoy; }
    public int getTardanzasHoy() { return tardanzasHoy; }
    public void setTardanzasHoy(int tardanzasHoy) { this.tardanzasHoy = tardanzasHoy; }
    public int getFaltasHoy() { return faltasHoy; }
    public void setFaltasHoy(int faltasHoy) { this.faltasHoy = faltasHoy; }
}

package com.suntur.dto;

import java.util.List;

/** Boleta de notas completa de una matrícula. */
public class BoletaDTO {
    private MatriculaDTO matricula;
    private List<BoletaFilaDTO> filas;
    private Double promedioGeneral;

    public BoletaDTO() {}

    public MatriculaDTO getMatricula() { return matricula; }
    public void setMatricula(MatriculaDTO matricula) { this.matricula = matricula; }
    public List<BoletaFilaDTO> getFilas() { return filas; }
    public void setFilas(List<BoletaFilaDTO> filas) { this.filas = filas; }
    public Double getPromedioGeneral() { return promedioGeneral; }
    public void setPromedioGeneral(Double promedioGeneral) { this.promedioGeneral = promedioGeneral; }
}

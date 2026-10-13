package com.suntur.dto;

/** Fila de la boleta de notas: un curso con sus cuatro bimestres y su promedio. */
public class BoletaFilaDTO {
    private String curso;
    private Integer b1;
    private Integer b2;
    private Integer b3;
    private Integer b4;
    private Double promedio;

    public BoletaFilaDTO() {}

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public Integer getB1() { return b1; }
    public void setB1(Integer b1) { this.b1 = b1; }
    public Integer getB2() { return b2; }
    public void setB2(Integer b2) { this.b2 = b2; }
    public Integer getB3() { return b3; }
    public void setB3(Integer b3) { this.b3 = b3; }
    public Integer getB4() { return b4; }
    public void setB4(Integer b4) { this.b4 = b4; }
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}

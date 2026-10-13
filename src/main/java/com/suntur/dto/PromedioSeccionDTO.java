package com.suntur.dto;

/** Promedio de notas por sección, para reportes. */
public class PromedioSeccionDTO {
    private String seccion;
    private int alumnos;
    private Double promedio;

    public PromedioSeccionDTO() {}

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    public int getAlumnos() { return alumnos; }
    public void setAlumnos(int alumnos) { this.alumnos = alumnos; }
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}

package com.suntur.model;

/** Sección de un grado, por ejemplo Primaria 3° A (tabla seccion). */
public class Seccion {
    private int id;
    private String nivel;
    private int grado;
    private String nombre;

    public Seccion() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public int getGrado() { return grado; }
    public void setGrado(int grado) { this.grado = grado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEtiqueta() { return nivel + " " + grado + "° " + nombre; }
}

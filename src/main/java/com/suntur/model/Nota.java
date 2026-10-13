package com.suntur.model;

/** Calificación vigesimal (0-20) por curso y bimestre (tabla nota). */
public class Nota {
    private int id;
    private int matriculaId;
    private int cursoId;
    private int bimestre;
    private int valor;

    public Nota() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMatriculaId() { return matriculaId; }
    public void setMatriculaId(int matriculaId) { this.matriculaId = matriculaId; }
    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
    public int getBimestre() { return bimestre; }
    public void setBimestre(int bimestre) { this.bimestre = bimestre; }
    public int getValor() { return valor; }
    public void setValor(int valor) { this.valor = valor; }
}

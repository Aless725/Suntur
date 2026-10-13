package com.suntur.dto;

/** Fila de registro de notas: estudiante y su nota (null si aún no tiene). */
public class NotaDTO {
    private int matriculaId;
    private String estudiante;
    private Integer valor;

    public NotaDTO() {}

    public int getMatriculaId() { return matriculaId; }
    public void setMatriculaId(int matriculaId) { this.matriculaId = matriculaId; }
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
}

package com.suntur.dto;

/** Fila de registro de asistencia: estudiante y su estado del día (null si aún no se marca). */
public class AsistenciaDTO {
    private int matriculaId;
    private String estudiante;
    private String estado;

    public AsistenciaDTO() {}

    public int getMatriculaId() { return matriculaId; }
    public void setMatriculaId(int matriculaId) { this.matriculaId = matriculaId; }
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

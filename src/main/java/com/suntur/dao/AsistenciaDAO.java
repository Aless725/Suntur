package com.suntur.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.dto.AsistenciaDTO;

@Repository
public class AsistenciaDAO extends BaseDAO {

    public AsistenciaDAO(Conexion cn) {
        super(cn);
    }

    /** Alumnos de la sección con su asistencia del día (estado null si no se ha marcado). */
    public List<AsistenciaDTO> listarPorSeccion(int seccionId, int anio, LocalDate fecha) {
        return consultar(
                "SELECT m.id AS matricula_id, e.apellidos, e.nombres, a.estado "
              + "FROM matricula m "
              + "JOIN estudiante e ON e.id = m.estudiante_id "
              + "LEFT JOIN asistencia a ON a.matricula_id = m.id AND a.fecha = ? "
              + "WHERE m.seccion_id = ? AND m.anio = ? AND m.estado = 'ACTIVA' "
              + "ORDER BY e.apellidos, e.nombres",
                rs -> {
                    AsistenciaDTO d = new AsistenciaDTO();
                    d.setMatriculaId(rs.getInt("matricula_id"));
                    d.setEstudiante(rs.getString("apellidos") + ", " + rs.getString("nombres"));
                    d.setEstado(rs.getString("estado"));
                    return d;
                }, fecha, seccionId, anio);
    }

    /** Inserta o actualiza la asistencia (clave: matrícula + fecha). */
    public void guardar(int matriculaId, LocalDate fecha, String estado) {
        int filas = ejecutar("UPDATE asistencia SET estado = ? WHERE matricula_id = ? AND fecha = ?",
                estado, matriculaId, fecha);
        if (filas == 0) {
            ejecutar("INSERT INTO asistencia(matricula_id, fecha, estado) VALUES (?,?,?)",
                    matriculaId, fecha, estado);
        }
    }
}

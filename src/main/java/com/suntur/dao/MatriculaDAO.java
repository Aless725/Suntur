package com.suntur.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.dto.MatriculaDTO;
import com.suntur.model.Matricula;

@Repository
public class MatriculaDAO extends BaseDAO {

    private static final String SELECT_DTO =
            "SELECT m.id, m.anio, m.fecha, m.estado, e.dni, e.apellidos, e.nombres, "
          + "s.id AS seccion_id, s.nivel, s.grado, s.nombre AS seccion_nombre "
          + "FROM matricula m "
          + "JOIN estudiante e ON e.id = m.estudiante_id "
          + "JOIN seccion s ON s.id = m.seccion_id ";

    public MatriculaDAO(Conexion cn) {
        super(cn);
    }

    private static MatriculaDTO mapearDTO(ResultSet rs) throws SQLException {
        MatriculaDTO d = new MatriculaDTO();
        d.setId(rs.getInt("id"));
        d.setAnio(rs.getInt("anio"));
        d.setFecha(fecha(rs, "fecha"));
        d.setEstado(rs.getString("estado"));
        d.setDni(rs.getString("dni"));
        d.setEstudiante(rs.getString("apellidos") + ", " + rs.getString("nombres"));
        d.setSeccionId(rs.getInt("seccion_id"));
        d.setSeccion(rs.getString("nivel") + " " + rs.getInt("grado") + "° " + rs.getString("seccion_nombre"));
        return d;
    }

    /** Matrículas de un año, con datos del estudiante y la sección. */
    public List<MatriculaDTO> listarPorAnio(int anio) {
        return consultar(SELECT_DTO + "WHERE m.anio = ? ORDER BY m.id DESC", MatriculaDAO::mapearDTO, anio);
    }

    public MatriculaDTO buscarDTO(int id) {
        return uno(SELECT_DTO + "WHERE m.id = ?", MatriculaDAO::mapearDTO, id);
    }

    public int registrar(Matricula m) {
        return insertar("INSERT INTO matricula(estudiante_id, seccion_id, anio, fecha, estado) VALUES (?,?,?,?, 'ACTIVA')",
                m.getEstudianteId(), m.getSeccionId(), m.getAnio(), m.getFecha());
    }

    /** True si el estudiante ya tiene una matrícula ACTIVA en ese año. */
    public boolean existeActiva(int estudianteId, int anio) {
        return contar("SELECT COUNT(*) FROM matricula WHERE estudiante_id = ? AND anio = ? AND estado = 'ACTIVA'",
                estudianteId, anio) > 0;
    }

    public void anular(int id) {
        ejecutar("UPDATE matricula SET estado = 'ANULADA' WHERE id = ?", id);
    }

    public int contarActivas(int anio) {
        return contar("SELECT COUNT(*) FROM matricula WHERE anio = ? AND estado = 'ACTIVA'", anio);
    }
}

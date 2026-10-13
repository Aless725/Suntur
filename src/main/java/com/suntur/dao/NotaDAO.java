package com.suntur.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.dto.NotaDTO;

@Repository
public class NotaDAO extends BaseDAO {

    public NotaDAO(Conexion cn) {
        super(cn);
    }

    /** Alumnos matriculados en la sección con su nota (o null) para el curso y bimestre indicados. */
    public List<NotaDTO> listarPorSeccion(int seccionId, int anio, int cursoId, int bimestre) {
        return consultar(
                "SELECT m.id AS matricula_id, e.apellidos, e.nombres, n.valor "
              + "FROM matricula m "
              + "JOIN estudiante e ON e.id = m.estudiante_id "
              + "LEFT JOIN nota n ON n.matricula_id = m.id AND n.curso_id = ? AND n.bimestre = ? "
              + "WHERE m.seccion_id = ? AND m.anio = ? AND m.estado = 'ACTIVA' "
              + "ORDER BY e.apellidos, e.nombres",
                rs -> {
                    NotaDTO d = new NotaDTO();
                    d.setMatriculaId(rs.getInt("matricula_id"));
                    d.setEstudiante(rs.getString("apellidos") + ", " + rs.getString("nombres"));
                    d.setValor(entero(rs, "valor"));
                    return d;
                }, cursoId, bimestre, seccionId, anio);
    }

    /** Inserta o actualiza la nota (clave: matrícula + curso + bimestre). */
    public void guardar(int matriculaId, int cursoId, int bimestre, int valor) {
        int filas = ejecutar("UPDATE nota SET valor = ? WHERE matricula_id = ? AND curso_id = ? AND bimestre = ?",
                valor, matriculaId, cursoId, bimestre);
        if (filas == 0) {
            ejecutar("INSERT INTO nota(matricula_id, curso_id, bimestre, valor) VALUES (?,?,?,?)",
                    matriculaId, cursoId, bimestre, valor);
        }
    }

    /** Notas de una matrícula: una fila por (curso, bimestre) registrada. */
    public List<Object[]> notasDeMatricula(int matriculaId) {
        return consultar(
                "SELECT c.nombre, n.bimestre, n.valor FROM curso c "
              + "LEFT JOIN nota n ON n.curso_id = c.id AND n.matricula_id = ? "
              + "ORDER BY c.nombre, n.bimestre",
                rs -> new Object[] { rs.getString("nombre"), entero(rs, "bimestre"), entero(rs, "valor") },
                matriculaId);
    }
}

package com.suntur.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.dto.PromedioSeccionDTO;
import com.suntur.dto.ResumenDTO;

/** Consultas agregadas para el tablero y los reportes. */
@Repository
public class ReporteDAO extends BaseDAO {

    public ReporteDAO(Conexion cn) {
        super(cn);
    }

    public ResumenDTO resumen(int anio, LocalDate hoy) {
        ResumenDTO r = new ResumenDTO();
        r.setEstudiantes(contar("SELECT COUNT(*) FROM estudiante WHERE estado = 'ACTIVO'"));
        r.setMatriculas(contar("SELECT COUNT(*) FROM matricula WHERE anio = ? AND estado = 'ACTIVA'", anio));
        r.setUsuarios(contar("SELECT COUNT(*) FROM usuario WHERE activo = TRUE"));
        r.setPagosPendientesCantidad(contar(
                "SELECT COUNT(*) FROM pago p JOIN matricula m ON m.id = p.matricula_id "
              + "WHERE m.anio = ? AND p.estado = 'PENDIENTE'", anio));
        r.setPagosPendientes(sumaPagos(anio, "PENDIENTE"));
        r.setPagosCobrados(sumaPagos(anio, "PAGADO"));
        r.setPresentesHoy(asistenciaDelDia(anio, hoy, "P"));
        r.setTardanzasHoy(asistenciaDelDia(anio, hoy, "T"));
        r.setFaltasHoy(asistenciaDelDia(anio, hoy, "F"));
        return r;
    }

    private BigDecimal sumaPagos(int anio, String estado) {
        BigDecimal total = uno(
                "SELECT COALESCE(SUM(p.monto), 0) FROM pago p JOIN matricula m ON m.id = p.matricula_id "
              + "WHERE m.anio = ? AND p.estado = ?",
                rs -> rs.getBigDecimal(1), anio, estado);
        return total == null ? BigDecimal.ZERO : total;
    }

    private int asistenciaDelDia(int anio, LocalDate fecha, String estado) {
        return contar("SELECT COUNT(*) FROM asistencia a JOIN matricula m ON m.id = a.matricula_id "
                + "WHERE m.anio = ? AND a.fecha = ? AND a.estado = ?", anio, fecha, estado);
    }

    /** Promedio de todas las notas registradas por sección. */
    public List<PromedioSeccionDTO> promediosPorSeccion(int anio) {
        return consultar(
                "SELECT s.nivel, s.grado, s.nombre, COUNT(DISTINCT m.id) AS alumnos, AVG(n.valor) AS promedio "
              + "FROM seccion s "
              + "JOIN matricula m ON m.seccion_id = s.id AND m.anio = ? AND m.estado = 'ACTIVA' "
              + "LEFT JOIN nota n ON n.matricula_id = m.id "
              + "GROUP BY s.id, s.nivel, s.grado, s.nombre "
              + "ORDER BY s.nivel, s.grado, s.nombre",
                rs -> {
                    PromedioSeccionDTO d = new PromedioSeccionDTO();
                    d.setSeccion(rs.getString("nivel") + " " + rs.getInt("grado") + "° " + rs.getString("nombre"));
                    d.setAlumnos(rs.getInt("alumnos"));
                    d.setPromedio(decimal(rs, "promedio"));
                    return d;
                }, anio);
    }
}

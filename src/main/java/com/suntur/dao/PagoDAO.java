package com.suntur.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.dto.PagoDTO;

@Repository
public class PagoDAO extends BaseDAO {

    public PagoDAO(Conexion cn) {
        super(cn);
    }

    /** Pagos del año; si estado es null o vacío se listan todos. */
    public List<PagoDTO> listar(int anio, String estado) {
        boolean filtrar = estado != null && !estado.isBlank();
        String sql = "SELECT p.id, p.monto, p.estado, p.fecha_pago, a.nombre AS actividad, "
                + "e.apellidos, e.nombres, s.nivel, s.grado, s.nombre AS seccion_nombre "
                + "FROM pago p "
                + "JOIN matricula m ON m.id = p.matricula_id "
                + "JOIN estudiante e ON e.id = m.estudiante_id "
                + "JOIN seccion s ON s.id = m.seccion_id "
                + "JOIN actividad a ON a.id = p.actividad_id "
                + "WHERE m.anio = ? "
                + (filtrar ? "AND p.estado = ? " : "")
                + "ORDER BY p.estado DESC, e.apellidos, e.nombres, p.id";
        Object[] params = filtrar ? new Object[] { anio, estado } : new Object[] { anio };
        return consultar(sql, rs -> {
            PagoDTO d = new PagoDTO();
            d.setId(rs.getInt("id"));
            d.setMonto(rs.getBigDecimal("monto"));
            d.setEstado(rs.getString("estado"));
            d.setFechaPago(fecha(rs, "fecha_pago"));
            d.setActividad(rs.getString("actividad"));
            d.setEstudiante(rs.getString("apellidos") + ", " + rs.getString("nombres"));
            d.setSeccion(rs.getString("nivel") + " " + rs.getInt("grado") + "° " + rs.getString("seccion_nombre"));
            return d;
        }, params);
    }

    /**
     * Genera un cobro PENDIENTE de la actividad para cada matrícula activa de la sección
     * que aún no lo tenga. Devuelve cuántos cobros se crearon.
     */
    public int asignarASeccion(int actividadId, int seccionId, int anio) {
        return ejecutar(
                "INSERT INTO pago(matricula_id, actividad_id, monto, estado) "
              + "SELECT m.id, a.id, a.monto, 'PENDIENTE' FROM matricula m CROSS JOIN actividad a "
              + "WHERE a.id = ? AND m.seccion_id = ? AND m.anio = ? AND m.estado = 'ACTIVA' "
              + "AND NOT EXISTS (SELECT 1 FROM pago p WHERE p.matricula_id = m.id AND p.actividad_id = a.id)",
                actividadId, seccionId, anio);
    }

    /** Marca el pago como PAGADO. Devuelve true si estaba pendiente. */
    public boolean marcarPagado(int pagoId, LocalDate fecha) {
        return ejecutar("UPDATE pago SET estado = 'PAGADO', fecha_pago = ? WHERE id = ? AND estado = 'PENDIENTE'",
                fecha, pagoId) > 0;
    }
}

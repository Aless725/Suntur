package com.suntur.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.model.Estudiante;

@Repository
public class EstudianteDAO extends BaseDAO implements CrudDAO<Estudiante> {

    public EstudianteDAO(Conexion cn) {
        super(cn);
    }

    private static Estudiante mapear(java.sql.ResultSet rs) throws java.sql.SQLException {
        Estudiante e = new Estudiante();
        e.setId(rs.getInt("id"));
        e.setDni(rs.getString("dni"));
        e.setNombres(rs.getString("nombres"));
        e.setApellidos(rs.getString("apellidos"));
        e.setFechaNacimiento(fecha(rs, "fecha_nacimiento"));
        e.setDireccion(rs.getString("direccion"));
        e.setApoderado(rs.getString("apoderado"));
        e.setTelefonoApoderado(rs.getString("telefono_apoderado"));
        e.setEstado(rs.getString("estado"));
        return e;
    }

    @Override
    public List<Estudiante> listar() {
        return buscar("");
    }

    /** Busca estudiantes activos por DNI, nombres o apellidos. */
    public List<Estudiante> buscar(String texto) {
        String like = "%" + (texto == null ? "" : texto.trim().toLowerCase()) + "%";
        return consultar("SELECT * FROM estudiante WHERE estado = 'ACTIVO' "
                + "AND (dni LIKE ? OR LOWER(nombres) LIKE ? OR LOWER(apellidos) LIKE ?) "
                + "ORDER BY apellidos, nombres", EstudianteDAO::mapear, like, like, like);
    }

    @Override
    public Estudiante buscarPorId(int id) {
        return uno("SELECT * FROM estudiante WHERE id = ?", EstudianteDAO::mapear, id);
    }

    /** True si ya existe otro estudiante (distinto de excluirId) con ese DNI. */
    public boolean existeDni(String dni, int excluirId) {
        return contar("SELECT COUNT(*) FROM estudiante WHERE dni = ? AND id <> ?", dni, excluirId) > 0;
    }

    @Override
    public int guardar(Estudiante e) {
        if (e.getId() == 0) {
            return insertar("INSERT INTO estudiante(dni, nombres, apellidos, fecha_nacimiento, direccion, "
                    + "apoderado, telefono_apoderado, estado) VALUES (?,?,?,?,?,?,?,'ACTIVO')",
                    e.getDni(), e.getNombres(), e.getApellidos(), e.getFechaNacimiento(), e.getDireccion(),
                    e.getApoderado(), e.getTelefonoApoderado());
        }
        ejecutar("UPDATE estudiante SET dni=?, nombres=?, apellidos=?, fecha_nacimiento=?, direccion=?, "
                + "apoderado=?, telefono_apoderado=? WHERE id=?",
                e.getDni(), e.getNombres(), e.getApellidos(), e.getFechaNacimiento(), e.getDireccion(),
                e.getApoderado(), e.getTelefonoApoderado(), e.getId());
        return e.getId();
    }

    /** Baja lógica: se conserva el historial de matrículas y notas. */
    public void desactivar(int id) {
        ejecutar("UPDATE estudiante SET estado = 'INACTIVO' WHERE id = ?", id);
    }
}

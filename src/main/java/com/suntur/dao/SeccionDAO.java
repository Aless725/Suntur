package com.suntur.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.model.Seccion;

@Repository
public class SeccionDAO extends BaseDAO implements CrudDAO<Seccion> {

    public SeccionDAO(Conexion cn) {
        super(cn);
    }

    private static Seccion mapear(java.sql.ResultSet rs) throws java.sql.SQLException {
        Seccion s = new Seccion();
        s.setId(rs.getInt("id"));
        s.setNivel(rs.getString("nivel"));
        s.setGrado(rs.getInt("grado"));
        s.setNombre(rs.getString("nombre"));
        return s;
    }

    @Override
    public List<Seccion> listar() {
        return consultar("SELECT * FROM seccion ORDER BY nivel, grado, nombre", SeccionDAO::mapear);
    }

    @Override
    public Seccion buscarPorId(int id) {
        return uno("SELECT * FROM seccion WHERE id = ?", SeccionDAO::mapear, id);
    }

    @Override
    public int guardar(Seccion s) {
        if (s.getId() == 0) {
            return insertar("INSERT INTO seccion(nivel, grado, nombre) VALUES (?,?,?)",
                    s.getNivel(), s.getGrado(), s.getNombre());
        }
        ejecutar("UPDATE seccion SET nivel=?, grado=?, nombre=? WHERE id=?",
                s.getNivel(), s.getGrado(), s.getNombre(), s.getId());
        return s.getId();
    }

    public int contar() {
        return contar("SELECT COUNT(*) FROM seccion");
    }
}

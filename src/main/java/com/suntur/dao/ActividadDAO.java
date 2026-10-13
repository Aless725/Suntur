package com.suntur.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.model.Actividad;

@Repository
public class ActividadDAO extends BaseDAO implements CrudDAO<Actividad> {

    public ActividadDAO(Conexion cn) {
        super(cn);
    }

    private static Actividad mapear(java.sql.ResultSet rs) throws java.sql.SQLException {
        Actividad a = new Actividad();
        a.setId(rs.getInt("id"));
        a.setNombre(rs.getString("nombre"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setMonto(rs.getBigDecimal("monto"));
        a.setFecha(fecha(rs, "fecha"));
        return a;
    }

    @Override
    public List<Actividad> listar() {
        return consultar("SELECT * FROM actividad ORDER BY id DESC", ActividadDAO::mapear);
    }

    @Override
    public Actividad buscarPorId(int id) {
        return uno("SELECT * FROM actividad WHERE id = ?", ActividadDAO::mapear, id);
    }

    @Override
    public int guardar(Actividad a) {
        if (a.getId() == 0) {
            return insertar("INSERT INTO actividad(nombre, descripcion, monto, fecha) VALUES (?,?,?,?)",
                    a.getNombre(), a.getDescripcion(), a.getMonto(), a.getFecha());
        }
        ejecutar("UPDATE actividad SET nombre=?, descripcion=?, monto=?, fecha=? WHERE id=?",
                a.getNombre(), a.getDescripcion(), a.getMonto(), a.getFecha(), a.getId());
        return a.getId();
    }

    public int contar() {
        return contar("SELECT COUNT(*) FROM actividad");
    }
}

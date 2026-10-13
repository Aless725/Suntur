package com.suntur.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.model.Curso;

@Repository
public class CursoDAO extends BaseDAO implements CrudDAO<Curso> {

    public CursoDAO(Conexion cn) {
        super(cn);
    }

    private static Curso mapear(java.sql.ResultSet rs) throws java.sql.SQLException {
        Curso c = new Curso();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        return c;
    }

    @Override
    public List<Curso> listar() {
        return consultar("SELECT * FROM curso ORDER BY nombre", CursoDAO::mapear);
    }

    @Override
    public Curso buscarPorId(int id) {
        return uno("SELECT * FROM curso WHERE id = ?", CursoDAO::mapear, id);
    }

    @Override
    public int guardar(Curso c) {
        if (c.getId() == 0) {
            return insertar("INSERT INTO curso(nombre) VALUES (?)", c.getNombre());
        }
        ejecutar("UPDATE curso SET nombre=? WHERE id=?", c.getNombre(), c.getId());
        return c.getId();
    }

    public int contar() {
        return contar("SELECT COUNT(*) FROM curso");
    }
}

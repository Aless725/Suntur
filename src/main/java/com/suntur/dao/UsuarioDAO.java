package com.suntur.dao;

import org.springframework.stereotype.Repository;

import com.suntur.config.Conexion;
import com.suntur.model.Usuario;

@Repository
public class UsuarioDAO extends BaseDAO {

    public UsuarioDAO(Conexion cn) {
        super(cn);
    }

    private static Usuario mapear(java.sql.ResultSet rs) throws java.sql.SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombres(rs.getString("nombres"));
        u.setApellidos(rs.getString("apellidos"));
        u.setCorreo(rs.getString("correo"));
        u.setPassword(rs.getString("password"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }

    /** Devuelve el usuario con ese correo o null si no existe. */
    public Usuario buscarPorCorreo(String correo) {
        return uno("SELECT * FROM usuario WHERE correo = ?", UsuarioDAO::mapear, correo);
    }

    public int registrar(Usuario u) {
        return insertar("INSERT INTO usuario(nombres, apellidos, correo, password, rol, activo) VALUES (?,?,?,?,?,?)",
                u.getNombres(), u.getApellidos(), u.getCorreo(), u.getPassword(), u.getRol(), u.isActivo());
    }

    public int contar() {
        return contar("SELECT COUNT(*) FROM usuario");
    }
}

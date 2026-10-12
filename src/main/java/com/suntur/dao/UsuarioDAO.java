package com.suntur.dao;

import com.suntur.config.Conexion;
import com.suntur.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Usuario buscarUsuario(String correo) {
        Usuario us = new Usuario();
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            if (rs.next()) {
                us.setId(rs.getInt("id"));
                us.setNombres(rs.getString("nombres"));
                us.setApellidos(rs.getString("apellidos"));
                us.setCorreo(rs.getString("correo"));
                us.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("Error en buscarUsuario: " + e.getMessage());
        }
        return us;
    }

    public boolean registrar(String nombre, String apellido, String correo, String password) {
        String sql = "INSERT INTO usuario(nombres, apellidos, correo, password) VALUES(?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, password);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }
}
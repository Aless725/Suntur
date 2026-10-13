package com.suntur.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.suntur.config.Conexion;

/**
 * Clase base de los DAO: concentra el manejo de conexión, PreparedStatement
 * y mapeo de filas para que cada DAO solo declare su SQL.
 */
public abstract class BaseDAO {

    /** Convierte la fila actual de un ResultSet en un objeto. */
    @FunctionalInterface
    protected interface FilaMapper<T> {
        T mapear(ResultSet rs) throws SQLException;
    }

    protected final Conexion cn;

    protected BaseDAO(Conexion cn) {
        this.cn = cn;
    }

    protected <T> List<T> consultar(String sql, FilaMapper<T> mapper, Object... params) {
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            enlazar(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapper.mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new DaoException("Error al consultar: " + e.getMessage(), e);
        }
    }

    protected <T> T uno(String sql, FilaMapper<T> mapper, Object... params) {
        List<T> lista = consultar(sql, mapper, params);
        return lista.isEmpty() ? null : lista.get(0);
    }

    protected int contar(String sql, Object... params) {
        Integer n = uno(sql, rs -> rs.getInt(1), params);
        return n == null ? 0 : n;
    }

    /** Ejecuta INSERT/UPDATE/DELETE y devuelve las filas afectadas. */
    protected int ejecutar(String sql, Object... params) {
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            enlazar(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error al ejecutar: " + e.getMessage(), e);
        }
    }

    /** Ejecuta un INSERT y devuelve el id autogenerado. */
    protected int insertar(String sql, Object... params) {
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            enlazar(ps, params);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Error al insertar: " + e.getMessage(), e);
        }
    }

    private void enlazar(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object p = params[i];
            if (p instanceof LocalDate f) {
                ps.setDate(i + 1, Date.valueOf(f));
            } else {
                ps.setObject(i + 1, p);
            }
        }
    }

    protected static LocalDate fecha(ResultSet rs, String columna) throws SQLException {
        Date d = rs.getDate(columna);
        return d == null ? null : d.toLocalDate();
    }

    protected static Integer entero(ResultSet rs, String columna) throws SQLException {
        int v = rs.getInt(columna);
        return rs.wasNull() ? null : v;
    }

    protected static Double decimal(ResultSet rs, String columna) throws SQLException {
        double v = rs.getDouble(columna);
        return rs.wasNull() ? null : v;
    }
}

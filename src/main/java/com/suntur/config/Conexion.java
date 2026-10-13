package com.suntur.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Fábrica de conexiones JDBC. Los datos de conexión salen de application.properties
 * (o de variables de entorno SUNTUR_DB_URL, SUNTUR_DB_USER y SUNTUR_DB_PASSWORD).
 * Cada llamada abre una conexión nueva; los DAO la cierran con try-with-resources.
 */
@Component
public class Conexion {

    private final String url;
    private final String usuario;
    private final String clave;

    public Conexion(@Value("${suntur.db.url}") String url,
                    @Value("${suntur.db.user}") String usuario,
                    @Value("${suntur.db.password}") String clave) {
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, clave);
    }
}

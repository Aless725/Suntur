package com.suntur.dao;

/** Error técnico de acceso a datos (envuelve SQLException). */
public class DaoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DaoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

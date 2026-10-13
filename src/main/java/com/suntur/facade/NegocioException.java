package com.suntur.facade;

/** Error de regla de negocio o validación; el mensaje se muestra tal cual al usuario. */
public class NegocioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

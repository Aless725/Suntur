package com.suntur.dao;

import java.util.List;

/** Contrato genérico del patrón DAO para entidades con id numérico. */
public interface CrudDAO<T> {
    List<T> listar();
    T buscarPorId(int id);
    /** Inserta si id == 0, actualiza en caso contrario. Devuelve el id. */
    int guardar(T entidad);
}

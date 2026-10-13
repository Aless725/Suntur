package com.suntur.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.suntur.config.AuthInterceptor;
import com.suntur.dao.DaoException;
import com.suntur.facade.SunturFacade;
import com.suntur.model.Usuario;

import jakarta.servlet.http.HttpSession;

/** Datos comunes a todas las vistas (usuario en sesión y año académico) y manejo de errores técnicos. */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final SunturFacade facade;

    public GlobalControllerAdvice(SunturFacade facade) {
        this.facade = facade;
    }

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual(HttpSession sesion) {
        return (Usuario) sesion.getAttribute(AuthInterceptor.SESION_USUARIO);
    }

    @ModelAttribute("anioActual")
    public int anioActual() {
        return facade.anioActual();
    }

    @ExceptionHandler(DaoException.class)
    public String errorDeDatos(DaoException e, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("mensaje", "No se pudo completar la operación con la base de datos. "
                + "Verifica que MySQL esté activo y que los datos de conexión sean correctos.");
        return "error";
    }
}

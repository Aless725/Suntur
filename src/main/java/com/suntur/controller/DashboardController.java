package com.suntur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.suntur.facade.SunturFacade;

@Controller
public class DashboardController {

    private final SunturFacade facade;

    public DashboardController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String acceso, Model model) {
        int anio = facade.anioActual();
        model.addAttribute("resumen", facade.resumen(anio));
        model.addAttribute("promedios", facade.promediosPorSeccion(anio));
        if ("denegado".equals(acceso)) {
            model.addAttribute("error", "No tienes permiso para acceder a esa sección.");
        }
        return "dashboard";
    }
}

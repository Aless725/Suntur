package com.suntur.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suntur.facade.NegocioException;
import com.suntur.facade.SunturFacade;

/** Registro de asistencia diaria por sección. */
@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    private final SunturFacade facade;

    public AsistenciaController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String formulario(@RequestParam(required = false) Integer seccionId,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                             Model model) {
        LocalDate dia = fecha != null ? fecha : LocalDate.now();
        model.addAttribute("secciones", facade.listarSecciones());
        model.addAttribute("seccionId", seccionId);
        model.addAttribute("fecha", dia);
        model.addAttribute("hoy", LocalDate.now());
        if (seccionId != null) {
            model.addAttribute("filas", facade.listarAsistencia(seccionId, dia, facade.anioActual()));
        }
        return "asistencias/registro";
    }

    /** Los campos del formulario se llaman asis_{idMatricula} con valor P, T o F. */
    @PostMapping("/guardar")
    public String guardar(@RequestParam int seccionId,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                          @RequestParam Map<String, String> params, RedirectAttributes ra) {
        Map<Integer, String> estados = new LinkedHashMap<>();
        params.forEach((clave, valor) -> {
            if (clave.startsWith("asis_")) {
                try {
                    estados.put(Integer.parseInt(clave.substring(5)), valor);
                } catch (NumberFormatException ignorar) {
                    // campo ajeno al formulario: se ignora
                }
            }
        });
        try {
            int n = facade.guardarAsistencia(estados, fecha);
            ra.addFlashAttribute("ok", "Asistencia guardada (" + n + " alumno(s)).");
        } catch (NegocioException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        ra.addAttribute("seccionId", seccionId);
        ra.addAttribute("fecha", fecha.toString());
        return "redirect:/asistencias";
    }
}

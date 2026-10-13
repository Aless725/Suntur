package com.suntur.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suntur.dto.MatriculaDTO;
import com.suntur.dto.PagoDTO;
import com.suntur.facade.NegocioException;
import com.suntur.facade.SunturFacade;

import jakarta.servlet.http.HttpServletResponse;

/** Reportes e informes: resumen general, boleta de notas y exportación a CSV. */
@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final SunturFacade facade;

    public ReporteController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String reportes(Model model) {
        int anio = facade.anioActual();
        model.addAttribute("resumen", facade.resumen(anio));
        model.addAttribute("promedios", facade.promediosPorSeccion(anio));
        model.addAttribute("matriculas", facade.listarMatriculas(anio));
        model.addAttribute("pendientes", facade.listarPagos(anio, "PENDIENTE"));
        return "reportes/index";
    }

    @GetMapping("/boleta/{matriculaId}")
    public String boleta(@PathVariable int matriculaId, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("boleta", facade.boleta(matriculaId));
            return "reportes/boleta";
        } catch (NegocioException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/reportes";
        }
    }

    @GetMapping("/matriculas.csv")
    public void matriculasCsv(HttpServletResponse response) throws IOException {
        List<MatriculaDTO> lista = facade.listarMatriculas(facade.anioActual());
        PrintWriter out = abrirCsv(response, "matriculas-" + facade.anioActual() + ".csv");
        out.println("DNI;Estudiante;Seccion;Anio;Fecha;Estado");
        for (MatriculaDTO m : lista) {
            out.println(String.join(";", csv(m.getDni()), csv(m.getEstudiante()), csv(m.getSeccion()),
                    String.valueOf(m.getAnio()), String.valueOf(m.getFecha()), csv(m.getEstado())));
        }
        out.flush();
    }

    @GetMapping("/pagos.csv")
    public void pagosCsv(HttpServletResponse response) throws IOException {
        List<PagoDTO> lista = facade.listarPagos(facade.anioActual(), "");
        PrintWriter out = abrirCsv(response, "pagos-" + facade.anioActual() + ".csv");
        out.println("Estudiante;Seccion;Actividad;Monto;Estado;FechaPago");
        for (PagoDTO p : lista) {
            out.println(String.join(";", csv(p.getEstudiante()), csv(p.getSeccion()), csv(p.getActividad()),
                    String.valueOf(p.getMonto()), csv(p.getEstado()),
                    p.getFechaPago() == null ? "" : p.getFechaPago().toString()));
        }
        out.flush();
    }

    private PrintWriter abrirCsv(HttpServletResponse response, String nombre) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
        PrintWriter out = response.getWriter();
        out.print('﻿'); // BOM para que Excel reconozca UTF-8
        return out;
    }

    /** Escapa un campo CSV y neutraliza fórmulas (=, +, -, @) para evitar inyección en Excel. */
    private static String csv(String valor) {
        if (valor == null) {
            return "";
        }
        String v = valor;
        if (!v.isEmpty() && "=+-@".indexOf(v.charAt(0)) >= 0) {
            v = "'" + v;
        }
        return "\"" + v.replace("\"", "\"\"") + "\"";
    }
}

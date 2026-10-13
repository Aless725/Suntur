package com.suntur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suntur.facade.NegocioException;
import com.suntur.facade.SunturFacade;

/** Módulo de matrícula (solo ADMIN). */
@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final SunturFacade facade;

    public MatriculaController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Integer anio, Model model) {
        int a = anio != null ? anio : facade.anioActual();
        model.addAttribute("anio", a);
        model.addAttribute("matriculas", facade.listarMatriculas(a));
        return "matriculas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("estudiantes", facade.listarEstudiantes(""));
        model.addAttribute("secciones", facade.listarSecciones());
        return "matriculas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam int estudianteId, @RequestParam int seccionId,
                          @RequestParam int anio, Model model, RedirectAttributes ra) {
        try {
            facade.matricular(estudianteId, seccionId, anio);
            ra.addFlashAttribute("ok", "Matrícula registrada.");
            ra.addAttribute("anio", anio);
            return "redirect:/matriculas";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("estudiantes", facade.listarEstudiantes(""));
            model.addAttribute("secciones", facade.listarSecciones());
            return "matriculas/form";
        }
    }

    @PostMapping("/{id}/anular")
    public String anular(@PathVariable int id, @RequestParam(required = false) Integer anio, RedirectAttributes ra) {
        facade.anularMatricula(id);
        ra.addFlashAttribute("ok", "Matrícula anulada.");
        if (anio != null) {
            ra.addAttribute("anio", anio);
        }
        return "redirect:/matriculas";
    }
}

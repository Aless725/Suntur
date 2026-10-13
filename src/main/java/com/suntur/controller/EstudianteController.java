package com.suntur.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
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
import com.suntur.model.Estudiante;

/** Módulo de estudiantes: listado, búsqueda, alta, edición y baja lógica. */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final SunturFacade facade;

    public EstudianteController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String listar(@RequestParam(required = false, defaultValue = "") String q, Model model) {
        model.addAttribute("estudiantes", facade.listarEstudiantes(q));
        model.addAttribute("q", q);
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("e", new Estudiante());
        return "estudiantes/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable int id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("e", facade.obtenerEstudiante(id));
            return "estudiantes/form";
        } catch (NegocioException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/estudiantes";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(defaultValue = "0") int id,
                          @RequestParam String dni, @RequestParam String nombres, @RequestParam String apellidos,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
                          @RequestParam(required = false) String direccion,
                          @RequestParam(required = false) String apoderado,
                          @RequestParam(required = false) String telefonoApoderado,
                          Model model, RedirectAttributes ra) {
        Estudiante e = new Estudiante();
        e.setId(id);
        e.setDni(dni);
        e.setNombres(nombres);
        e.setApellidos(apellidos);
        e.setFechaNacimiento(fechaNacimiento);
        e.setDireccion(direccion);
        e.setApoderado(apoderado);
        e.setTelefonoApoderado(telefonoApoderado);
        try {
            facade.guardarEstudiante(e);
            ra.addFlashAttribute("ok", id == 0 ? "Estudiante registrado correctamente." : "Datos actualizados.");
            return "redirect:/estudiantes";
        } catch (NegocioException ex) {
            model.addAttribute("e", e);
            model.addAttribute("error", ex.getMessage());
            return "estudiantes/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable int id, RedirectAttributes ra) {
        facade.eliminarEstudiante(id);
        ra.addFlashAttribute("ok", "Estudiante dado de baja.");
        return "redirect:/estudiantes";
    }
}

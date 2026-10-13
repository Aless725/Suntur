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

/** Actividades complementarias y control de pagos (solo ADMIN). */
@Controller
@RequestMapping("/pagos")
public class PagoController {

    private final SunturFacade facade;

    public PagoController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String listar(@RequestParam(required = false, defaultValue = "") String estado, Model model) {
        model.addAttribute("pagos", facade.listarPagos(facade.anioActual(), estado));
        model.addAttribute("estado", estado);
        return "pagos/lista";
    }

    @PostMapping("/{id}/pagar")
    public String pagar(@PathVariable int id, @RequestParam(required = false, defaultValue = "") String estado,
                        RedirectAttributes ra) {
        try {
            facade.registrarPago(id);
            ra.addFlashAttribute("ok", "Pago registrado.");
        } catch (NegocioException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        ra.addAttribute("estado", estado);
        return "redirect:/pagos";
    }

    @GetMapping("/actividades")
    public String actividades(Model model) {
        cargarActividades(model);
        return "pagos/actividades";
    }

    @PostMapping("/actividades/guardar")
    public String crearActividad(@RequestParam String nombre, @RequestParam(required = false) String descripcion,
                                 @RequestParam String monto,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                 RedirectAttributes ra) {
        try {
            facade.crearActividad(nombre, descripcion, monto, fecha);
            ra.addFlashAttribute("ok", "Actividad creada.");
        } catch (NegocioException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pagos/actividades";
    }

    @PostMapping("/asignar")
    public String asignar(@RequestParam int actividadId, @RequestParam int seccionId, RedirectAttributes ra) {
        try {
            int n = facade.asignarActividad(actividadId, seccionId, facade.anioActual());
            ra.addFlashAttribute("ok", n + " cobro(s) generado(s) para la sección.");
        } catch (NegocioException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pagos/actividades";
    }

    private void cargarActividades(Model model) {
        model.addAttribute("actividades", facade.listarActividades());
        model.addAttribute("secciones", facade.listarSecciones());
    }
}

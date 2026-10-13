package com.suntur.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suntur.facade.NegocioException;
import com.suntur.facade.SunturFacade;

/** Registro de calificaciones por sección, curso y bimestre. */
@Controller
@RequestMapping("/notas")
public class NotaController {

    private final SunturFacade facade;

    public NotaController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String formulario(@RequestParam(required = false) Integer seccionId,
                             @RequestParam(required = false) Integer cursoId,
                             @RequestParam(required = false, defaultValue = "1") int bimestre,
                             Model model) {
        model.addAttribute("secciones", facade.listarSecciones());
        model.addAttribute("cursos", facade.listarCursos());
        model.addAttribute("seccionId", seccionId);
        model.addAttribute("cursoId", cursoId);
        model.addAttribute("bimestre", bimestre);
        if (seccionId != null && cursoId != null) {
            model.addAttribute("filas", facade.listarNotas(seccionId, cursoId, bimestre, facade.anioActual()));
        }
        return "notas/registro";
    }

    /** Los campos del formulario se llaman nota_{idMatricula}. */
    @PostMapping("/guardar")
    public String guardar(@RequestParam int seccionId, @RequestParam int cursoId, @RequestParam int bimestre,
                          @RequestParam Map<String, String> params, RedirectAttributes ra) {
        Map<Integer, String> notas = new LinkedHashMap<>();
        params.forEach((clave, valor) -> {
            if (clave.startsWith("nota_")) {
                try {
                    notas.put(Integer.parseInt(clave.substring(5)), valor);
                } catch (NumberFormatException ignorar) {
                    // campo ajeno al formulario: se ignora
                }
            }
        });
        try {
            int n = facade.guardarNotas(notas, cursoId, bimestre);
            ra.addFlashAttribute("ok", n + " nota(s) guardada(s).");
        } catch (NegocioException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        ra.addAttribute("seccionId", seccionId);
        ra.addAttribute("cursoId", cursoId);
        ra.addAttribute("bimestre", bimestre);
        return "redirect:/notas";
    }
}

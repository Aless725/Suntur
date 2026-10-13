package com.suntur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suntur.config.AuthInterceptor;
import com.suntur.facade.NegocioException;
import com.suntur.facade.SunturFacade;
import com.suntur.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/** Inicio de sesión, registro y cierre de sesión. */
@Controller
public class AuthController {

    private final SunturFacade facade;

    public AuthController(SunturFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(HttpSession sesion) {
        return sesion.getAttribute(AuthInterceptor.SESION_USUARIO) != null ? "redirect:/dashboard" : "login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String correo, @RequestParam String password,
                                HttpServletRequest request, Model model) {
        try {
            Usuario u = facade.autenticar(correo, password);
            request.getSession(true);
            request.changeSessionId(); // evita la fijación de sesión
            u.setPassword(null);       // no se conserva el hash en la sesión
            request.getSession().setAttribute(AuthInterceptor.SESION_USUARIO, u);
            return "redirect:/dashboard";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("correo", correo);
            return "login";
        }
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String nombres, @RequestParam String apellidos,
                            @RequestParam String correo, @RequestParam String password,
                            @RequestParam String confirmar, Model model, RedirectAttributes ra) {
        try {
            facade.registrarUsuario(nombres, apellidos, correo, password, confirmar);
            ra.addFlashAttribute("ok", "Cuenta creada. Ya puedes iniciar sesión.");
            return "redirect:/login";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("nombres", nombres);
            model.addAttribute("apellidos", apellidos);
            model.addAttribute("correo", correo);
            return "registro";
        }
    }

    @GetMapping("/logout")
    public String salir(HttpSession sesion) {
        sesion.invalidate();
        return "redirect:/login";
    }
}

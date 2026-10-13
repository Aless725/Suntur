package com.suntur.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.suntur.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Protege todas las rutas: exige sesión iniciada y aplica los permisos por rol.
 * ADMIN accede a todo. DOCENTE solo a tablero, notas, asistencias, reportes y la lista de estudiantes.
 */
public class AuthInterceptor implements HandlerInterceptor {

    public static final String SESION_USUARIO = "usuario";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ctx = request.getContextPath();
        String ruta = request.getRequestURI().substring(ctx.length());
        HttpSession sesion = request.getSession(false);
        Usuario usuario = sesion == null ? null : (Usuario) sesion.getAttribute(SESION_USUARIO);

        if (usuario == null) {
            response.sendRedirect(ctx + "/login");
            return false;
        }
        if (!usuario.isAdmin() && esSoloAdmin(ruta, request.getMethod())) {
            response.sendRedirect(ctx + "/dashboard?acceso=denegado");
            return false;
        }
        return true;
    }

    private boolean esSoloAdmin(String ruta, String metodo) {
        if (ruta.startsWith("/matriculas") || ruta.startsWith("/pagos") || ruta.startsWith("/reportes/pagos")) {
            return true;
        }
        // Estudiantes: el docente solo puede consultar el listado.
        return ruta.startsWith("/estudiantes")
                && (!"GET".equals(metodo) || ruta.contains("/nuevo") || ruta.contains("/editar"));
    }
}

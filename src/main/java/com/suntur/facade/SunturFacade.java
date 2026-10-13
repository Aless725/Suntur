package com.suntur.facade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suntur.dao.ActividadDAO;
import com.suntur.dao.AsistenciaDAO;
import com.suntur.dao.CursoDAO;
import com.suntur.dao.EstudianteDAO;
import com.suntur.dao.MatriculaDAO;
import com.suntur.dao.NotaDAO;
import com.suntur.dao.PagoDAO;
import com.suntur.dao.ReporteDAO;
import com.suntur.dao.SeccionDAO;
import com.suntur.dao.UsuarioDAO;
import com.suntur.dto.AsistenciaDTO;
import com.suntur.dto.BoletaDTO;
import com.suntur.dto.BoletaFilaDTO;
import com.suntur.dto.MatriculaDTO;
import com.suntur.dto.NotaDTO;
import com.suntur.dto.PagoDTO;
import com.suntur.dto.PromedioSeccionDTO;
import com.suntur.dto.ResumenDTO;
import com.suntur.model.Actividad;
import com.suntur.model.Curso;
import com.suntur.model.Estudiante;
import com.suntur.model.Matricula;
import com.suntur.model.Seccion;
import com.suntur.model.Usuario;

/**
 * Patrón Facade: único punto de entrada de los controladores a la lógica de negocio.
 * Aquí viven las validaciones y reglas; los controladores no conocen los DAO.
 */
@Service
public class SunturFacade {

    private static final Pattern DNI = Pattern.compile("\\d{8}");
    private static final Pattern CORREO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern TELEFONO = Pattern.compile("\\d{7,15}");

    private final UsuarioDAO usuarioDAO;
    private final EstudianteDAO estudianteDAO;
    private final SeccionDAO seccionDAO;
    private final CursoDAO cursoDAO;
    private final MatriculaDAO matriculaDAO;
    private final NotaDAO notaDAO;
    private final AsistenciaDAO asistenciaDAO;
    private final ActividadDAO actividadDAO;
    private final PagoDAO pagoDAO;
    private final ReporteDAO reporteDAO;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final String anioConfigurado;

    public SunturFacade(UsuarioDAO usuarioDAO, EstudianteDAO estudianteDAO, SeccionDAO seccionDAO,
                        CursoDAO cursoDAO, MatriculaDAO matriculaDAO, NotaDAO notaDAO,
                        AsistenciaDAO asistenciaDAO, ActividadDAO actividadDAO, PagoDAO pagoDAO,
                        ReporteDAO reporteDAO, @Value("${suntur.anio:}") String anioConfigurado) {
        this.usuarioDAO = usuarioDAO;
        this.estudianteDAO = estudianteDAO;
        this.seccionDAO = seccionDAO;
        this.cursoDAO = cursoDAO;
        this.matriculaDAO = matriculaDAO;
        this.notaDAO = notaDAO;
        this.asistenciaDAO = asistenciaDAO;
        this.actividadDAO = actividadDAO;
        this.pagoDAO = pagoDAO;
        this.reporteDAO = reporteDAO;
        this.anioConfigurado = anioConfigurado;
    }

    // ------------------------------------------------------------------ Año
    public int anioActual() {
        if (anioConfigurado != null && anioConfigurado.matches("\\d{4}")) {
            return Integer.parseInt(anioConfigurado);
        }
        return Year.now().getValue();
    }

    // ------------------------------------------------------------------ Usuarios
    public Usuario autenticar(String correo, String password) {
        if (esVacio(correo) || esVacio(password)) {
            throw new NegocioException("Ingresa tu correo y contraseña.");
        }
        Usuario u = usuarioDAO.buscarPorCorreo(correo.trim().toLowerCase());
        if (u == null || !u.isActivo() || !encoder.matches(password, u.getPassword())) {
            throw new NegocioException("Correo o contraseña incorrectos.");
        }
        return u;
    }

    /** Registro público: crea siempre un usuario con rol DOCENTE. */
    public void registrarUsuario(String nombres, String apellidos, String correo, String password, String confirmar) {
        if (esVacio(nombres) || esVacio(apellidos) || esVacio(correo) || esVacio(password)) {
            throw new NegocioException("Completa todos los campos.");
        }
        if (!CORREO.matcher(correo.trim()).matches()) {
            throw new NegocioException("El correo no tiene un formato válido.");
        }
        if (password.length() < 8) {
            throw new NegocioException("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!password.equals(confirmar)) {
            throw new NegocioException("Las contraseñas no coinciden.");
        }
        String mail = correo.trim().toLowerCase();
        if (usuarioDAO.buscarPorCorreo(mail) != null) {
            throw new NegocioException("Ya existe un usuario con ese correo.");
        }
        Usuario u = new Usuario();
        u.setNombres(nombres.trim());
        u.setApellidos(apellidos.trim());
        u.setCorreo(mail);
        u.setPassword(encoder.encode(password));
        u.setRol("DOCENTE");
        u.setActivo(true);
        usuarioDAO.registrar(u);
    }

    // ------------------------------------------------------------------ Estudiantes
    public List<Estudiante> listarEstudiantes(String texto) {
        return estudianteDAO.buscar(texto);
    }

    public Estudiante obtenerEstudiante(int id) {
        Estudiante e = estudianteDAO.buscarPorId(id);
        if (e == null) {
            throw new NegocioException("El estudiante no existe.");
        }
        return e;
    }

    public void guardarEstudiante(Estudiante e) {
        if (e.getDni() == null || !DNI.matcher(e.getDni().trim()).matches()) {
            throw new NegocioException("El DNI debe tener exactamente 8 dígitos.");
        }
        if (esVacio(e.getNombres()) || esVacio(e.getApellidos())) {
            throw new NegocioException("Nombres y apellidos son obligatorios.");
        }
        if (e.getFechaNacimiento() == null || !e.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new NegocioException("Ingresa una fecha de nacimiento válida (anterior a hoy).");
        }
        if (!esVacio(e.getTelefonoApoderado()) && !TELEFONO.matcher(e.getTelefonoApoderado().trim()).matches()) {
            throw new NegocioException("El teléfono del apoderado solo debe tener dígitos (7 a 15).");
        }
        e.setDni(e.getDni().trim());
        e.setNombres(e.getNombres().trim());
        e.setApellidos(e.getApellidos().trim());
        if (estudianteDAO.existeDni(e.getDni(), e.getId())) {
            throw new NegocioException("Ya existe un estudiante con el DNI " + e.getDni() + ".");
        }
        estudianteDAO.guardar(e);
    }

    public void eliminarEstudiante(int id) {
        estudianteDAO.desactivar(id);
    }

    // ------------------------------------------------------------------ Matrículas
    public List<Seccion> listarSecciones() {
        return seccionDAO.listar();
    }

    public List<MatriculaDTO> listarMatriculas(int anio) {
        return matriculaDAO.listarPorAnio(anio);
    }

    public void matricular(int estudianteId, int seccionId, int anio) {
        if (estudianteDAO.buscarPorId(estudianteId) == null) {
            throw new NegocioException("Selecciona un estudiante válido.");
        }
        if (seccionDAO.buscarPorId(seccionId) == null) {
            throw new NegocioException("Selecciona una sección válida.");
        }
        if (matriculaDAO.existeActiva(estudianteId, anio)) {
            throw new NegocioException("El estudiante ya tiene una matrícula activa en " + anio + ".");
        }
        Matricula m = new Matricula();
        m.setEstudianteId(estudianteId);
        m.setSeccionId(seccionId);
        m.setAnio(anio);
        m.setFecha(LocalDate.now());
        matriculaDAO.registrar(m);
    }

    public void anularMatricula(int id) {
        matriculaDAO.anular(id);
    }

    // ------------------------------------------------------------------ Notas
    public List<Curso> listarCursos() {
        return cursoDAO.listar();
    }

    public List<NotaDTO> listarNotas(int seccionId, int cursoId, int bimestre, int anio) {
        return notaDAO.listarPorSeccion(seccionId, anio, cursoId, bimestre);
    }

    /**
     * Guarda las notas enviadas (clave = id de matrícula, valor = texto). Los campos vacíos se ignoran.
     * @return cantidad de notas guardadas
     */
    public int guardarNotas(Map<Integer, String> notasPorMatricula, int cursoId, int bimestre) {
        if (bimestre < 1 || bimestre > 4) {
            throw new NegocioException("El bimestre debe estar entre 1 y 4.");
        }
        if (cursoDAO.buscarPorId(cursoId) == null) {
            throw new NegocioException("Selecciona un curso válido.");
        }
        // Se valida todo antes de guardar para no dejar el registro a medias.
        Map<Integer, Integer> validas = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> en : notasPorMatricula.entrySet()) {
            String txt = en.getValue() == null ? "" : en.getValue().trim();
            if (txt.isEmpty()) {
                continue;
            }
            int valor;
            try {
                valor = Integer.parseInt(txt);
            } catch (NumberFormatException ex) {
                throw new NegocioException("Las notas deben ser números enteros entre 0 y 20.");
            }
            if (valor < 0 || valor > 20) {
                throw new NegocioException("Las notas deben estar entre 0 y 20.");
            }
            validas.put(en.getKey(), valor);
        }
        validas.forEach((matriculaId, valor) -> notaDAO.guardar(matriculaId, cursoId, bimestre, valor));
        return validas.size();
    }

    public BoletaDTO boleta(int matriculaId) {
        MatriculaDTO mat = matriculaDAO.buscarDTO(matriculaId);
        if (mat == null) {
            throw new NegocioException("La matrícula no existe.");
        }
        Map<String, Integer[]> porCurso = new LinkedHashMap<>();
        for (Object[] fila : notaDAO.notasDeMatricula(matriculaId)) {
            Integer[] b = porCurso.computeIfAbsent((String) fila[0], k -> new Integer[4]);
            if (fila[1] != null && fila[2] != null) {
                b[(Integer) fila[1] - 1] = (Integer) fila[2];
            }
        }
        List<BoletaFilaDTO> filas = new ArrayList<>();
        double suma = 0;
        int conNota = 0;
        for (Map.Entry<String, Integer[]> en : porCurso.entrySet()) {
            Integer[] b = en.getValue();
            BoletaFilaDTO f = new BoletaFilaDTO();
            f.setCurso(en.getKey());
            f.setB1(b[0]);
            f.setB2(b[1]);
            f.setB3(b[2]);
            f.setB4(b[3]);
            f.setPromedio(promedio(b));
            if (f.getPromedio() != null) {
                suma += f.getPromedio();
                conNota++;
            }
            filas.add(f);
        }
        BoletaDTO boleta = new BoletaDTO();
        boleta.setMatricula(mat);
        boleta.setFilas(filas);
        boleta.setPromedioGeneral(conNota == 0 ? null : suma / conNota);
        return boleta;
    }

    private static Double promedio(Integer[] notas) {
        double suma = 0;
        int n = 0;
        for (Integer v : notas) {
            if (v != null) {
                suma += v;
                n++;
            }
        }
        return n == 0 ? null : suma / n;
    }

    // ------------------------------------------------------------------ Asistencia
    public List<AsistenciaDTO> listarAsistencia(int seccionId, LocalDate fecha, int anio) {
        return asistenciaDAO.listarPorSeccion(seccionId, anio, fecha);
    }

    /** @return cantidad de asistencias guardadas (se ignoran los alumnos sin estado marcado) */
    public int guardarAsistencia(Map<Integer, String> estadoPorMatricula, LocalDate fecha) {
        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            throw new NegocioException("La fecha de asistencia no puede ser futura.");
        }
        int n = 0;
        for (Map.Entry<Integer, String> en : estadoPorMatricula.entrySet()) {
            String estado = en.getValue() == null ? "" : en.getValue().trim().toUpperCase();
            if (estado.isEmpty()) {
                continue;
            }
            if (!estado.equals("P") && !estado.equals("T") && !estado.equals("F")) {
                throw new NegocioException("Estado de asistencia no válido.");
            }
            asistenciaDAO.guardar(en.getKey(), fecha, estado);
            n++;
        }
        return n;
    }

    // ------------------------------------------------------------------ Actividades y pagos
    public List<Actividad> listarActividades() {
        return actividadDAO.listar();
    }

    public void crearActividad(String nombre, String descripcion, String monto, LocalDate fecha) {
        if (esVacio(nombre)) {
            throw new NegocioException("El nombre de la actividad es obligatorio.");
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(monto == null ? "" : monto.trim());
        } catch (NumberFormatException ex) {
            throw new NegocioException("El monto no es un número válido.");
        }
        if (valor.signum() <= 0) {
            throw new NegocioException("El monto debe ser mayor que cero.");
        }
        Actividad a = new Actividad();
        a.setNombre(nombre.trim());
        a.setDescripcion(esVacio(descripcion) ? null : descripcion.trim());
        a.setMonto(valor.setScale(2, java.math.RoundingMode.HALF_UP));
        a.setFecha(fecha);
        actividadDAO.guardar(a);
    }

    /** Genera el cobro de la actividad para toda la sección. @return cobros creados */
    public int asignarActividad(int actividadId, int seccionId, int anio) {
        if (actividadDAO.buscarPorId(actividadId) == null || seccionDAO.buscarPorId(seccionId) == null) {
            throw new NegocioException("Selecciona una actividad y una sección válidas.");
        }
        return pagoDAO.asignarASeccion(actividadId, seccionId, anio);
    }

    public List<PagoDTO> listarPagos(int anio, String estado) {
        return pagoDAO.listar(anio, estado);
    }

    public void registrarPago(int pagoId) {
        if (!pagoDAO.marcarPagado(pagoId, LocalDate.now())) {
            throw new NegocioException("El pago no existe o ya estaba registrado.");
        }
    }

    // ------------------------------------------------------------------ Reportes
    public ResumenDTO resumen(int anio) {
        return reporteDAO.resumen(anio, LocalDate.now());
    }

    public List<PromedioSeccionDTO> promediosPorSeccion(int anio) {
        return reporteDAO.promediosPorSeccion(anio);
    }

    private static boolean esVacio(String s) {
        return s == null || s.isBlank();
    }
}

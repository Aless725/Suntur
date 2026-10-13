package com.suntur.config;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.suntur.dao.ActividadDAO;
import com.suntur.dao.AsistenciaDAO;
import com.suntur.dao.CursoDAO;
import com.suntur.dao.EstudianteDAO;
import com.suntur.dao.MatriculaDAO;
import com.suntur.dao.NotaDAO;
import com.suntur.dao.PagoDAO;
import com.suntur.dao.SeccionDAO;
import com.suntur.dao.UsuarioDAO;
import com.suntur.facade.SunturFacade;
import com.suntur.model.Actividad;
import com.suntur.model.Curso;
import com.suntur.model.Estudiante;
import com.suntur.model.Matricula;
import com.suntur.model.Seccion;
import com.suntur.model.Usuario;

/**
 * Al iniciar la aplicación: crea las tablas (db/schema.sql) y carga los datos base
 * (secciones, cursos, actividades y usuario administrador). Opcionalmente carga datos de ejemplo.
 */
@Component
public class DatabaseInitializer implements ApplicationRunner {

    public static final String ADMIN_CORREO = "admin@suntur.edu.pe";
    public static final String ADMIN_CLAVE = "Admin123";
    public static final String DOCENTE_CORREO = "docente@suntur.edu.pe";
    public static final String DOCENTE_CLAVE = "Docente123";

    private final Conexion cn;
    private final UsuarioDAO usuarioDAO;
    private final SeccionDAO seccionDAO;
    private final CursoDAO cursoDAO;
    private final ActividadDAO actividadDAO;
    private final EstudianteDAO estudianteDAO;
    private final MatriculaDAO matriculaDAO;
    private final NotaDAO notaDAO;
    private final AsistenciaDAO asistenciaDAO;
    private final PagoDAO pagoDAO;
    private final SunturFacade facade;
    private final boolean sembrarDemo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DatabaseInitializer(Conexion cn, UsuarioDAO usuarioDAO, SeccionDAO seccionDAO, CursoDAO cursoDAO,
                               ActividadDAO actividadDAO, EstudianteDAO estudianteDAO, MatriculaDAO matriculaDAO,
                               NotaDAO notaDAO, AsistenciaDAO asistenciaDAO, PagoDAO pagoDAO, SunturFacade facade,
                               @Value("${suntur.seed-demo:false}") boolean sembrarDemo) {
        this.cn = cn;
        this.usuarioDAO = usuarioDAO;
        this.seccionDAO = seccionDAO;
        this.cursoDAO = cursoDAO;
        this.actividadDAO = actividadDAO;
        this.estudianteDAO = estudianteDAO;
        this.matriculaDAO = matriculaDAO;
        this.notaDAO = notaDAO;
        this.asistenciaDAO = asistenciaDAO;
        this.pagoDAO = pagoDAO;
        this.facade = facade;
        this.sembrarDemo = sembrarDemo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        crearTablas();
        sembrarBase();
        if (sembrarDemo && estudianteDAO.listar().isEmpty()) {
            sembrarDemo();
        }
    }

    /** Ejecuta cada sentencia de db/schema.sql (separadas por ";" al final de línea). */
    private void crearTablas() throws Exception {
        String script;
        try (InputStream in = new ClassPathResource("db/schema.sql").getInputStream()) {
            script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        StringBuilder sinComentarios = new StringBuilder();
        for (String linea : script.split("\\R")) {
            if (!linea.trim().startsWith("--")) {
                sinComentarios.append(linea).append('\n');
            }
        }
        try (Connection con = cn.getConnection(); Statement st = con.createStatement()) {
            for (String sentencia : sinComentarios.toString().split(";\\s*\\n")) {
                if (!sentencia.isBlank()) {
                    st.execute(sentencia.trim());
                }
            }
        }
    }

    private void sembrarBase() {
        if (usuarioDAO.contar() == 0) {
            usuarioDAO.registrar(usuario("Administrador", "Suntur", ADMIN_CORREO, ADMIN_CLAVE, "ADMIN"));
        }
        if (seccionDAO.contar() == 0) {
            for (int grado = 1; grado <= 6; grado++) {
                crearSeccion("Primaria", grado, "A");
                crearSeccion("Primaria", grado, "B");
            }
            for (int grado = 1; grado <= 4; grado++) {
                crearSeccion("Secundaria", grado, "A");
                crearSeccion("Secundaria", grado, "B");
            }
            crearSeccion("Secundaria", 5, "A"); // 21 secciones en total
        }
        if (cursoDAO.contar() == 0) {
            for (String nombre : List.of("Matemática", "Comunicación", "Ciencia y Tecnología", "Personal Social",
                    "Inglés", "Arte y Cultura", "Educación Física", "Educación Religiosa")) {
                Curso c = new Curso();
                c.setNombre(nombre);
                cursoDAO.guardar(c);
            }
        }
        if (actividadDAO.contar() == 0) {
            crearActividad("Paseo escolar", "Visita cultural del año", "35.00");
            crearActividad("Aniversario institucional", "Cuota de actividades por aniversario", "20.00");
            crearActividad("Taller de robótica", "Taller complementario semestral", "60.00");
        }
    }

    private void sembrarDemo() {
        usuarioDAO.registrar(usuario("Lucía", "Paredes Soto", DOCENTE_CORREO, DOCENTE_CLAVE, "DOCENTE"));

        String[][] alumnos = {
            {"71234501", "Camila", "Quispe Huamán"}, {"71234502", "Diego", "Ramos Flores"},
            {"71234503", "Valeria", "Torres Mendoza"}, {"71234504", "Mateo", "Castillo Rojas"},
            {"71234505", "Sofía", "Vargas León"}, {"71234506", "Joaquín", "Salazar Paredes"},
            {"71234507", "Luciana", "Chávez Ortiz"}, {"71234508", "Andrés", "Medina Cruz"}};
        List<Seccion> secciones = seccionDAO.listar();
        int anio = facade.anioActual();
        // Los primeros 5 alumnos van a la primera sección y el resto a la segunda.
        for (int i = 0; i < alumnos.length; i++) {
            Estudiante e = new Estudiante();
            e.setDni(alumnos[i][0]);
            e.setNombres(alumnos[i][1]);
            e.setApellidos(alumnos[i][2]);
            e.setFechaNacimiento(LocalDate.of(2012 + (i % 3), 3 + i, 10 + i));
            e.setDireccion("La Molina, Lima");
            e.setApoderado("Apoderado de " + alumnos[i][1]);
            e.setTelefonoApoderado("98765430" + i);
            int estId = estudianteDAO.guardar(e);

            Matricula m = new Matricula();
            m.setEstudianteId(estId);
            m.setSeccionId(secciones.get(i < 5 ? 0 : 1).getId());
            m.setAnio(anio);
            m.setFecha(LocalDate.now());
            int matId = matriculaDAO.registrar(m);

            List<Curso> cursos = cursoDAO.listar();
            for (int c = 0; c < cursos.size(); c++) {
                notaDAO.guardar(matId, cursos.get(c).getId(), 1, 11 + (i * 3 + c * 2) % 10);
            }
            asistenciaDAO.guardar(matId, LocalDate.now(), i % 4 == 3 ? "F" : (i % 4 == 2 ? "T" : "P"));
        }
        Actividad paseo = actividadDAO.listar().stream().filter(a -> a.getNombre().startsWith("Paseo")).findFirst().orElseThrow();
        pagoDAO.asignarASeccion(paseo.getId(), secciones.get(0).getId(), anio);
        pagoDAO.listar(anio, "PENDIENTE").stream().limit(2).forEach(p -> pagoDAO.marcarPagado(p.getId(), LocalDate.now()));
    }

    private Usuario usuario(String nombres, String apellidos, String correo, String clave, String rol) {
        Usuario u = new Usuario();
        u.setNombres(nombres);
        u.setApellidos(apellidos);
        u.setCorreo(correo);
        u.setPassword(encoder.encode(clave));
        u.setRol(rol);
        u.setActivo(true);
        return u;
    }

    private void crearSeccion(String nivel, int grado, String nombre) {
        Seccion s = new Seccion();
        s.setNivel(nivel);
        s.setGrado(grado);
        s.setNombre(nombre);
        seccionDAO.guardar(s);
    }

    private void crearActividad(String nombre, String descripcion, String monto) {
        Actividad a = new Actividad();
        a.setNombre(nombre);
        a.setDescripcion(descripcion);
        a.setMonto(new BigDecimal(monto));
        actividadDAO.guardar(a);
    }
}

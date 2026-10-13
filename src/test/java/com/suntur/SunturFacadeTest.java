package com.suntur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.suntur.config.DatabaseInitializer;
import com.suntur.dto.BoletaDTO;
import com.suntur.dto.MatriculaDTO;
import com.suntur.facade.NegocioException;
import com.suntur.facade.SunturFacade;
import com.suntur.model.Estudiante;
import com.suntur.model.Seccion;

/** Prueba de integración de la fachada contra H2 en memoria (perfil "h2"). */
@SpringBootTest
@ActiveProfiles("h2")
class SunturFacadeTest {

    @Autowired
    private SunturFacade facade;

    @Test
    void arrancaConDatosBase() {
        assertEquals(21, facade.listarSecciones().size());
        assertEquals(8, facade.listarCursos().size());
        assertNotNull(facade.autenticar(DatabaseInitializer.ADMIN_CORREO, DatabaseInitializer.ADMIN_CLAVE));
    }

    @Test
    void rechazaCredencialesIncorrectas() {
        assertThrows(NegocioException.class, () -> facade.autenticar(DatabaseInitializer.ADMIN_CORREO, "incorrecta"));
    }

    @Test
    void flujoCompletoMatriculaNotasYPagos() {
        int anio = facade.anioActual();
        Estudiante e = new Estudiante();
        e.setDni("99999999");
        e.setNombres("Prueba");
        e.setApellidos("Automática");
        e.setFechaNacimiento(LocalDate.of(2013, 5, 20));
        facade.guardarEstudiante(e);

        Estudiante guardado = facade.listarEstudiantes("99999999").get(0);
        Seccion seccion = facade.listarSecciones().get(2);

        // DNI duplicado
        Estudiante repetido = new Estudiante();
        repetido.setDni("99999999");
        repetido.setNombres("Otro");
        repetido.setApellidos("Alumno");
        repetido.setFechaNacimiento(LocalDate.of(2012, 1, 1));
        assertThrows(NegocioException.class, () -> facade.guardarEstudiante(repetido));

        // Matrícula única por año
        facade.matricular(guardado.getId(), seccion.getId(), anio);
        assertThrows(NegocioException.class, () -> facade.matricular(guardado.getId(), seccion.getId(), anio));

        MatriculaDTO mat = facade.listarMatriculas(anio).stream()
                .filter(m -> "99999999".equals(m.getDni())).findFirst().orElseThrow();
        int cursoId = facade.listarCursos().get(0).getId();

        // Notas: valida rango y calcula promedio en la boleta
        assertThrows(NegocioException.class, () -> facade.guardarNotas(java.util.Map.of(mat.getId(), "21"), cursoId, 1));
        facade.guardarNotas(java.util.Map.of(mat.getId(), "14"), cursoId, 1);
        facade.guardarNotas(java.util.Map.of(mat.getId(), "16"), cursoId, 2);
        BoletaDTO boleta = facade.boleta(mat.getId());
        assertEquals(15.0, boleta.getFilas().get(0).getPromedio(), 0.001);

        // Asistencia
        assertEquals(1, facade.guardarAsistencia(java.util.Map.of(mat.getId(), "T"), LocalDate.now()));

        // Pagos: se asigna una actividad a la sección y se cobra
        int actividadId = facade.listarActividades().get(0).getId();
        assertTrue(facade.asignarActividad(actividadId, seccion.getId(), anio) >= 1);
        int pagoId = facade.listarPagos(anio, "PENDIENTE").stream()
                .filter(p -> p.getEstudiante().contains("Automática")).findFirst().orElseThrow().getId();
        facade.registrarPago(pagoId);
        assertThrows(NegocioException.class, () -> facade.registrarPago(pagoId));
    }
}

package pe.jvresguardo.sigecap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.jvresguardo.sigecap.model.Trabajador;
import pe.jvresguardo.sigecap.repository.TrabajadorRepositoryFalso;

class TrabajadorServiceTest {

    private TrabajadorRepositoryFalso trabajadorRepository;
    private TrabajadorService trabajadorService;

    @BeforeEach
    void setUp() {
        trabajadorRepository = new TrabajadorRepositoryFalso();
        trabajadorService = new TrabajadorService(trabajadorRepository);
    }

    @Test
    void registrarUnTrabajadorValidoLoGuardaActivo() throws SQLException {
        Trabajador trabajador = new Trabajador("12345678", "Juan", "Perez", "Vigilante");

        Trabajador guardado = trabajadorService.registrar(trabajador);

        assertNotNull(guardado.getId());
        assertTrue(guardado.isEstado());
    }

    @Test
    void rechazaCamposObligatoriosFaltantes() {
        Trabajador trabajador = new Trabajador("", "Juan", "Perez", "Vigilante");

        assertThrows(IllegalArgumentException.class, () -> trabajadorService.registrar(trabajador));
    }

    @Test
    void noPermiteDniDuplicadoAlRegistrar() throws SQLException {
        trabajadorService.registrar(new Trabajador("12345678", "Juan", "Perez", "Vigilante"));

        Trabajador duplicado = new Trabajador("12345678", "Otro", "Nombre", "Supervisor");

        assertThrows(IllegalArgumentException.class, () -> trabajadorService.registrar(duplicado));
    }

    @Test
    void actualizaCorrectamenteLosDatos() throws SQLException {
        Trabajador guardado = trabajadorService.registrar(new Trabajador("12345678", "Juan", "Perez", "Vigilante"));

        guardado.setCargo("Supervisor");
        trabajadorService.actualizar(guardado);

        Trabajador actualizado = trabajadorRepository.buscarPorId(guardado.getId());
        assertEquals("Supervisor", actualizado.getCargo());
    }

    @Test
    void noPermiteDniDuplicadoAlActualizar() throws SQLException {
        trabajadorService.registrar(new Trabajador("11111111", "Juan", "Perez", "Vigilante"));
        Trabajador segundo = trabajadorService.registrar(new Trabajador("22222222", "Maria", "Lopez", "Supervisor"));

        segundo.setDni("11111111");

        assertThrows(IllegalArgumentException.class, () -> trabajadorService.actualizar(segundo));
    }

    @Test
    void activaYDesactivaUnTrabajador() throws SQLException {
        Trabajador guardado = trabajadorService.registrar(new Trabajador("12345678", "Juan", "Perez", "Vigilante"));

        trabajadorService.desactivar(guardado.getId());
        assertFalse(trabajadorRepository.buscarPorId(guardado.getId()).isEstado());

        trabajadorService.activar(guardado.getId());
        assertTrue(trabajadorRepository.buscarPorId(guardado.getId()).isEstado());
    }
}

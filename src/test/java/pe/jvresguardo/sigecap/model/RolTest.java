package pe.jvresguardo.sigecap.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RolTest {

    @Test
    void unRolNuevoQuedaActivoPorDefecto() {
        Rol rol = new Rol("ADMIN", "Administrador del sistema");

        assertTrue(rol.isEstado());
    }

    @Test
    void elConstructorCompletoAsignaTodosLosDatos() {
        Rol rol = new Rol(1L, "SUPERVISOR", "Supervisor de campo", false);

        assertEquals(1L, rol.getId());
        assertEquals("SUPERVISOR", rol.getNombre());
        assertEquals("Supervisor de campo", rol.getDescripcion());
        assertEquals(false, rol.isEstado());
    }
}

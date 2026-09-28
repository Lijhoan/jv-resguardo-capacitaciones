package pe.jvresguardo.sigecap.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void unUsuarioNuevoQuedaActivoPorDefecto() {
        Rol rol = new Rol("ADMIN", "Administrador del sistema");
        Usuario usuario = new Usuario("jperez", "Juan Perez", "jperez@jvresguardo.pe", "hash-fake", rol);

        assertTrue(usuario.isEstado());
    }

    @Test
    void elUsuarioMantieneLaReferenciaASuRol() {
        Rol rol = new Rol("SUPERVISOR", "Supervisor de campo");
        Usuario usuario = new Usuario("mlopez", "Maria Lopez", "mlopez@jvresguardo.pe", "hash-fake", rol);

        assertEquals(rol, usuario.getRol());
        assertEquals("SUPERVISOR", usuario.getRol().getNombre());
    }
}

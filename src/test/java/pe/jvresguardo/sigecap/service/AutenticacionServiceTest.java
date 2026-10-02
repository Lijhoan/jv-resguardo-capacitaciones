package pe.jvresguardo.sigecap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.jvresguardo.sigecap.model.Rol;
import pe.jvresguardo.sigecap.model.Usuario;
import pe.jvresguardo.sigecap.repository.UsuarioRepositoryFalso;
import pe.jvresguardo.sigecap.security.PasswordHasher;

class AutenticacionServiceTest {

    private UsuarioRepositoryFalso usuarioRepository;
    private AutenticacionService autenticacionService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioRepository = new UsuarioRepositoryFalso();
        autenticacionService = new AutenticacionService(usuarioRepository);

        Rol rol = new Rol(1L, "SUPERVISOR", "Supervisor de campo", true);
        usuario = new Usuario("jperez", "Juan Perez", "jperez@jvresguardo.pe",
                PasswordHasher.hash("clave123"), rol);
        usuarioRepository.guardar(usuario);
    }

    @Test
    void autenticaConPasswordCorrecto() throws SQLException {
        AutenticacionService.Resultado resultado = autenticacionService.autenticar("jperez", "clave123");

        assertTrue(resultado.exitoso());
        assertEquals("jperez", resultado.usuario().getUsername());
    }

    @Test
    void rechazaPasswordIncorrecto() throws SQLException {
        AutenticacionService.Resultado resultado = autenticacionService.autenticar("jperez", "clave-equivocada");

        assertFalse(resultado.exitoso());
    }

    @Test
    void rechazaUsuarioInactivo() throws SQLException {
        usuarioRepository.cambiarEstado(usuario.getId(), false);

        AutenticacionService.Resultado resultado = autenticacionService.autenticar("jperez", "clave123");

        assertFalse(resultado.exitoso());
    }
}

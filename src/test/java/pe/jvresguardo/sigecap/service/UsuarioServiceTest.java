package pe.jvresguardo.sigecap.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.jvresguardo.sigecap.model.Rol;
import pe.jvresguardo.sigecap.model.Usuario;
import pe.jvresguardo.sigecap.repository.RolRepositoryFalso;
import pe.jvresguardo.sigecap.repository.UsuarioRepositoryFalso;

class UsuarioServiceTest {

    private UsuarioRepositoryFalso usuarioRepository;
    private UsuarioService usuarioService;
    private Rol rolSupervisor;

    @BeforeEach
    void setUp() {
        usuarioRepository = new UsuarioRepositoryFalso();
        RolRepositoryFalso rolRepository = new RolRepositoryFalso();
        usuarioService = new UsuarioService(usuarioRepository, rolRepository);

        rolSupervisor = new Rol(1L, "SUPERVISOR", "Supervisor de campo", true);
        rolRepository.agregar(rolSupervisor);
    }

    @Test
    void registrarUnUsuarioValidoGuardaElHashNoElTextoPlano() throws SQLException {
        Usuario usuario = new Usuario("jperez", "Juan Perez", "jperez@jvresguardo.pe", "clave123", rolSupervisor);

        Usuario guardado = usuarioService.registrar(usuario);

        assertNotNull(guardado.getId());
        assertNotEquals("clave123", guardado.getPassword());
    }

    @Test
    void noPermiteUsernameDuplicado() throws SQLException {
        usuarioService.registrar(new Usuario("jperez", "Juan Perez", "jperez@jvresguardo.pe", "clave123", rolSupervisor));

        Usuario duplicado = new Usuario("jperez", "Otro Nombre", "otro@jvresguardo.pe", "clave456", rolSupervisor);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(duplicado));
    }

    @Test
    void noPermiteEmailDuplicado() throws SQLException {
        usuarioService.registrar(new Usuario("jperez", "Juan Perez", "jperez@jvresguardo.pe", "clave123", rolSupervisor));

        Usuario duplicado = new Usuario("mlopez", "Maria Lopez", "jperez@jvresguardo.pe", "clave456", rolSupervisor);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(duplicado));
    }

    @Test
    void rechazaRolQueNoExiste() {
        Rol rolInexistente = new Rol(99L, "FANTASMA", "No existe", true);
        Usuario usuario = new Usuario("jperez", "Juan Perez", "jperez@jvresguardo.pe", "clave123", rolInexistente);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(usuario));
    }

    @Test
    void rechazaCamposObligatoriosFaltantes() {
        Usuario usuario = new Usuario("", "Juan Perez", "jperez@jvresguardo.pe", "clave123", rolSupervisor);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(usuario));
    }
}

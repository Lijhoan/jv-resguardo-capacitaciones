package pe.jvresguardo.sigecap.service;

import java.sql.SQLException;

import pe.jvresguardo.sigecap.model.Usuario;
import pe.jvresguardo.sigecap.repository.UsuarioRepository;
import pe.jvresguardo.sigecap.security.PasswordHasher;

/**
 * HU-JVR-013
 * Validacion de credenciales y control de acceso por rol.
 */
public class AutenticacionService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacionService() {
        this(new UsuarioRepository());
    }

    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Resultado autenticar(String username, String password) throws SQLException {
        Usuario usuario = usuarioRepository.buscarPorUsername(username);
        if (usuario == null) {
            return Resultado.fallo("Usuario no encontrado");
        }
        if (!usuario.isEstado()) {
            return Resultado.fallo("El usuario esta inactivo");
        }
        if (!PasswordHasher.verificar(password, usuario.getPassword())) {
            return Resultado.fallo("Contraseña incorrecta");
        }
        return Resultado.exito(usuario);
    }

    /**
     * HU-JVR-013
     * Resultado simple y entendible de un intento de autenticacion.
     */
    public record Resultado(boolean exitoso, Usuario usuario, String mensaje) {

        public static Resultado exito(Usuario usuario) {
            return new Resultado(true, usuario, "Autenticacion exitosa");
        }

        public static Resultado fallo(String mensaje) {
            return new Resultado(false, null, mensaje);
        }
    }
}

package pe.jvresguardo.sigecap.service;

import java.sql.SQLException;

import pe.jvresguardo.sigecap.model.Rol;
import pe.jvresguardo.sigecap.model.Usuario;
import pe.jvresguardo.sigecap.repository.RolRepository;
import pe.jvresguardo.sigecap.repository.UsuarioRepository;
import pe.jvresguardo.sigecap.security.PasswordHasher;

/**
 * HU-JVR-012
 * Reglas de negocio para el registro y mantenimiento de usuarios.
 */
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService() {
        this(new UsuarioRepository(), new RolRepository());
    }

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public Usuario registrar(Usuario usuario) throws SQLException {
        validarCamposObligatorios(usuario);
        validarRolExiste(usuario.getRol());

        if (usuarioRepository.buscarPorUsername(usuario.getUsername()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el username '" + usuario.getUsername() + "'");
        }
        if (usuarioRepository.buscarPorEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el email '" + usuario.getEmail() + "'");
        }

        usuario.setPassword(PasswordHasher.hash(usuario.getPassword()));
        return usuarioRepository.guardar(usuario);
    }

    public void actualizarDatos(Long id, String nombreCompleto, String email) throws SQLException {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con id " + id);
        }
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        Usuario existente = usuarioRepository.buscarPorEmail(email);
        if (existente != null && !existente.getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe un usuario con el email '" + email + "'");
        }

        usuario.setNombreCompleto(nombreCompleto);
        usuario.setEmail(email);
        usuarioRepository.actualizar(usuario);
    }

    public void activar(Long id) throws SQLException {
        usuarioRepository.cambiarEstado(id, true);
    }

    public void desactivar(Long id) throws SQLException {
        usuarioRepository.cambiarEstado(id, false);
    }

    private void validarCamposObligatorios(Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
    }

    private void validarRolExiste(Rol rol) throws SQLException {
        if (rol == null || rol.getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un rol asignado");
        }
        if (rolRepository.buscarPorId(rol.getId()) == null) {
            throw new IllegalArgumentException("El rol con id " + rol.getId() + " no existe");
        }
    }
}

package pe.jvresguardo.sigecap.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import pe.jvresguardo.sigecap.model.Usuario;

/**
 * Doble de prueba en memoria. Evita abrir una conexion JDBC real en los tests
 * de service; no reemplaza una prueba de integracion contra la BD.
 */
public class UsuarioRepositoryFalso extends UsuarioRepository {

    private final Map<Long, Usuario> porId = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setId(secuencia.getAndIncrement());
        porId.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return porId.get(id);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return porId.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return porId.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Usuario usuario) {
        porId.put(usuario.getId(), usuario);
    }

    @Override
    public void cambiarEstado(Long id, boolean estado) {
        Usuario usuario = porId.get(id);
        if (usuario != null) {
            usuario.setEstado(estado);
        }
    }
}

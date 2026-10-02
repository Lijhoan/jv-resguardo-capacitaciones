package pe.jvresguardo.sigecap.repository;

import java.util.HashMap;
import java.util.Map;

import pe.jvresguardo.sigecap.model.Rol;

/**
 * Doble de prueba en memoria. Evita abrir una conexion JDBC real en los tests
 * de service; no reemplaza una prueba de integracion contra la BD.
 */
public class RolRepositoryFalso extends RolRepository {

    private final Map<Long, Rol> roles = new HashMap<>();

    public void agregar(Rol rol) {
        roles.put(rol.getId(), rol);
    }

    @Override
    public Rol buscarPorId(Long id) {
        return roles.get(id);
    }
}

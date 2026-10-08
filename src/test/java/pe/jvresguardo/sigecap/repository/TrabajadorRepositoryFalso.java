package pe.jvresguardo.sigecap.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import pe.jvresguardo.sigecap.model.Trabajador;

/**
 * Doble de prueba en memoria. Evita abrir una conexion JDBC real en los tests
 * de service; no reemplaza una prueba de integracion contra la BD.
 */
public class TrabajadorRepositoryFalso extends TrabajadorRepository {

    private final Map<Long, Trabajador> porId = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    @Override
    public Trabajador guardar(Trabajador trabajador) {
        trabajador.setId(secuencia.getAndIncrement());
        porId.put(trabajador.getId(), trabajador);
        return trabajador;
    }

    @Override
    public Trabajador buscarPorId(Long id) {
        return porId.get(id);
    }

    @Override
    public Trabajador buscarPorDni(String dni) {
        return porId.values().stream()
                .filter(t -> t.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Trabajador> listar() {
        return new ArrayList<>(porId.values());
    }

    @Override
    public List<Trabajador> buscar(String texto) {
        String patron = texto.toLowerCase();
        return porId.values().stream()
                .filter(t -> t.getDni().toLowerCase().contains(patron)
                        || t.getNombres().toLowerCase().contains(patron)
                        || t.getApellidos().toLowerCase().contains(patron)
                        || t.getCargo().toLowerCase().contains(patron))
                .toList();
    }

    @Override
    public void actualizar(Trabajador trabajador) {
        porId.put(trabajador.getId(), trabajador);
    }

    @Override
    public void cambiarEstado(Long id, boolean estado) {
        Trabajador trabajador = porId.get(id);
        if (trabajador != null) {
            trabajador.setEstado(estado);
        }
    }
}

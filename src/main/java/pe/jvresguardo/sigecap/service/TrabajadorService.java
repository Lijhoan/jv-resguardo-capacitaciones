package pe.jvresguardo.sigecap.service;

import java.sql.SQLException;
import java.util.List;

import pe.jvresguardo.sigecap.model.Trabajador;
import pe.jvresguardo.sigecap.repository.TrabajadorRepository;

/**
 * HU-JVR-001
 * Reglas de negocio para el registro y mantenimiento del personal.
 */
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorService() {
        this(new TrabajadorRepository());
    }

    public TrabajadorService(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    public Trabajador registrar(Trabajador trabajador) throws SQLException {
        validarCamposObligatorios(trabajador);
        if (trabajadorRepository.buscarPorDni(trabajador.getDni()) != null) {
            throw new IllegalArgumentException("Ya existe un trabajador con el DNI '" + trabajador.getDni() + "'");
        }
        trabajador.setEstado(true);
        return trabajadorRepository.guardar(trabajador);
    }

    public void actualizar(Trabajador trabajador) throws SQLException {
        validarCamposObligatorios(trabajador);
        if (trabajador.getId() == null) {
            throw new IllegalArgumentException("El trabajador debe tener un id para actualizarse");
        }
        if (trabajadorRepository.buscarPorId(trabajador.getId()) == null) {
            throw new IllegalArgumentException("No existe un trabajador con id " + trabajador.getId());
        }

        Trabajador conMismoDni = trabajadorRepository.buscarPorDni(trabajador.getDni());
        if (conMismoDni != null && !conMismoDni.getId().equals(trabajador.getId())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el DNI '" + trabajador.getDni() + "'");
        }

        trabajadorRepository.actualizar(trabajador);
    }

    public List<Trabajador> listar() throws SQLException {
        return trabajadorRepository.listar();
    }

    public List<Trabajador> buscar(String texto) throws SQLException {
        if (texto == null || texto.isBlank()) {
            return trabajadorRepository.listar();
        }
        return trabajadorRepository.buscar(texto.trim());
    }

    public void activar(Long id) throws SQLException {
        trabajadorRepository.cambiarEstado(id, true);
    }

    public void desactivar(Long id) throws SQLException {
        trabajadorRepository.cambiarEstado(id, false);
    }

    private void validarCamposObligatorios(Trabajador trabajador) {
        if (trabajador.getDni() == null || trabajador.getDni().isBlank()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if (trabajador.getNombres() == null || trabajador.getNombres().isBlank()) {
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }
        if (trabajador.getApellidos() == null || trabajador.getApellidos().isBlank()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
        if (trabajador.getCargo() == null || trabajador.getCargo().isBlank()) {
            throw new IllegalArgumentException("El cargo es obligatorio");
        }
    }
}

package pe.jvresguardo.sigecap.controller;

import java.awt.Component;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import pe.jvresguardo.sigecap.model.Trabajador;
import pe.jvresguardo.sigecap.service.TrabajadorService;

/**
 * HU-JVR-001
 * Coordina PersonalPanel con TrabajadorService.
 */
public class PersonalController {

    private final TrabajadorService trabajadorService;

    public PersonalController() {
        this(new TrabajadorService());
    }

    public PersonalController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    public List<Trabajador> listar(Component vista) {
        try {
            return trabajadorService.listar();
        } catch (SQLException e) {
            mostrarErrorConexion(vista);
            return List.of();
        }
    }

    public List<Trabajador> buscar(String texto, Component vista) {
        try {
            return trabajadorService.buscar(texto);
        } catch (SQLException e) {
            mostrarErrorConexion(vista);
            return List.of();
        }
    }

    public boolean registrar(Trabajador trabajador, Component vista) {
        try {
            trabajadorService.registrar(trabajador);
            return true;
        } catch (IllegalArgumentException e) {
            mostrarError(vista, e.getMessage());
            return false;
        } catch (SQLException e) {
            mostrarErrorConexion(vista);
            return false;
        }
    }

    public boolean actualizar(Trabajador trabajador, Component vista) {
        try {
            trabajadorService.actualizar(trabajador);
            return true;
        } catch (IllegalArgumentException e) {
            mostrarError(vista, e.getMessage());
            return false;
        } catch (SQLException e) {
            mostrarErrorConexion(vista);
            return false;
        }
    }

    public boolean cambiarEstado(Long id, boolean estado, Component vista) {
        try {
            if (estado) {
                trabajadorService.activar(id);
            } else {
                trabajadorService.desactivar(id);
            }
            return true;
        } catch (SQLException e) {
            mostrarErrorConexion(vista);
            return false;
        }
    }

    private void mostrarError(Component vista, String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Personal", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarErrorConexion(Component vista) {
        JOptionPane.showMessageDialog(vista, "No se pudo conectar con la base de datos.", "Error de conexion",
                JOptionPane.ERROR_MESSAGE);
    }
}

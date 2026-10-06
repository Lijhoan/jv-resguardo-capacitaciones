package pe.jvresguardo.sigecap.controller;

import java.awt.Component;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import pe.jvresguardo.sigecap.service.AutenticacionService;

/**
 * HU-JVR-013
 * Coordina el login entre LoginView y AutenticacionService.
 */
public class LoginController {

    private final AutenticacionService autenticacionService;

    public LoginController() {
        this(new AutenticacionService());
    }

    public LoginController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    public void iniciarSesion(String username, char[] password, Component vista) {
        try {
            AutenticacionService.Resultado resultado = autenticacionService.autenticar(username, new String(password));

            if (resultado.exitoso()) {
                JOptionPane.showMessageDialog(vista,
                        "Bienvenido, " + resultado.usuario().getNombreCompleto() + "\n"
                                + "Rol: " + resultado.usuario().getRol().getNombre(),
                        "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, resultado.mensaje(), "Login fallido", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "No se pudo conectar con la base de datos.",
                    "Error de conexion", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}

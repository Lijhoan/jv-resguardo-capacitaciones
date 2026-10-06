package pe.jvresguardo.sigecap.controller;

import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import pe.jvresguardo.sigecap.model.Usuario;
import pe.jvresguardo.sigecap.service.AutenticacionService;
import pe.jvresguardo.sigecap.view.MainView;

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

    public void iniciarSesion(String username, char[] password, JFrame vista) {
        try {
            AutenticacionService.Resultado resultado = autenticacionService.autenticar(username, new String(password));

            if (resultado.exitoso()) {
                Usuario usuario = resultado.usuario();
                vista.dispose();
                SwingUtilities.invokeLater(() -> new MainView(usuario).setVisible(true));
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

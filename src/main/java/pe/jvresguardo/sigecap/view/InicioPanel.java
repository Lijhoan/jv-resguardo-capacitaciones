package pe.jvresguardo.sigecap.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pe.jvresguardo.sigecap.model.Usuario;

/**
 * HU-JVR-014
 * Panel de inicio mostrado dentro de MainView tras un login exitoso.
 */
public class InicioPanel extends JPanel {

    public InicioPanel(Usuario usuario) {
        super(new GridBagLayout());
        construirContenido(usuario);
    }

    private void construirContenido(Usuario usuario) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Panel principal");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));

        JLabel bienvenida = new JLabel("Bienvenido, " + usuario.getNombreCompleto());
        bienvenida.setFont(bienvenida.getFont().deriveFont(16f));

        JLabel rol = new JLabel("Rol: " + usuario.getRol().getNombre());
        rol.setFont(rol.getFont().deriveFont(14f));

        gbc.gridy = 0;
        add(titulo, gbc);
        gbc.gridy = 1;
        add(bienvenida, gbc);
        gbc.gridy = 2;
        add(rol, gbc);
    }
}

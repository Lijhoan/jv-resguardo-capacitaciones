package pe.jvresguardo.sigecap.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pe.jvresguardo.sigecap.model.Usuario;

/**
 * HU-JVR-009
 * HU-JVR-010
 * HU-JVR-011
 * Panel de inicio mostrado dentro de MainView tras un login exitoso.
 */
public class InicioPanel extends JPanel {

    public InicioPanel(Usuario usuario) {
        super(new GridBagLayout());
        setBackground(EstiloUI.COLOR_FONDO);
        construirContenido(usuario);
    }

    private void construirContenido(Usuario usuario) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Panel principal");
        titulo.setFont(EstiloUI.FUENTE_TITULO);
        titulo.setForeground(EstiloUI.COLOR_TEXTO_PRINCIPAL);

        JLabel bienvenida = new JLabel("Bienvenido, " + usuario.getNombreCompleto());
        bienvenida.setFont(EstiloUI.FUENTE_TEXTO);
        bienvenida.setForeground(EstiloUI.COLOR_TEXTO_PRINCIPAL);

        JLabel rol = new JLabel("Rol: " + usuario.getRol().getNombre());
        rol.setFont(EstiloUI.FUENTE_TEXTO_SECUNDARIO);
        rol.setForeground(EstiloUI.COLOR_TEXTO_SECUNDARIO);

        gbc.gridy = 0;
        add(titulo, gbc);
        gbc.gridy = 1;
        add(bienvenida, gbc);
        gbc.gridy = 2;
        add(rol, gbc);
    }
}

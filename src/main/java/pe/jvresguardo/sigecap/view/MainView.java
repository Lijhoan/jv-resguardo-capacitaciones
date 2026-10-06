package pe.jvresguardo.sigecap.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pe.jvresguardo.sigecap.model.Usuario;

/**
 * HU-JVR-014
 * Ventana principal de SIGECAP J&V, mostrada tras un login exitoso.
 */
public class MainView extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelCentral = new JPanel(cardLayout);

    public MainView(Usuario usuario) {
        super("SIGECAP J&V");
        configurarVentana();
        add(construirBarraSuperior(usuario), BorderLayout.NORTH);
        add(construirMenuLateral(), BorderLayout.WEST);
        add(construirAreaCentral(usuario), BorderLayout.CENTER);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JPanel construirBarraSuperior(Usuario usuario) {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        barra.setBackground(new Color(33, 47, 61));

        JLabel titulo = new JLabel("SIGECAP J&V");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        titulo.setForeground(Color.WHITE);

        JLabel datosUsuario = new JLabel(usuario.getNombreCompleto() + "  |  " + usuario.getRol().getNombre());
        datosUsuario.setForeground(Color.WHITE);

        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        botonCerrarSesion.addActionListener(e -> cerrarSesion());

        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.add(datosUsuario);
        panelDerecho.add(botonCerrarSesion);

        barra.add(titulo, BorderLayout.WEST);
        barra.add(panelDerecho, BorderLayout.EAST);
        return barra;
    }

    private JPanel construirMenuLateral() {
        JPanel menu = new JPanel(new GridLayout(6, 1, 0, 5));
        menu.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        menu.add(crearBotonMenu("Inicio", "inicio"));
        menu.add(crearBotonMenu("Personal", "personal"));
        menu.add(crearBotonMenu("Capacitaciones", "capacitaciones"));
        menu.add(crearBotonMenu("Seguimiento", "seguimiento"));
        menu.add(crearBotonMenu("Reportes", "reportes"));
        menu.add(crearBotonMenu("Usuarios", "usuarios"));

        return menu;
    }

    private JButton crearBotonMenu(String texto, String nombreTarjeta) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> cardLayout.show(panelCentral, nombreTarjeta));
        return boton;
    }

    private JPanel construirAreaCentral(Usuario usuario) {
        panelCentral.add(new InicioPanel(usuario), "inicio");
        panelCentral.add(crearPanelEnDesarrollo("Personal"), "personal");
        panelCentral.add(crearPanelEnDesarrollo("Capacitaciones"), "capacitaciones");
        panelCentral.add(crearPanelEnDesarrollo("Seguimiento"), "seguimiento");
        panelCentral.add(crearPanelEnDesarrollo("Reportes"), "reportes");
        panelCentral.add(crearPanelEnDesarrollo("Usuarios"), "usuarios");
        cardLayout.show(panelCentral, "inicio");
        return panelCentral;
    }

    private JPanel crearPanelEnDesarrollo(String nombreModulo) {
        JPanel panel = new JPanel();
        JLabel mensaje = new JLabel("Módulo " + nombreModulo + " - en desarrollo", SwingConstants.CENTER);
        mensaje.setFont(mensaje.getFont().deriveFont(Font.PLAIN, 16f));
        panel.add(mensaje);
        return panel;
    }

    private void cerrarSesion() {
        dispose();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}

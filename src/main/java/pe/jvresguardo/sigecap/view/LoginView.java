package pe.jvresguardo.sigecap.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pe.jvresguardo.sigecap.controller.LoginController;

/**
 * HU-JVR-013
 * Pantalla de login: primera interfaz visible de SIGECAP J&V.
 */
public class LoginView extends JFrame {

    private final JTextField campoUsuario = new JTextField(15);
    private final JPasswordField campoPassword = new JPasswordField(15);
    private final LoginController loginController = new LoginController();

    public LoginView() {
        super("SIGECAP J&V");
        configurarVentana();
        construirContenido();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirContenido() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("SIGECAP J&V", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));

        JLabel subtitulo = new JLabel("Gestión de Capacitaciones", SwingConstants.CENTER);
        subtitulo.setFont(subtitulo.getFont().deriveFont(Font.PLAIN, 13f));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridy = 1;
        panel.add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        panel.add(campoUsuario, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        panel.add(campoPassword, gbc);

        JButton botonIngresar = new JButton("Ingresar");
        JButton botonSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.add(botonIngresar);
        panelBotones.add(botonSalir);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        botonIngresar.addActionListener(e -> ingresar());
        botonSalir.addActionListener(e -> System.exit(0));
        campoPassword.addActionListener(e -> ingresar());

        setContentPane(panel);
    }

    private void ingresar() {
        String username = campoUsuario.getText().trim();
        char[] password = campoPassword.getPassword();
        loginController.iniciarSesion(username, password, this);
        campoPassword.setText("");
    }
}

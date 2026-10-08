package pe.jvresguardo.sigecap.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pe.jvresguardo.sigecap.model.Usuario;

/**
 * HU-JVR-009
 * HU-JVR-010
 * HU-JVR-011
 * Ventana principal de SIGECAP J&V, mostrada tras un login exitoso.
 * Sigue el sistema visual definido en CLAUDE.md: sidebar oscuro colapsable,
 * barra superior con contexto de usuario y area central con CardLayout.
 */
public class MainView extends JFrame {

    private static final int ANCHO_SIDEBAR_EXPANDIDO = 200;
    private static final int ANCHO_SIDEBAR_CONTRAIDO = 60;

    /**
     * Opcion del menu lateral: icono provisional (ver CLAUDE.md, Iconografia),
     * texto visible y nombre de la tarjeta que muestra en el area central.
     */
    private record ItemMenu(String icono, String texto, String tarjeta) {
    }

    private static final List<ItemMenu> ITEMS_MENU = List.of(
            new ItemMenu("⌂", "Inicio", "inicio"),
            new ItemMenu("◐", "Personal", "personal"),
            new ItemMenu("◇", "Capacitaciones", "capacitaciones"),
            new ItemMenu("◎", "Seguimiento", "seguimiento"),
            new ItemMenu("▤", "Reportes", "reportes"),
            new ItemMenu("◈", "Usuarios", "usuarios"));

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelCentral = new JPanel(cardLayout);
    private final JPanel sidebar = new JPanel();
    private final Map<JButton, ItemMenu> botonesMenu = new LinkedHashMap<>();

    private boolean sidebarExpandido = true;
    private String tarjetaActiva = "inicio";

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
        barra.setBackground(EstiloUI.COLOR_OSCURO);

        JLabel titulo = new JLabel("SIGECAP J&V");
        titulo.setFont(EstiloUI.FUENTE_TITULO.deriveFont(18f));
        titulo.setForeground(EstiloUI.COLOR_TEXTO_CLARO);

        JLabel datosUsuario = new JLabel(usuario.getNombreCompleto() + "  |  " + usuario.getRol().getNombre());
        datosUsuario.setFont(EstiloUI.FUENTE_SUBTITULO);
        datosUsuario.setForeground(EstiloUI.COLOR_TEXTO_CLARO);

        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        estilizarBotonSobreOscuro(botonCerrarSesion);
        botonCerrarSesion.addActionListener(e -> cerrarSesion());

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelDerecho.setOpaque(false);
        panelDerecho.add(datosUsuario);
        panelDerecho.add(botonCerrarSesion);

        barra.add(titulo, BorderLayout.WEST);
        barra.add(panelDerecho, BorderLayout.EAST);
        return barra;
    }

    private void estilizarBotonSobreOscuro(JButton boton) {
        boton.setFont(EstiloUI.FUENTE_BOTON);
        boton.setForeground(EstiloUI.COLOR_TEXTO_CLARO);
        boton.setBackground(EstiloUI.COLOR_ACTIVO);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    }

    private JPanel construirMenuLateral() {
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(EstiloUI.COLOR_OSCURO);
        sidebar.setPreferredSize(new Dimension(ANCHO_SIDEBAR_EXPANDIDO, 0));

        sidebar.add(construirBotonToggle(), BorderLayout.NORTH);
        sidebar.add(construirListaOpciones(), BorderLayout.CENTER);

        return sidebar;
    }

    private JPanel construirBotonToggle() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton botonToggle = new JButton("☰");
        botonToggle.setFont(EstiloUI.FUENTE_MENU);
        botonToggle.setForeground(EstiloUI.COLOR_TEXTO_CLARO);
        botonToggle.setBackground(EstiloUI.COLOR_OSCURO);
        botonToggle.setOpaque(true);
        botonToggle.setBorderPainted(false);
        botonToggle.setFocusPainted(false);
        botonToggle.addActionListener(e -> alternarSidebar());

        contenedor.add(botonToggle, BorderLayout.WEST);
        return contenedor;
    }

    private JPanel construirListaOpciones() {
        JPanel lista = new JPanel();
        lista.setOpaque(false);
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));

        for (ItemMenu item : ITEMS_MENU) {
            JButton boton = crearBotonMenu(item);
            botonesMenu.put(boton, item);
            lista.add(boton);
            lista.add(Box.createVerticalStrut(2));
        }

        actualizarEstilosMenu();
        return lista;
    }

    private JButton crearBotonMenu(ItemMenu item) {
        JButton boton = new JButton();
        boton.setFont(EstiloUI.FUENTE_MENU);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        boton.addActionListener(e -> {
            tarjetaActiva = item.tarjeta();
            cardLayout.show(panelCentral, tarjetaActiva);
            actualizarEstilosMenu();
        });
        return boton;
    }

    private void actualizarEstilosMenu() {
        for (Map.Entry<JButton, ItemMenu> entry : botonesMenu.entrySet()) {
            JButton boton = entry.getKey();
            ItemMenu item = entry.getValue();

            boolean activo = item.tarjeta().equals(tarjetaActiva);
            boton.setBackground(activo ? EstiloUI.COLOR_ACENTO : EstiloUI.COLOR_OSCURO);
            boton.setForeground(EstiloUI.COLOR_TEXTO_CLARO);

            String textoIcono = item.icono() + "  ";
            boton.setText(sidebarExpandido ? textoIcono + item.texto() : item.icono());
            boton.setHorizontalAlignment(sidebarExpandido ? SwingConstants.LEFT : SwingConstants.CENTER);
            boton.setBorder(BorderFactory.createEmptyBorder(10, sidebarExpandido ? 18 : 0, 10, 10));
        }
    }

    private void alternarSidebar() {
        sidebarExpandido = !sidebarExpandido;
        sidebar.setPreferredSize(
                new Dimension(sidebarExpandido ? ANCHO_SIDEBAR_EXPANDIDO : ANCHO_SIDEBAR_CONTRAIDO, 0));
        actualizarEstilosMenu();
        sidebar.revalidate();
        sidebar.repaint();
    }

    private JPanel construirAreaCentral(Usuario usuario) {
        panelCentral.setBackground(EstiloUI.COLOR_FONDO);
        panelCentral.add(new InicioPanel(usuario), "inicio");
        panelCentral.add(new PersonalPanel(), "personal");
        panelCentral.add(crearPanelEnDesarrollo("Capacitaciones"), "capacitaciones");
        panelCentral.add(crearPanelEnDesarrollo("Seguimiento"), "seguimiento");
        panelCentral.add(crearPanelEnDesarrollo("Reportes"), "reportes");
        panelCentral.add(crearPanelEnDesarrollo("Usuarios"), "usuarios");
        cardLayout.show(panelCentral, tarjetaActiva);
        return panelCentral;
    }

    private JPanel crearPanelEnDesarrollo(String nombreModulo) {
        JPanel panel = new JPanel();
        panel.setBackground(EstiloUI.COLOR_FONDO);

        JLabel mensaje = new JLabel("Módulo " + nombreModulo + " - en desarrollo", SwingConstants.CENTER);
        mensaje.setFont(EstiloUI.FUENTE_TEXTO);
        mensaje.setForeground(EstiloUI.COLOR_TEXTO_SECUNDARIO);
        panel.add(mensaje);
        return panel;
    }

    private void cerrarSesion() {
        dispose();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}

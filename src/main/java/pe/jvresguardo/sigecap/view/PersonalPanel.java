package pe.jvresguardo.sigecap.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import pe.jvresguardo.sigecap.controller.PersonalController;
import pe.jvresguardo.sigecap.model.Trabajador;

/**
 * HU-JVR-001
 * Panel de gestion de personal: listado, registro/edicion e historial.
 * El historial se habilitara al integrar capacitaciones.
 */
public class PersonalPanel extends JPanel {

    private static final String[] COLUMNAS = {"DNI", "Nombres", "Apellidos", "Cargo", "Estado"};

    private final PersonalController controller = new PersonalController();
    private final JTabbedPane pestanas = new JTabbedPane();

    private final DefaultTableModel modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modeloTabla);
    private final JTextField campoBusqueda = new JTextField(20);

    private final JTextField campoDni = new JTextField(15);
    private final JTextField campoNombres = new JTextField(20);
    private final JTextField campoApellidos = new JTextField(20);
    private final JTextField campoCargo = new JTextField(20);
    private final JCheckBox checkEstado = new JCheckBox("Activo", true);

    private List<Trabajador> trabajadoresVisibles = List.of();
    private Long idEnEdicion;

    public PersonalPanel() {
        super(new BorderLayout());
        setBackground(EstiloUI.COLOR_FONDO);

        pestanas.addTab("Listado", construirListado());
        pestanas.addTab("Registrar / Editar", construirFormulario());
        pestanas.addTab("Historial", construirHistorial());

        add(pestanas, BorderLayout.CENTER);
        cargarListado();
    }

    private JPanel construirListado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUI.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        barraSuperior.setOpaque(false);

        JButton botonBuscar = new JButton("Buscar");
        JButton botonLimpiar = new JButton("Limpiar");
        JButton botonEditar = new JButton("Editar");
        JButton botonCambiarEstado = new JButton("Activar/Desactivar");

        JLabel etiquetaBusqueda = etiqueta("Buscar:");

        barraSuperior.add(etiquetaBusqueda);
        barraSuperior.add(campoBusqueda);
        barraSuperior.add(botonBuscar);
        barraSuperior.add(botonLimpiar);
        barraSuperior.add(botonEditar);
        barraSuperior.add(botonCambiarEstado);

        tabla.setRowHeight(24);
        tabla.setFont(EstiloUI.FUENTE_TEXTO);
        tabla.getTableHeader().setFont(EstiloUI.FUENTE_BOTON);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        botonBuscar.addActionListener(e -> buscar());
        botonLimpiar.addActionListener(e -> {
            campoBusqueda.setText("");
            cargarListado();
        });
        botonEditar.addActionListener(e -> cargarSeleccionEnFormulario());
        botonCambiarEstado.addActionListener(e -> alternarEstadoSeleccionado());

        panel.add(barraSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(EstiloUI.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        panel.add(etiqueta("DNI:"), gbc);
        gbc.gridx = 1;
        panel.add(campoDni, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(etiqueta("Nombres:"), gbc);
        gbc.gridx = 1;
        panel.add(campoNombres, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(etiqueta("Apellidos:"), gbc);
        gbc.gridx = 1;
        panel.add(campoApellidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(etiqueta("Cargo:"), gbc);
        gbc.gridx = 1;
        panel.add(campoCargo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(etiqueta("Estado:"), gbc);
        gbc.gridx = 1;
        checkEstado.setOpaque(false);
        checkEstado.setFont(EstiloUI.FUENTE_TEXTO);
        panel.add(checkEstado, gbc);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.setFont(EstiloUI.FUENTE_BOTON);
        botonGuardar.setForeground(EstiloUI.COLOR_TEXTO_CLARO);
        botonGuardar.setBackground(EstiloUI.COLOR_ACENTO);
        botonGuardar.setOpaque(true);
        botonGuardar.setBorderPainted(false);
        botonGuardar.setFocusPainted(false);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(EstiloUI.FUENTE_BOTON);
        botonCancelar.setForeground(EstiloUI.COLOR_TEXTO_SECUNDARIO);
        botonCancelar.setFocusPainted(false);

        botonGuardar.addActionListener(e -> guardar());
        botonCancelar.addActionListener(e -> limpiarFormulario());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(botonCancelar);
        panelBotones.add(botonGuardar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        return panel;
    }

    private JPanel construirHistorial() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(EstiloUI.COLOR_FONDO);

        JLabel mensaje = new JLabel("El historial estará disponible al integrar capacitaciones.",
                SwingConstants.CENTER);
        mensaje.setFont(EstiloUI.FUENTE_TEXTO);
        mensaje.setForeground(EstiloUI.COLOR_TEXTO_SECUNDARIO);
        panel.add(mensaje);
        return panel;
    }

    private JLabel etiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(EstiloUI.FUENTE_TEXTO);
        label.setForeground(EstiloUI.COLOR_TEXTO_PRINCIPAL);
        return label;
    }

    private void cargarListado() {
        mostrarEnTabla(controller.listar(this));
    }

    private void buscar() {
        mostrarEnTabla(controller.buscar(campoBusqueda.getText(), this));
    }

    private void mostrarEnTabla(List<Trabajador> trabajadores) {
        trabajadoresVisibles = trabajadores;
        modeloTabla.setRowCount(0);
        for (Trabajador t : trabajadores) {
            modeloTabla.addRow(new Object[] {
                    t.getDni(), t.getNombres(), t.getApellidos(), t.getCargo(),
                    t.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private Trabajador obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= trabajadoresVisibles.size()) {
            JOptionPane.showMessageDialog(this, "Seleccione un trabajador de la lista.", "Personal",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return trabajadoresVisibles.get(fila);
    }

    private void cargarSeleccionEnFormulario() {
        Trabajador trabajador = obtenerSeleccionado();
        if (trabajador == null) {
            return;
        }
        idEnEdicion = trabajador.getId();
        campoDni.setText(trabajador.getDni());
        campoNombres.setText(trabajador.getNombres());
        campoApellidos.setText(trabajador.getApellidos());
        campoCargo.setText(trabajador.getCargo());
        checkEstado.setSelected(trabajador.isEstado());
        pestanas.setSelectedIndex(1);
    }

    private void alternarEstadoSeleccionado() {
        Trabajador trabajador = obtenerSeleccionado();
        if (trabajador == null) {
            return;
        }
        boolean exito = controller.cambiarEstado(trabajador.getId(), !trabajador.isEstado(), this);
        if (exito) {
            cargarListado();
        }
    }

    private void guardar() {
        Trabajador trabajador = new Trabajador(idEnEdicion, campoDni.getText().trim(), campoNombres.getText().trim(),
                campoApellidos.getText().trim(), campoCargo.getText().trim(), checkEstado.isSelected());

        boolean exito = (idEnEdicion == null)
                ? controller.registrar(trabajador, this)
                : controller.actualizar(trabajador, this);

        if (exito) {
            limpiarFormulario();
            cargarListado();
            pestanas.setSelectedIndex(0);
        }
    }

    private void limpiarFormulario() {
        idEnEdicion = null;
        campoDni.setText("");
        campoNombres.setText("");
        campoApellidos.setText("");
        campoCargo.setText("");
        checkEstado.setSelected(true);
    }
}

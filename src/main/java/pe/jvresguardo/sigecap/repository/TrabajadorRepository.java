package pe.jvresguardo.sigecap.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.jvresguardo.sigecap.config.DatabaseConnection;
import pe.jvresguardo.sigecap.model.Trabajador;

/**
 * HU-JVR-001
 * Acceso JDBC a la tabla trabajadores.
 */
public class TrabajadorRepository {

    public Trabajador guardar(Trabajador trabajador) throws SQLException {
        String sql = "INSERT INTO trabajadores (dni, nombres, apellidos, cargo, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, trabajador.getDni());
            stmt.setString(2, trabajador.getNombres());
            stmt.setString(3, trabajador.getApellidos());
            stmt.setString(4, trabajador.getCargo());
            stmt.setBoolean(5, trabajador.isEstado());
            stmt.executeUpdate();

            try (ResultSet llaves = stmt.getGeneratedKeys()) {
                if (llaves.next()) {
                    trabajador.setId(llaves.getLong(1));
                }
            }
        }
        return trabajador;
    }

    public Trabajador buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, dni, nombres, apellidos, cargo, estado FROM trabajadores WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public Trabajador buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT id, dni, nombres, apellidos, cargo, estado FROM trabajadores WHERE dni = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public List<Trabajador> listar() throws SQLException {
        String sql = "SELECT id, dni, nombres, apellidos, cargo, estado FROM trabajadores ORDER BY apellidos, nombres";
        List<Trabajador> trabajadores = new ArrayList<>();
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                trabajadores.add(mapear(rs));
            }
        }
        return trabajadores;
    }

    public List<Trabajador> buscar(String texto) throws SQLException {
        String sql = "SELECT id, dni, nombres, apellidos, cargo, estado FROM trabajadores "
                + "WHERE dni LIKE ? OR nombres LIKE ? OR apellidos LIKE ? OR cargo LIKE ? "
                + "ORDER BY apellidos, nombres";
        String patron = "%" + texto + "%";
        List<Trabajador> trabajadores = new ArrayList<>();
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);
            stmt.setString(4, patron);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trabajadores.add(mapear(rs));
                }
            }
        }
        return trabajadores;
    }

    public void actualizar(Trabajador trabajador) throws SQLException {
        String sql = "UPDATE trabajadores SET dni = ?, nombres = ?, apellidos = ?, cargo = ?, estado = ? "
                + "WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, trabajador.getDni());
            stmt.setString(2, trabajador.getNombres());
            stmt.setString(3, trabajador.getApellidos());
            stmt.setString(4, trabajador.getCargo());
            stmt.setBoolean(5, trabajador.isEstado());
            stmt.setLong(6, trabajador.getId());
            stmt.executeUpdate();
        }
    }

    public void cambiarEstado(Long id, boolean estado) throws SQLException {
        String sql = "UPDATE trabajadores SET estado = ? WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
    }

    private Trabajador mapear(ResultSet rs) throws SQLException {
        return new Trabajador(
                rs.getLong("id"),
                rs.getString("dni"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("cargo"),
                rs.getBoolean("estado"));
    }
}

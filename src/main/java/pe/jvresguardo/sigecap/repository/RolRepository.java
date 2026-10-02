package pe.jvresguardo.sigecap.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.jvresguardo.sigecap.config.DatabaseConnection;
import pe.jvresguardo.sigecap.model.Rol;

/**
 * HU-JVR-012
 * Acceso JDBC a la tabla roles.
 */
public class RolRepository {

    public Rol guardar(Rol rol) throws SQLException {
        String sql = "INSERT INTO roles (nombre, descripcion, estado) VALUES (?, ?, ?)";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            stmt.setBoolean(3, rol.isEstado());
            stmt.executeUpdate();

            try (ResultSet llaves = stmt.getGeneratedKeys()) {
                if (llaves.next()) {
                    rol.setId(llaves.getLong(1));
                }
            }
        }
        return rol;
    }

    public Rol buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, estado FROM roles WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRol(rs);
                }
            }
        }
        return null;
    }

    public List<Rol> listar() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, estado FROM roles ORDER BY nombre";
        List<Rol> roles = new ArrayList<>();
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(mapearRol(rs));
            }
        }
        return roles;
    }

    public void actualizar(Rol rol) throws SQLException {
        String sql = "UPDATE roles SET nombre = ?, descripcion = ?, estado = ? WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            stmt.setBoolean(3, rol.isEstado());
            stmt.setLong(4, rol.getId());
            stmt.executeUpdate();
        }
    }

    public void cambiarEstado(Long id, boolean estado) throws SQLException {
        String sql = "UPDATE roles SET estado = ? WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
    }

    private Rol mapearRol(ResultSet rs) throws SQLException {
        return new Rol(rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getBoolean("estado"));
    }
}

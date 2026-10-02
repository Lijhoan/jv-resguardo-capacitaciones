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
import pe.jvresguardo.sigecap.model.Usuario;

/**
 * HU-JVR-012
 * Acceso JDBC a la tabla usuarios.
 * Al reconstruir un Usuario, carga tambien su Rol asociado (HU-JVR-013).
 */
public class UsuarioRepository {

    private final RolRepository rolRepository = new RolRepository();

    public Usuario guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (username, nombre_completo, email, password, rol_id, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getNombreCompleto());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setLong(5, usuario.getRol().getId());
            stmt.setBoolean(6, usuario.isEstado());
            stmt.executeUpdate();

            try (ResultSet llaves = stmt.getGeneratedKeys()) {
                if (llaves.next()) {
                    usuario.setId(llaves.getLong(1));
                }
            }
        }
        return usuario;
    }

    public Usuario buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, username, nombre_completo, email, password, rol_id, estado "
                + "FROM usuarios WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT id, username, nombre_completo, email, password, rol_id, estado "
                + "FROM usuarios WHERE username = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT id, username, nombre_completo, email, password, rol_id, estado "
                + "FROM usuarios ORDER BY username";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET username = ?, nombre_completo = ?, email = ?, password = ?, "
                + "rol_id = ?, estado = ? WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getNombreCompleto());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setLong(5, usuario.getRol().getId());
            stmt.setBoolean(6, usuario.isEstado());
            stmt.setLong(7, usuario.getId());
            stmt.executeUpdate();
        }
    }

    public void cambiarEstado(Long id, boolean estado) throws SQLException {
        String sql = "UPDATE usuarios SET estado = ? WHERE id = ?";
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Rol rol = rolRepository.buscarPorId(rs.getLong("rol_id"));
        return new Usuario(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("nombre_completo"),
                rs.getString("email"),
                rs.getString("password"),
                rol,
                rs.getBoolean("estado"));
    }
}

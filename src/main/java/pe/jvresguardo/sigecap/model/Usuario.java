package pe.jvresguardo.sigecap.model;

/**
 * HU-JVR-012
 * Representa un usuario del sistema.
 * El campo password almacena el hash de la contraseña, no el valor real (HU-JVR-013).
 */
public class Usuario {

    private Long id;
    private String username;
    private String nombreCompleto;
    private String email;
    private String password;
    private Rol rol;
    private boolean estado;

    public Usuario() {
    }

    public Usuario(String username, String nombreCompleto, String email, String password, Rol rol) {
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = true;
    }

    public Usuario(Long id, String username, String nombreCompleto, String email, String password, Rol rol,
            boolean estado) {
        this.id = id;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", username='" + username + "', rol=" + rol + ", estado=" + estado + "}";
    }
}

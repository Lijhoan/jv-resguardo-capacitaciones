package pe.jvresguardo.sigecap.model;

/**
 * HU-JVR-012
 * Representa un rol del sistema, usado para el control de acceso (HU-JVR-013).
 */
public class Rol {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean estado;

    public Rol() {
    }

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = true;
    }

    public Rol(Long id, String nombre, String descripcion, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Rol{id=" + id + ", nombre='" + nombre + "', estado=" + estado + "}";
    }
}

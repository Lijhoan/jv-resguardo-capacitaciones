package pe.jvresguardo.sigecap.model;

/**
 * HU-JVR-001
 * Representa a un trabajador del personal de operaciones.
 */
public class Trabajador {

    private Long id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String cargo;
    private boolean estado;

    public Trabajador() {
    }

    public Trabajador(String dni, String nombres, String apellidos, String cargo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.estado = true;
    }

    public Trabajador(Long id, String dni, String nombres, String apellidos, String cargo, boolean estado) {
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    @Override
    public String toString() {
        return "Trabajador{id=" + id + ", dni='" + dni + "', nombreCompleto='" + getNombreCompleto()
                + "', cargo='" + cargo + "', estado=" + estado + "}";
    }
}

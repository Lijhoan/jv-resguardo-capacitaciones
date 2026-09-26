package pe.jvresguardo.sigecap.model;

/**
 * HU-JVR-002: datos básicos de un curso.
 */
public class Curso {

    private long id;
    private String codigo;
    private String nombre;
    private String tipo;
    private int vigenciaMeses;
    private String estado;

    public Curso(long id, String codigo, String nombre, String tipo, int vigenciaMeses, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.vigenciaMeses = vigenciaMeses;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVigenciaMeses() {
        return vigenciaMeses;
    }

    public void setVigenciaMeses(int vigenciaMeses) {
        this.vigenciaMeses = vigenciaMeses;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
package modelo;

/**
 * Entidad principal del caso: Peluqueria.
 * Ejercicio 21 - Actividad 3: Colecciones del SDK de Java.
 *
 * Atributos (minimo 5):
 *   - numeroTurno  : identificador principal
 *   - nombreCliente
 *   - tipoServicio
 *   - estado       : PENDIENTE | PROCESADO | CANCELADO
 *   - categoria    : CABELLO | BARBA | TRATAMIENTO
 *
 * @author Gabriela Orozco Garcia
 */
public class ClienteServicio {

    private String numeroTurno;
    private String nombreCliente;
    private String tipoServicio;
    private String estado;
    private String categoria;

    public ClienteServicio(String numeroTurno, String nombreCliente,
                           String tipoServicio, String categoria) {
        this.numeroTurno = numeroTurno;
        this.nombreCliente = nombreCliente;
        this.tipoServicio = tipoServicio;
        this.categoria = categoria;
        this.estado = "PENDIENTE";
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getNumeroTurno() {
        return numeroTurno;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public String getEstado() {
        return estado;
    }

    public String getCategoria() {
        return categoria;
    }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setNumeroTurno(String numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
}
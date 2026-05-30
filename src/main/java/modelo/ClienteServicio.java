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
 *   equals() y hashCode() trabajan con numeroTurno (identificador principal).
 *  * Esto es obligatorio para que las colecciones del SDK funcionen correctamente.
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

    // ── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Turno: "     + numeroTurno
                + " | Cliente: " + nombreCliente
                + " | Servicio: " + tipoServicio
                + " | Categoria: " + categoria
                + " | Estado: "  + estado;
    }

    // ── equals y hashCode por identificador principal: numeroTurno ────────────
    // Las colecciones de Java (HashMap, LinkedList, removeIf, contains)
    // dependen de estos metodos para comparar, buscar y evitar duplicados.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClienteServicio otro = (ClienteServicio) obj;
        return this.numeroTurno.equalsIgnoreCase(otro.numeroTurno);
    }

    @Override
    public int hashCode() {
        return numeroTurno.toLowerCase().hashCode();
    }
}
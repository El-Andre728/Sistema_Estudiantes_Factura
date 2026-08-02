package gt.edu.umg.sistema.facturacion.modelo;

import java.math.BigDecimal;

/**
 * Línea de detalle: texto libre, sin catálogo de productos.
 */
public class DetalleFactura {

    private int id;
    private int facturaId;
    private String descripcion;
    private int cantidad;
    private BigDecimal precioUnitario = BigDecimal.ZERO;
    private BigDecimal subtotal = BigDecimal.ZERO;

    public DetalleFactura() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * cantidad * precioUnitario. BigDecimal no tiene operador *, por eso
     * es .multiply() en vez de simplemente "cantidad * precioUnitario".
     */
    public void calcularSubtotal() {
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}

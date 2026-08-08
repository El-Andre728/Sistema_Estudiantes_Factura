package gt.edu.umg.sistema.facturacion.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Cabecera de factura. Cliente y NIT viven aquí mismo
 */
public class Factura {

    private int id;
    private String numeroFactura;
    private LocalDate fecha;
    private String clienteNombre;
    private String clienteNit;
    private BigDecimal total = BigDecimal.ZERO;
    private List<DetalleFactura> detalles = new ArrayList<>();

    public Factura() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteNit() {
        return clienteNit;
    }

    public void setClienteNit(String clienteNit) {
        this.clienteNit = clienteNit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    /**
     * Suma los subtotales de cada línea. Se llama antes de guardar/actualizar
     */
    public void calcularTotal() {
        BigDecimal suma = BigDecimal.ZERO;
        for (DetalleFactura d : detalles) {
            suma = suma.add(d.getSubtotal());
        }
        this.total = suma;
    }
}

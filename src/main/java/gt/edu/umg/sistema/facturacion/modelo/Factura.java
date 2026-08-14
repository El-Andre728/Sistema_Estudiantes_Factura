package gt.edu.umg.sistema.facturacion.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author garci
 */

public class Factura{
    private int idFactura;
    private LocalDate fecha;
    private Cliente cliente;
    private String numeroFactura;
    private String nombreFactura;
    private List<DetalleFactura> detalles;
    
    //metodos y funciones
    public void agregarDetalle(DetalleFactura detalle){
        detalles.add(detalle);
    }
    public double calcularTotal(){
        double total = 0;
        for(DetalleFactura d : detalles){
            total += d.calcularSubtotal();
        }
        return total;
    }
    
    //constructores
    public Factura(){
        this.detalles = new ArrayList<>();
    }
    
    public Factura(int idFactura, LocalDate fecha, Cliente cliente,String numeroFactura,String nombreFactura, List<DetalleFactura> detalles){
     this.idFactura = idFactura;
     this.fecha = fecha;
     this.cliente = cliente;
     this.numeroFactura = numeroFactura;
     this.nombreFactura = nombreFactura;
     this.detalles = detalles;        
    }
    
    //getters y setters
    public int getIdFactura(){
        return idFactura;
    }
    public void setIdFactura(int idFactura){
        this.idFactura = idFactura;
    }
    
    public LocalDate getFecha(){
        return fecha;
    }
    
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    public String getNumeroFactura(){
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura){
        this.numeroFactura = numeroFactura;
    }
    public String getNombreFactura(){
        return nombreFactura;
    }
    public void setNombreFactura(String nombreFactura){
        this.nombreFactura = nombreFactura;
    }
 
    public List<DetalleFactura> getDetalles(){
        return detalles;
    }
    public void setDetalles(List<DetalleFactura> detalles){
        this.detalles = new ArrayList<>();
    }
}
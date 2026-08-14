package gt.edu.umg.sistema.facturacion.modelo;



/**
 * Línea de detalle: texto libre, sin catálogo de productos.
 */
public class DetalleFactura {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    
    //metodos y funciones
    public double calcularSubtotal(){
        return cantidad * precioUnitario;
    }
    
    //constructores
    public DetalleFactura(){
    }
    
    public DetalleFactura(Producto producto,int cantidad, double precioUnitario){
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }    
    
    //getters y setters
    public Producto getProducto(){
        return producto;
    }
    
    public void setProducto(Producto producto){
        this.producto = producto;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
    
    public double getPrecioUnitario(){
        return precioUnitario;
    }
    
    public void setPrecioUnitario(double precioUnitario){
        this.precioUnitario = precioUnitario;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.modelo;

/**
 *
 * @author garci
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private int existencia;
    private double precio;    
    
    //constructores
    public Producto(){}
    
    public Producto(int idProducto, String nombre, double precio, int existencia) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
    }
    
    //getters y setters
    public int getIdProducto(){
        return idProducto;
    }
    public void setIdProducto(int idProducto){
        this.idProducto = idProducto;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public int getExistencia(){
        return existencia;
    }    
    public void setExistencia(int existencia){
        this.existencia = existencia;
    }    
    public double getPrecio(){
        return precio;        
    }    
    public void setPrecio(double precio){
        this.precio = precio;
    }  
    public boolean hayExistencia(int cantidad){
        return existencia >= cantidad;
    }
}

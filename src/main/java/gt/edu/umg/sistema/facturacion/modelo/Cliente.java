/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.modelo;

/**
 *
 * @author garci
 */
public class Cliente extends Persona{
    private String direccion;
    
    //constructores
    public Cliente (){
        super();
    }
    
    public Cliente(int id,String nombre, String nit, String telefono, String direccion){
        super(id, nombre, nit, telefono);
        this.direccion = direccion;
    }
    //getters y setters
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
}
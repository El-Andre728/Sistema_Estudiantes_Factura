/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.modelo;

/**
 *
 * @author garci
 */
public abstract class Persona {
    private int id;
    private String nombre;
    private String nit;
    private String telefono;

    //metodos y funciones
    public String mostrarInformacion() {
        return "ID: " + id + ", Nombre: " + nombre + ", NIT: " + nit + ", Telefono: " + telefono;
    }

    //getters y setters
     public Persona() {
    }

    public Persona(int id, String nombre, String nit, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
    }

    //getters y setters
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
  
    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
        
    public String getNit(){
        return nit;
    }
    
    public void setNit(String nit){
        this.nit = nit;
    }
    
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }    
}
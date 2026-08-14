/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.dao;

/**
 *
 * @author garci
 */
import gt.edu.umg.sistema.facturacion.modelo.Producto;
import java.util.List;

public interface ProductoDAO {
    void guardar(Producto producto);
    List<Producto> listar();
    Producto buscarPorId(int idProducto);
    void actualizar(Producto producto);
    void eliminar(int idProducto);
}
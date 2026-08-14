/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.dao;

/**
 *
 * @author garci
 */
import gt.edu.umg.sistema.facturacion.modelo.Cliente;
import java.util.List;

public interface ClienteDAO {
    void guardar(Cliente cliente);
    List<Cliente> listar();
    Cliente buscarPorId(int id);
    void actualizar(Cliente cliente);
    void eliminar(int id);
    Cliente buscarPorNit(String nit);
}

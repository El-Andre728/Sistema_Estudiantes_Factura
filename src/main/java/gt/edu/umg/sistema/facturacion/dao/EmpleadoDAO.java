/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.dao;

/**
 *
 * @author garci
 */
import gt.edu.umg.sistema.facturacion.modelo.Empleado;
import java.util.List;

public interface EmpleadoDAO {
    void guardar(Empleado empleado);
    List<Empleado> listar();
    Empleado buscarPorId(int id);
    void actualizar(Empleado empleado);
    void eliminar(int id);
}
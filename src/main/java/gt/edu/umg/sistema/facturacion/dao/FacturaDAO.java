package gt.edu.umg.sistema.facturacion.dao;

import gt.edu.umg.sistema.facturacion.modelo.Factura;
import java.util.List;

public interface FacturaDAO {

    void guardar(Factura factura);

    List<Factura> listar();

    Factura buscarPorId(int id);

    void actualizar(Factura factura);

    void eliminar(int id);
}

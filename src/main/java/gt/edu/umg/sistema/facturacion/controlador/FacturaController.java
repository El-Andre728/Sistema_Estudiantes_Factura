package gt.edu.umg.sistema.facturacion.controlador;

import gt.edu.umg.sistema.facturacion.dao.FacturaDAO;
import gt.edu.umg.sistema.facturacion.dao.FacturaDAOImpl;
import gt.edu.umg.sistema.facturacion.modelo.Factura;

import java.util.List;

public class FacturaController {

    private final FacturaDAO dao;

    public FacturaController() {
        this.dao = new FacturaDAOImpl();
    }

    public void guardar(Factura factura) {
        factura.calcularTotal();
        dao.guardar(factura);
    }

    public List<Factura> listar() {
        return dao.listar();
    }

    public Factura buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(Factura factura) {
        factura.calcularTotal();
        dao.actualizar(factura);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}

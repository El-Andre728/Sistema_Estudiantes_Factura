package gt.edu.umg.sistema.facturacion.dao;

import gt.edu.umg.sistema.facturacion.modelo.DetalleFactura;
import gt.edu.umg.sistema.facturacion.modelo.Factura;
import gt.edu.umg.sistema.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {

    private static final String SQL_INSERT_FACTURA =
        "INSERT INTO factura (numero_factura, fecha, id_cliente, total) VALUES (?, ?, ?, ?)";
    
    private static final String SQL_INSERT_DETALLE =
        "INSERT INTO detalle_factura (factura_id, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE_FACTURA =
        "UPDATE factura SET id_cliente = ?, total = ? WHERE id = ?";

    private static final String SQL_DELETE_DETALLE_POR_FACTURA =
            "DELETE FROM detalle_factura WHERE factura_id = ?";

    private static final String SQL_DELETE_FACTURA =
            "DELETE FROM factura WHERE id = ?";

    private static final String SQL_SELECT_FACTURAS =
            "SELECT id, numero_factura, fecha, id_cliente, total FROM factura ORDER BY id";
    private static final String SQL_SELECT_FACTURA_POR_ID =
            "SELECT id, numero_factura, fecha, id_cliente, total FROM factura WHERE id = ?";
    
    private static final String SQL_SELECT_DETALLE_POR_FACTURA =
        "SELECT id, factura_id, id_producto, cantidad, precio_unitario, subtotal "
        + "FROM detalle_factura WHERE factura_id = ? ORDER BY id";

    @Override
    public void guardar(Factura factura) {
        Connection con;
        try {
            con = Conexion.getConexion();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos.", e);
        }

        try {
            con.setAutoCommit(false); 

            factura.setNumeroFactura(generarNumeroFactura(con));

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, factura.getNumeroFactura());
                ps.setDate(2, java.sql.Date.valueOf(factura.getFecha()));
                ps.setInt(3, factura.getCliente().getId());
                ps.setDouble(4, factura.calcularTotal());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        factura.setIdFactura(rs.getInt(1));
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_DETALLE)) {
                for (DetalleFactura d : factura.getDetalles()) {
                    ps.setInt(1, factura.getIdFactura());
                    ps.setInt(2, d.getProducto().getIdProducto());
                    ps.setInt(3, d.getCantidad());
                    ps.setDouble(4, d.getPrecioUnitario());
                    ps.setDouble(5, d.calcularSubtotal());
                    ps.addBatch(); 
                }
                ps.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new RuntimeException("Error al guardar la factura.", e);
        } finally {
            restaurarAutoCommit(con);
        }
    }

    @Override
    public void actualizar(Factura factura) {
        Connection con;
        try {
            con = Conexion.getConexion();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos.", e);
        }

        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE_FACTURA)) {
                ps.setInt(1, factura.getCliente().getId());
                ps.setDouble(2, factura.calcularTotal());
                ps.setInt(3, factura.getIdFactura());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(SQL_DELETE_DETALLE_POR_FACTURA)) {
                ps.setInt(1, factura.getIdFactura());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_DETALLE)) {
                for (DetalleFactura d : factura.getDetalles()) {
                    ps.setInt(1, factura.getIdFactura());
                    ps.setInt(2, d.getProducto().getIdProducto());
                    ps.setInt(3, d.getCantidad());
                    ps.setDouble(4, d.getPrecioUnitario());
                    ps.setDouble(5, d.calcularSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new RuntimeException("Error al actualizar la factura.", e);
        } finally {
            restaurarAutoCommit(con);
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(SQL_DELETE_FACTURA)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la factura.", e);
        }
    }

    @Override
    public List<Factura> listar() {
        List<Factura> facturas = new ArrayList<>();
        try {
            Connection con = Conexion.getConexion();
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(SQL_SELECT_FACTURAS)) {
                while (rs.next()) {
                    Factura f = mapearFactura(rs);
                    cargarDetalles(f, con);
                    facturas.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas.", e);
        }
        return facturas;
    }
    
    private void cargarDetalles(Factura factura, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_DETALLE_POR_FACTURA)) {
            ps.setInt(1, factura.getIdFactura());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    factura.getDetalles().add(mapearDetalle(rs));
                }
            }
        }
    }

    @Override
    public Factura buscarPorId(int id) {
        Factura factura = null;
        try {
            Connection con = Conexion.getConexion();

            try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_FACTURA_POR_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        factura = mapearFactura(rs);
                    }
                }
            }

            if (factura != null) {
                cargarDetalles(factura, con);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la factura.", e);
        }
        return factura;
    }

    private String generarNumeroFactura(Connection con) throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS siguiente FROM factura";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            int siguiente = 1;
            if (rs.next()) {
                siguiente = rs.getInt("siguiente");
            }
            return String.format("FAC-%04d", siguiente); 
        }
    }

    private void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al hacer rollback.", ex);
        }
    }

    private void restaurarAutoCommit(Connection con) {
        try {
            con.setAutoCommit(true); 
        } catch (SQLException e) {
            
        }
    }

    private Factura mapearFactura(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setIdFactura(rs.getInt("id"));
        f.setNumeroFactura(rs.getString("numero_factura"));
        f.setFecha(rs.getDate("fecha").toLocalDate());
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        f.setCliente(clienteDAO.buscarPorId(rs.getInt("id_cliente")));
        return f;
    }

    private DetalleFactura mapearDetalle(ResultSet rs) throws SQLException {
        DetalleFactura d = new DetalleFactura();
        ProductoDAO productoDAO = new ProductoDAOImpl();
        d.setProducto(productoDAO.buscarPorId(rs.getInt("id_producto")));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getDouble("precio_unitario"));
        return d;
    }
}
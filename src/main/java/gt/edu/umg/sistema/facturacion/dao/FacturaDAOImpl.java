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
            "INSERT INTO factura (numero_factura, fecha, cliente_nombre, cliente_nit, total) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_DETALLE =
            "INSERT INTO detalle_factura (factura_id, descripcion, cantidad, precio_unitario, subtotal) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_FACTURA =
            "UPDATE factura SET cliente_nombre = ?, cliente_nit = ?, total = ? WHERE id = ?";

    private static final String SQL_DELETE_DETALLE_POR_FACTURA =
            "DELETE FROM detalle_factura WHERE factura_id = ?";

    private static final String SQL_DELETE_FACTURA =
            "DELETE FROM factura WHERE id = ?";

    private static final String SQL_SELECT_FACTURAS =
            "SELECT id, numero_factura, fecha, cliente_nombre, cliente_nit, total FROM factura ORDER BY id";

    private static final String SQL_SELECT_FACTURA_POR_ID =
            "SELECT id, numero_factura, fecha, cliente_nombre, cliente_nit, total FROM factura WHERE id = ?";

    private static final String SQL_SELECT_DETALLE_POR_FACTURA =
            "SELECT id, factura_id, descripcion, cantidad, precio_unitario, subtotal "
            + "FROM detalle_factura WHERE factura_id = ? ORDER BY id";

    /**
     * Guarda cabecera + todas sus líneas en UNA transacción. Si algo falla
     * a la mitad (ej. una línea con dato corrupto), rollback: no queda
     * factura huérfana sin detalle ni detalle sin factura.
     */
    @Override
    public void guardar(Factura factura) {
        Connection con;
        try {
            con = Conexion.getConexion();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos.", e);
        }

        try {
            con.setAutoCommit(false); // arranca la transacción: nada se guarda hasta commit()

            factura.setNumeroFactura(generarNumeroFactura(con));

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, factura.getNumeroFactura());
                ps.setDate(2, java.sql.Date.valueOf(factura.getFecha()));
                ps.setString(3, factura.getClienteNombre());
                ps.setString(4, factura.getClienteNit());
                ps.setBigDecimal(5, factura.getTotal());
                ps.executeUpdate();

                // RETURN_GENERATED_KEYS + getGeneratedKeys(): así recupero el id
                // que Postgres le asignó al SERIAL, sin hacer un SELECT aparte.
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        factura.setId(rs.getInt(1));
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_DETALLE)) {
                for (DetalleFactura d : factura.getDetalles()) {
                    ps.setInt(1, factura.getId());
                    ps.setString(2, d.getDescripcion());
                    ps.setInt(3, d.getCantidad());
                    ps.setBigDecimal(4, d.getPrecioUnitario());
                    ps.setBigDecimal(5, d.getSubtotal());
                    ps.addBatch(); // agrupa los inserts de línea en un solo viaje a la DB
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

    /**
     * Actualiza cabecera y reemplaza TODAS las líneas (borra las viejas,
     * inserta las nuevas). Más simple y confiable que tratar de calcular
     * cuáles líneas cambiaron, se borraron o son nuevas.
     */
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
                ps.setString(1, factura.getClienteNombre());
                ps.setString(2, factura.getClienteNit());
                ps.setBigDecimal(3, factura.getTotal());
                ps.setInt(4, factura.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(SQL_DELETE_DETALLE_POR_FACTURA)) {
                ps.setInt(1, factura.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_DETALLE)) {
                for (DetalleFactura d : factura.getDetalles()) {
                    ps.setInt(1, factura.getId());
                    ps.setString(2, d.getDescripcion());
                    ps.setInt(3, d.getCantidad());
                    ps.setBigDecimal(4, d.getPrecioUnitario());
                    ps.setBigDecimal(5, d.getSubtotal());
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

    /**
     * ON DELETE CASCADE en el DDL ya borra el detalle solo, pero el borrado
     * de una factura es una sola sentencia — no necesita transacción manual.
     */
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
                    facturas.add(mapearFactura(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas.", e);
        }
        return facturas;
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
                try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_DETALLE_POR_FACTURA)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            factura.getDetalles().add(mapearDetalle(rs));
                        }
                    }
                }
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
            return String.format("FAC-%04d", siguiente); // %04d = rellena con ceros a la izq hasta 4 dígitos
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
            con.setAutoCommit(true); // deja la conexión lista para la próxima operación normal
        } catch (SQLException e) {
            // No relanzo aquí: ya se resolvió o falló el commit/rollback antes de llegar a este punto.
        }
    }

    private Factura mapearFactura(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setId(rs.getInt("id"));
        f.setNumeroFactura(rs.getString("numero_factura"));
        f.setFecha(rs.getDate("fecha").toLocalDate());
        f.setClienteNombre(rs.getString("cliente_nombre"));
        f.setClienteNit(rs.getString("cliente_nit"));
        f.setTotal(rs.getBigDecimal("total"));
        return f;
    }

    private DetalleFactura mapearDetalle(ResultSet rs) throws SQLException {
        DetalleFactura d = new DetalleFactura();
        d.setId(rs.getInt("id"));
        d.setFacturaId(rs.getInt("factura_id"));
        d.setDescripcion(rs.getString("descripcion"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        d.setSubtotal(rs.getBigDecimal("subtotal"));
        return d;
    }
}

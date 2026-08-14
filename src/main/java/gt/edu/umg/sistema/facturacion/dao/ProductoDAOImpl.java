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
import gt.edu.umg.sistema.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio, existencia) VALUES (?, ?, ?)";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, producto.getNombre());
                ps.setDouble(2, producto.getPrecio());
                ps.setInt(3, producto.getExistencia());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setIdProducto(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, existencia FROM producto";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("existencia")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    @Override
    public Producto buscarPorId(int idProducto) {
        String sql = "SELECT id_producto, nombre, precio, existencia FROM producto WHERE id_producto = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idProducto);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("existencia")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, existencia = ? WHERE id_producto = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, producto.getNombre());
                ps.setDouble(2, producto.getPrecio());
                ps.setInt(3, producto.getExistencia());
                ps.setInt(4, producto.getIdProducto());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int idProducto) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idProducto);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el producto.", e);
        }
    }
}

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
import gt.edu.umg.sistema.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

    
public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, nit, telefono, direccion) VALUES (?, ?, ?, ?)";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getNit());
                ps.setString(3, cliente.getTelefono());
                ps.setString(4, cliente.getDireccion());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nombre, nit, telefono, direccion FROM cliente";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("nit"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT id, nombre, nit, telefono, direccion FROM cliente WHERE id = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("nit"),
                            rs.getString("telefono"),
                            rs.getString("direccion")
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
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, nit = ?, telefono = ?, direccion = ? WHERE id = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getNit());
                ps.setString(3, cliente.getTelefono());
                ps.setString(4, cliente.getDireccion());
                ps.setInt(5, cliente.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el producto.", e);
        }
    }

    @Override
    public Cliente buscarPorNit(String nit) {
        String sql = "SELECT id, nombre, nit, telefono, direccion FROM cliente WHERE nit = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nit);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("nit"),
                            rs.getString("telefono"),
                            rs.getString("direccion")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
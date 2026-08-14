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
import gt.edu.umg.sistema.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO{

    @Override
   public void guardar(Empleado empleado) {
        String sql = "INSERT INTO empleado (nombre, nit, telefono, codigo_empleado, puesto) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, empleado.getNombre());
                ps.setString(2, empleado.getNit());
                ps.setString(3, empleado.getTelefono());
                ps.setString(4, empleado.getCodigoEmpleado());
                ps.setString(5, empleado.getPuesto());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        empleado.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, nit, telefono, codigo_empleado, puesto FROM empleado";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    empleados.add(new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("nit"),
                        rs.getString("telefono"),
                        rs.getString("codigo_empleado"),
                        rs.getString("puesto")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public Empleado buscarPorId(int id) {
        String sql = "SELECT id, nombre, nit, telefono, codigo_empleado, puesto FROM empleado WHERE id = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("nit"),
                            rs.getString("telefono"),
                            rs.getString("codigo_empleado"),
                            rs.getString("puesto")
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
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleado SET nombre = ?, nit = ?, telefono = ?, codigo_empleado = ?, puesto = ? WHERE id = ?";
        try {
            Connection con = Conexion.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, empleado.getNombre());
                ps.setString(2, empleado.getNit());
                ps.setString(3, empleado.getTelefono());
                ps.setString(4, empleado.getCodigoEmpleado());
                ps.setString(5, empleado.getPuesto());
                ps.setInt(6, empleado.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM empleado WHERE id = ?";
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
}

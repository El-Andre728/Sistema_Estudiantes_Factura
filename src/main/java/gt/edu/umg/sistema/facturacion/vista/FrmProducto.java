/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.vista;

/**
 *
 * @author garci
 */
import gt.edu.umg.sistema.facturacion.modelo.Producto;
import gt.edu.umg.sistema.facturacion.dao.ProductoDAO;
import gt.edu.umg.sistema.facturacion.dao.ProductoDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmProducto extends JFrame {

    private JTextField txtNombre, txtPrecio, txtExistencia;
    private JButton btnNuevo, btnGrabar, btnActualizar, btnEliminar, btnCargar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ProductoDAO dao;
    private int idCargado = -1;

    public FrmProducto() {
        super("Gestión de Productos");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        dao = new ProductoDAOImpl();
        
        inicializarComponentes();
        cargarDatosTabla();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        pnlForm.add(new JLabel("Nombre del Producto:"));
        txtNombre = new JTextField();
        pnlForm.add(txtNombre);
        
        pnlForm.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        pnlForm.add(txtPrecio);
        
        pnlForm.add(new JLabel("Existencia:"));
        txtExistencia = new JTextField();
        pnlForm.add(txtExistencia);
        
        add(pnlForm, BorderLayout.NORTH);

        JPanel pnlBotones = new JPanel(new FlowLayout());
        btnNuevo = new JButton("Nuevo");
        btnGrabar = new JButton("Grabar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnGrabar);
        pnlBotones.add(btnActualizar);
        pnlBotones.add(btnEliminar);
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Existencia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        
        JPanel pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.add(pnlBotones, BorderLayout.NORTH);
        pnlCentro.add(scrollTabla, BorderLayout.CENTER);
        
        JPanel pnlSur = new JPanel(new FlowLayout());
        btnCargar = new JButton("Cargar seleccionada");
        pnlSur.add(btnCargar);
        pnlCentro.add(pnlSur, BorderLayout.SOUTH);
        
        add(pnlCentro, BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGrabar.addActionListener(e -> grabar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnCargar.addActionListener(e -> cargarSeleccionada());
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtPrecio.setText("");
        txtExistencia.setText("");
        idCargado = -1;
        btnGrabar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Producto> lista = dao.listar();
            for (Producto p : lista) {
                modeloTabla.addRow(new Object[]{p.getIdProducto(), p.getNombre(), p.getPrecio(), p.getExistencia()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtPrecio.getText().trim().isEmpty() || txtExistencia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void grabar() {
        if (!validarCampos()) return;
        try {
            Producto p = new Producto();
            p.setNombre(txtNombre.getText().trim());
            p.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            p.setExistencia(Integer.parseInt(txtExistencia.getText().trim()));
            
            dao.guardar(p);
            JOptionPane.showMessageDialog(this, "Producto guardado exitosamente.");
            limpiarFormulario();
            cargarDatosTabla();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El precio o la existencia deben ser valores numéricos válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        idCargado = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtExistencia.setText(modeloTabla.getValueAt(fila, 3).toString());
        
        btnGrabar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void actualizar() {
        if (idCargado == -1 || !validarCampos()) return;
        try {
            Producto p = new Producto();
            p.setIdProducto(idCargado);
            p.setNombre(txtNombre.getText().trim());
            p.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            p.setExistencia(Integer.parseInt(txtExistencia.getText().trim()));
            
            dao.actualizar(p);
            JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
            limpiarFormulario();
            cargarDatosTabla();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El precio o la existencia deben ser valores numéricos válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idCargado == -1) return;
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idCargado);
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                limpiarFormulario();
                cargarDatosTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

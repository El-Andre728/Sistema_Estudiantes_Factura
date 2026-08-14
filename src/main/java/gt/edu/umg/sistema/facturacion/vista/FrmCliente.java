/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.facturacion.vista;

/**
 *
 * @author garci
 */
import gt.edu.umg.sistema.facturacion.modelo.Cliente;
import gt.edu.umg.sistema.facturacion.dao.ClienteDAO;
import gt.edu.umg.sistema.facturacion.dao.ClienteDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmCliente extends JFrame {
    private JTextField txtNombre, txtNit, txtTelefono, txtDireccion;
    private JButton btnNuevo, btnGrabar, btnActualizar, btnEliminar, btnCargar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ClienteDAO dao;
    private int idCargado = -1;

    public FrmCliente() {
        super("Gestión de Clientes");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        
        dao = new ClienteDAOImpl();
        
        inicializarComponentes();
        cargarDatosTabla();
        pack();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Panel de Formulario
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        pnlForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        pnlForm.add(txtNombre);
        
        pnlForm.add(new JLabel("NIT:"));
        txtNit = new JTextField();
        pnlForm.add(txtNit);
        
        pnlForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        pnlForm.add(txtTelefono);
        
        pnlForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        pnlForm.add(txtDireccion);
        
        add(pnlForm, BorderLayout.NORTH);

        // Panel de Botones Superiores
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
        
        // Tabla y Panel Central
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "NIT", "Teléfono", "Dirección"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        
        JPanel pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.add(pnlBotones, BorderLayout.NORTH);
        pnlCentro.add(scrollTabla, BorderLayout.CENTER);
        
        // Panel Botón Inferior
        JPanel pnlSur = new JPanel(new FlowLayout());
        btnCargar = new JButton("Cargar seleccionada");
        pnlSur.add(btnCargar);
        pnlCentro.add(pnlSur, BorderLayout.SOUTH);
        
        add(pnlCentro, BorderLayout.CENTER);

        // Eventos
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGrabar.addActionListener(e -> grabar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnCargar.addActionListener(e -> cargarSeleccionada());
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtNit.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        idCargado = -1;
        btnGrabar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private String valorSeguro(Object valor) {
        return valor != null ? valor.toString() : "";
    }
    
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Cliente> lista = dao.listar();
            for (Cliente c : lista) {
                modeloTabla.addRow(new Object[]{c.getId(), c.getNombre(), c.getNit(), c.getTelefono(), c.getDireccion()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtNit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y NIT son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void grabar() {
        if (!validarCampos()) return;
        try {
            Cliente c = new Cliente();
            c.setNombre(txtNombre.getText().trim());
            c.setNit(txtNit.getText().trim());
            c.setTelefono(txtTelefono.getText().trim());
            c.setDireccion(txtDireccion.getText().trim());
            
            dao.guardar(c);
            JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente.");
            limpiarFormulario();
            cargarDatosTabla();
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
        txtNit.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtTelefono.setText(valorSeguro(modeloTabla.getValueAt(fila, 3)));
        txtDireccion.setText(valorSeguro(modeloTabla.getValueAt(fila, 4)));
        
        btnGrabar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void actualizar() {
        if (idCargado == -1 || !validarCampos()) return;
        try {
            Cliente c = new Cliente();
            c.setId(idCargado);
            c.setNombre(txtNombre.getText().trim());
            c.setNit(txtNit.getText().trim());
            c.setTelefono(txtTelefono.getText().trim());
            c.setDireccion(txtDireccion.getText().trim());
            
            dao.actualizar(c);
            JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
            limpiarFormulario();
            cargarDatosTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idCargado == -1) return;
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idCargado);
                JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                limpiarFormulario();
                cargarDatosTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


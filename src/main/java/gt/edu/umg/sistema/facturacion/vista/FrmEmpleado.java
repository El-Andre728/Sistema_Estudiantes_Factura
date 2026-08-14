package gt.edu.umg.sistema.facturacion.vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author garci
 */
import gt.edu.umg.sistema.facturacion.modelo.Empleado;
import gt.edu.umg.sistema.facturacion.dao.EmpleadoDAO;
import gt.edu.umg.sistema.facturacion.dao.EmpleadoDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmEmpleado extends JFrame {

    private JTextField txtNombre, txtNit, txtTelefono, txtCodigoEmpleado, txtPuesto;
    private JButton btnNuevo, btnGrabar, btnActualizar, btnEliminar, btnCargar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private EmpleadoDAO dao;
    private int idCargado = -1;

    public FrmEmpleado() {
        super("Gestión de Empleados");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        
        dao = new EmpleadoDAOImpl();
        
        inicializarComponentes();
        cargarDatosTabla();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
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
        
        pnlForm.add(new JLabel("Código Empleado:"));
        txtCodigoEmpleado = new JTextField();
        pnlForm.add(txtCodigoEmpleado);
        
        pnlForm.add(new JLabel("Puesto:"));
        txtPuesto = new JTextField();
        pnlForm.add(txtPuesto);
        
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
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "NIT", "Teléfono", "Cód. Emp", "Puesto"}, 0) {
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
        txtNit.setText("");
        txtTelefono.setText("");
        txtCodigoEmpleado.setText("");
        txtPuesto.setText("");
        idCargado = -1;
        btnGrabar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Empleado> lista = dao.listar();
            for (Empleado e : lista) {
                modeloTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getNit(), e.getTelefono(), e.getCodigoEmpleado(), e.getPuesto()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar empleados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtCodigoEmpleado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y el código de empleado son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void grabar() {
        if (!validarCampos()) return;
        try {
            Empleado emp = new Empleado();
            emp.setNombre(txtNombre.getText().trim());
            emp.setNit(txtNit.getText().trim());
            emp.setTelefono(txtTelefono.getText().trim());
            emp.setCodigoEmpleado(txtCodigoEmpleado.getText().trim());
            emp.setPuesto(txtPuesto.getText().trim());
            
            dao.guardar(emp);
            JOptionPane.showMessageDialog(this, "Empleado guardado exitosamente.");
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
        txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtCodigoEmpleado.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtPuesto.setText(modeloTabla.getValueAt(fila, 5).toString());
        
        btnGrabar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void actualizar() {
        if (idCargado == -1 || !validarCampos()) return;
        try {
            Empleado emp = new Empleado();
            emp.setId(idCargado);
            emp.setNombre(txtNombre.getText().trim());
            emp.setNit(txtNit.getText().trim());
            emp.setTelefono(txtTelefono.getText().trim());
            emp.setCodigoEmpleado(txtCodigoEmpleado.getText().trim());
            emp.setPuesto(txtPuesto.getText().trim());
            
            dao.actualizar(emp);
            JOptionPane.showMessageDialog(this, "Empleado actualizado exitosamente.");
            limpiarFormulario();
            cargarDatosTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idCargado == -1) return;
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idCargado);
                JOptionPane.showMessageDialog(this, "Empleado eliminado.");
                limpiarFormulario();
                cargarDatosTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

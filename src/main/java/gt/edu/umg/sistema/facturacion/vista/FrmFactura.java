package gt.edu.umg.sistema.facturacion.vista;

import gt.edu.umg.sistema.facturacion.controlador.FacturaController;
import gt.edu.umg.sistema.facturacion.modelo.DetalleFactura;
import gt.edu.umg.sistema.facturacion.modelo.Factura;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrmFactura extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(FrmFactura.class.getName());

    private final FacturaController controller;
    private int idFacturaActual = 0; // 0 = todavía no se ha guardado (factura nueva)

    public FrmFactura() {
        initComponents();
        // DISPOSE_ON_CLOSE en vez de EXIT_ON_CLOSE: si esta ventana se abre
        // desde el menú principal, cerrarla regresa al menú principal.
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        controller = new FacturaController();
        configurarTablaDetalle();
        limpiarFormulario();
        refrescarListado();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblCliente = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        lblNit = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        btnNueva = new javax.swing.JButton();
        scrollDetalle = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        btnAgregarLinea = new javax.swing.JButton();
        btnEliminarLinea = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnGrabar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblListado = new javax.swing.JLabel();
        scrollFacturas = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        btnCargar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Facturación");

        lblNumero.setText("No. Factura");

        txtNumero.setEditable(false);

        lblFecha.setText("Fecha");

        lblCliente.setText("Cliente");

        lblNit.setText("NIT");

        btnNueva.setText("Nueva");
        btnNueva.addActionListener(this::btnNuevaActionPerformed);

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Descripción", "Cantidad", "Precio unitario", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollDetalle.setViewportView(tblDetalle);

        btnAgregarLinea.setText("Agregar línea");
        btnAgregarLinea.addActionListener(this::btnAgregarLineaActionPerformed);

        btnEliminarLinea.setText("Eliminar línea");
        btnEliminarLinea.addActionListener(this::btnEliminarLineaActionPerformed);

        lblTotal.setText("Total");

        txtTotal.setEditable(false);

        btnGrabar.setText("Grabar factura");
        btnGrabar.addActionListener(this::btnGrabarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar.setText("Eliminar factura");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        lblListado.setText("Facturas guardadas");

        tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Número", "Fecha", "Cliente", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollFacturas.setViewportView(tblFacturas);

        btnCargar.setText("Cargar seleccionada");
        btnCargar.addActionListener(this::btnCargarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNumero)
                        .addGap(18, 18, 18)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblFecha)
                        .addGap(18, 18, 18)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCliente)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNit)
                        .addGap(18, 18, 18)
                        .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNueva))
                    .addComponent(scrollDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregarLinea)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarLinea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotal)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGrabar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar))
                    .addComponent(lblListado)
                    .addComponent(scrollFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargar))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumero)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCliente)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNit)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNueva))
                .addGap(18, 18, 18)
                .addComponent(scrollDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarLinea)
                    .addComponent(btnEliminarLinea)
                    .addComponent(lblTotal)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGrabar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar))
                .addGap(18, 18, 18)
                .addComponent(lblListado)
                .addGap(18, 18, 18)
                .addComponent(scrollFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCargar)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ------------------------------------------------------------------
    // Tabla de detalle: cantidad/precio editables, subtotal se calcula solo
    // ------------------------------------------------------------------
    private void configurarTablaDetalle() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Descripción", "Cantidad", "Precio unitario", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 3; // columna 3 = Subtotal, calculada, no editable a mano
            }
        };
        tblDetalle.setModel(modelo);

        // Cada vez que el usuario edita una celda de la tabla, este listener
        // se dispara. Si tocó cantidad o precio, recalcula el subtotal de esa fila.
        modelo.addTableModelListener(e -> {
            int fila = e.getFirstRow();
            int columna = e.getColumn();
            if (fila < 0 || (columna != 1 && columna != 2)) {
                return;
            }
            recalcularFilaDetalle(fila);
        });
    }

    private void recalcularFilaDetalle(int fila) {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        try {
            int cantidad = Integer.parseInt(modelo.getValueAt(fila, 1).toString().trim());
            BigDecimal precio = new BigDecimal(modelo.getValueAt(fila, 2).toString().trim());
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));
            modelo.setValueAt(subtotal.toPlainString(), fila, 3);
            recalcularTotal();
        } catch (NumberFormatException | NullPointerException ex) {
            // El usuario todavía está escribiendo (campo vacío o a medio
            // completar)no hay nada que calcular todavía, se ignora.
        }
    }

    private void recalcularTotal() {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object valor = modelo.getValueAt(i, 3);
            if (valor != null && !valor.toString().isBlank()) {
                total = total.add(new BigDecimal(valor.toString()));
            }
        }
        txtTotal.setText(total.toPlainString());
    }

    // ------------------------------------------------------------------
    // Botones
    // ------------------------------------------------------------------
    private void btnAgregarLineaActionPerformed(ActionEvent evt) {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        modelo.addRow(new Object[]{"", "", "", "0"});
    }

    private void btnEliminarLineaActionPerformed(ActionEvent evt) {
        int fila = tblDetalle.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una línea de la tabla de detalle.");
            return;
        }
        ((DefaultTableModel) tblDetalle.getModel()).removeRow(fila);
        recalcularTotal();
    }

    private void btnNuevaActionPerformed(ActionEvent evt) {
        limpiarFormulario();
    }

    private void btnGrabarActionPerformed(ActionEvent evt) {
        Factura factura = construirFacturaDesdeFormulario();
        if (factura == null) {
            return; 
        }
        try {
            controller.guardar(factura);
            idFacturaActual = factura.getId();
            txtNumero.setText(factura.getNumeroFactura());
            JOptionPane.showMessageDialog(this, "Factura " + factura.getNumeroFactura() + " grabada.");
            refrescarListado();
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, "Error al grabar factura", ex);
            JOptionPane.showMessageDialog(this, "No se pudo grabar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnActualizarActionPerformed(ActionEvent evt) {
        if (idFacturaActual == 0) {
            JOptionPane.showMessageDialog(this, "Primero carga una factura existente de la lista de abajo.");
            return;
        }
        Factura factura = construirFacturaDesdeFormulario();
        if (factura == null) {
            return;
        }
        factura.setId(idFacturaActual);
        try {
            controller.actualizar(factura);
            JOptionPane.showMessageDialog(this, "Factura actualizada.");
            refrescarListado();
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, "Error al actualizar factura", ex);
            JOptionPane.showMessageDialog(this, "No se pudo actualizar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(ActionEvent evt) {
        if (idFacturaActual == 0) {
            JOptionPane.showMessageDialog(this, "Primero carga una factura existente de la lista de abajo.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la factura " + txtNumero.getText() + "? Esto borra también su detalle.",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            controller.eliminar(idFacturaActual);
            JOptionPane.showMessageDialog(this, "Factura eliminada.");
            limpiarFormulario();
            refrescarListado();
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, "Error al eliminar factura", ex);
            JOptionPane.showMessageDialog(this, "No se pudo eliminar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnCargarActionPerformed(ActionEvent evt) {
        int fila = tblFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una factura de la lista.");
            return;
        }
        int id = (int) tblFacturas.getModel().getValueAt(fila, 0);
        Factura factura = controller.buscarPorId(id);
        if (factura == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la factura.");
            return;
        }
        cargarFacturaEnFormulario(factura);
    }

    // ------------------------------------------------------------------
    // Helpers de formulario
    // ------------------------------------------------------------------

    /**
     * Lee los campos y la tabla de detalle y arma un objeto Factura.
     * Devuelve null (y ya mostró el JOptionPane del error) si algo
     * no es válido, así el botón que llamó esto sabe que debe abortar.
     */
    private Factura construirFacturaDesdeFormulario() {
        String cliente = txtCliente.getText().trim();
        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del cliente es obligatorio.");
            return null;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agrega al menos una línea de detalle.");
            return null;
        }

        Factura factura = new Factura();
        factura.setClienteNombre(cliente);
        factura.setClienteNit(txtNit.getText().trim());
        factura.setFecha(parsearFecha(txtFecha.getText().trim()));

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String descripcion = String.valueOf(modelo.getValueAt(i, 0)).trim();
            if (descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La línea " + (i + 1) + " no tiene descripción.");
                return null;
            }
            try {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setDescripcion(descripcion);
                detalle.setCantidad(Integer.parseInt(modelo.getValueAt(i, 1).toString().trim()));
                detalle.setPrecioUnitario(new BigDecimal(modelo.getValueAt(i, 2).toString().trim()));
                detalle.calcularSubtotal();
                factura.getDetalles().add(detalle);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad o precio inválido en la línea " + (i + 1) + ".");
                return null;
            }
        }

        return factura;
    }

    private LocalDate parsearFecha(String texto) {
        try {
            return LocalDate.parse(texto); // espera formato: yyyy/MM/dd
        } catch (RuntimeException ex) {
            return LocalDate.now();
        }
    }

    private void cargarFacturaEnFormulario(Factura factura) {
        idFacturaActual = factura.getId();
        txtNumero.setText(factura.getNumeroFactura());
        txtFecha.setText(factura.getFecha().toString());
        txtCliente.setText(factura.getClienteNombre());
        txtNit.setText(factura.getClienteNit());
        txtTotal.setText(factura.getTotal().toPlainString());

        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        modelo.setRowCount(0);
        for (DetalleFactura d : factura.getDetalles()) {
            modelo.addRow(new Object[]{
                d.getDescripcion(),
                d.getCantidad(),
                d.getPrecioUnitario().toPlainString(),
                d.getSubtotal().toPlainString()
            });
        }
    }

    private void limpiarFormulario() {
        idFacturaActual = 0;
        txtNumero.setText("(se asigna al grabar)");
        txtFecha.setText(LocalDate.now().toString());
        txtCliente.setText("");
        txtNit.setText("");
        txtTotal.setText("0");
        ((DefaultTableModel) tblDetalle.getModel()).setRowCount(0);
    }

    private void refrescarListado() {
        List<Factura> facturas = controller.listar();
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Número", "Fecha", "Cliente", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Factura f : facturas) {
            modelo.addRow(new Object[]{
                f.getId(), f.getNumeroFactura(), f.getFecha(), f.getClienteNombre(), f.getTotal()
            });
        }
        tblFacturas.setModel(modelo);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            FrmFactura frm = new FrmFactura();
            frm.setLocationRelativeTo(null);
            frm.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregarLinea;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarLinea;
    private javax.swing.JButton btnGrabar;
    private javax.swing.JButton btnNueva;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblListado;
    private javax.swing.JLabel lblNit;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JScrollPane scrollDetalle;
    private javax.swing.JScrollPane scrollFacturas;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblFacturas;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}

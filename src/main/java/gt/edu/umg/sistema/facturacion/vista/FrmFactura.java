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

/**
 * Esta clase SÍ tiene FrmFactura.form al lado. Ábrela en NetBeans y entra
 * directo en modo Design, ya armada. El bloque initComponents() de abajo
 * es protegido (GEN-BEGIN/GEN-END) — NetBeans lo regenera solo cuando
 * guardas desde el diseñador. No lo edites a mano.
 */
public class FrmFactura extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(FrmFactura.class.getName());

    private final FacturaController controller;
    private int idFacturaActual = 0; // 0 = todavía no se ha guardado (factura nueva)

    public FrmFactura() {
        initComponents();
        // DISPOSE_ON_CLOSE en vez de EXIT_ON_CLOSE: si esta ventana se abre
        // desde el menú principal, cerrarla no debe matar toda la aplicación.
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        controller = new FacturaController();
        configurarTablaDetalle();
        limpiarFormulario();
        refrescarListado();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblNumero = new JLabel("No. Factura");
        txtNumero = new JTextField();
        txtNumero.setEditable(false);

        lblFecha = new JLabel("Fecha");
        txtFecha = new JTextField();

        lblCliente = new JLabel("Cliente");
        txtCliente = new JTextField();

        lblNit = new JLabel("NIT");
        txtNit = new JTextField();

        btnNueva = new JButton("Nueva");
        btnNueva.addActionListener(this::btnNuevaActionPerformed);

        tblDetalle = new JTable();
        scrollDetalle = new JScrollPane(tblDetalle);

        btnAgregarLinea = new JButton("Agregar línea");
        btnAgregarLinea.addActionListener(this::btnAgregarLineaActionPerformed);

        btnEliminarLinea = new JButton("Eliminar línea");
        btnEliminarLinea.addActionListener(this::btnEliminarLineaActionPerformed);

        lblTotal = new JLabel("Total");
        txtTotal = new JTextField();
        txtTotal.setEditable(false);

        btnGrabar = new JButton("Grabar factura");
        btnGrabar.addActionListener(this::btnGrabarActionPerformed);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar = new JButton("Eliminar factura");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        lblListado = new JLabel("Facturas guardadas");
        tblFacturas = new JTable();
        scrollFacturas = new JScrollPane(tblFacturas);

        btnCargar = new JButton("Cargar seleccionada");
        btnCargar.addActionListener(this::btnCargarActionPerformed);

        setTitle("Facturación");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblNumero).addComponent(txtNumero, 100, 100, 100)
                .addComponent(lblFecha).addComponent(txtFecha, 100, 100, 100)
                .addComponent(lblCliente).addComponent(txtCliente, 200, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblNit).addComponent(txtNit, 120, 120, 120)
                .addComponent(btnNueva))
            .addComponent(scrollDetalle, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnAgregarLinea)
                .addComponent(btnEliminarLinea)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal).addComponent(txtTotal, 120, 120, 120))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnGrabar).addComponent(btnActualizar).addComponent(btnEliminar))
            .addComponent(lblListado)
            .addComponent(scrollFacturas, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(btnCargar)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblNumero).addComponent(txtNumero)
                .addComponent(lblFecha).addComponent(txtFecha)
                .addComponent(lblCliente).addComponent(txtCliente)
                .addComponent(lblNit).addComponent(txtNit)
                .addComponent(btnNueva))
            .addComponent(scrollDetalle, 180, 180, 220)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btnAgregarLinea).addComponent(btnEliminarLinea)
                .addComponent(lblTotal).addComponent(txtTotal))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btnGrabar).addComponent(btnActualizar).addComponent(btnEliminar))
            .addComponent(lblListado)
            .addComponent(scrollFacturas, 150, 150, 180)
            .addComponent(btnCargar)
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
            // completar) — no hay nada que calcular todavía, se ignora.
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
            return; // construirFacturaDesdeFormulario ya mostró el error puntual
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
            return LocalDate.parse(texto); // espera formato ISO: yyyy-MM-dd
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
    private JLabel lblNumero;
    private JTextField txtNumero;
    private JLabel lblFecha;
    private JTextField txtFecha;
    private JLabel lblCliente;
    private JTextField txtCliente;
    private JLabel lblNit;
    private JTextField txtNit;
    private JButton btnNueva;
    private JTable tblDetalle;
    private JScrollPane scrollDetalle;
    private JButton btnAgregarLinea;
    private JButton btnEliminarLinea;
    private JLabel lblTotal;
    private JTextField txtTotal;
    private JButton btnGrabar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JLabel lblListado;
    private JTable tblFacturas;
    private JScrollPane scrollFacturas;
    private JButton btnCargar;
    // End of variables declaration//GEN-END:variables
}

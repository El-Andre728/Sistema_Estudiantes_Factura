package gt.edu.umg.sistema;

import gt.edu.umg.sistema.estudiantes.vista.FrmEstudiante;
import gt.edu.umg.sistema.facturacion.vista.FrmCliente;
import gt.edu.umg.sistema.facturacion.vista.FrmEmpleado;
import gt.edu.umg.sistema.facturacion.vista.FrmFactura;
import gt.edu.umg.sistema.facturacion.vista.FrmProducto;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionEvent;

/**
 * Menú de entrada. Estudiantes y Factura.
 */
public class SistemaPrincipal extends JFrame {

    public SistemaPrincipal() {
        setTitle("Sistema UMG");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Selecciona un módulo", SwingConstants.CENTER);
        JButton btnEstudiantes = new JButton("Estudiantes");
        JButton btnFactura = new JButton("Factura");
        JButton btnCliente = new JButton("Cliente");
        JButton btnEmpleado = new JButton("Empleado");
        JButton btnProducto = new JButton("Producto");


        btnEstudiantes.addActionListener(this::abrirEstudiantes);
        btnFactura.addActionListener(this::abrirFactura);
        btnCliente.addActionListener(this::abrirCliente);
        btnEmpleado.addActionListener(this::abrirEmpleado);
        btnProducto.addActionListener(this::abrirProducto);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(titulo, 250, 250, 250)
            .addComponent(btnEstudiantes, 250, 250, 250)
            .addComponent(btnFactura, 250, 250, 250)
            .addComponent(btnCliente, 250, 250, 250) 
            .addComponent(btnEmpleado, 250, 250, 250) 
            .addComponent(btnProducto, 250, 250, 250) 
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(titulo)
            .addComponent(btnEstudiantes)
            .addComponent(btnFactura)
            .addComponent(btnCliente) 
            .addComponent(btnEmpleado) 
            .addComponent(btnProducto) 
        );

        pack();
    }

    private void abrirEstudiantes(ActionEvent evt) {
        FrmEstudiante frm = new FrmEstudiante();
        frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void abrirFactura(ActionEvent evt) {
        FrmFactura frm = new FrmFactura();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    
    private void abrirCliente(ActionEvent evt) {
        FrmCliente frm = new FrmCliente();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    private void abrirEmpleado(ActionEvent evt) {
        FrmEmpleado frm = new FrmEmpleado();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    private void abrirProducto(ActionEvent evt) {
        FrmProducto frm = new FrmProducto();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            SistemaPrincipal menu = new SistemaPrincipal();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }
}

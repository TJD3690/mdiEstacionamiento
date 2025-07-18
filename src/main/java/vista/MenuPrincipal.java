package vista;

import java.awt.Dimension;
import javax.swing.JDesktopPane;


public class MenuPrincipal extends javax.swing.JFrame {

    public static JDesktopPane JDesktopPane_menu; 
    
     
    public MenuPrincipal() {
        initComponents();
        this.setSize(new Dimension(170,700));
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setTitle("SISTEMA DE GESTION DE ESTACIONAMIENTO (ARBOLITOS FIIS PARKING)");
        
        JDesktopPane_menu= new JDesktopPane();
        
        int ancho=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        this.JDesktopPane_menu.setBounds(0,0,ancho,alto-110);//En qué posicion aparece el jdesktop
        this.add(JDesktopPane_menu);
        
        JInternalCalendario jInternalFrame2 = new JInternalCalendario();
        this.add(jInternalFrame2);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jTablas = new javax.swing.JMenu();
        jTablaEmpleados = new javax.swing.JMenuItem();
        jTablaClientes = new javax.swing.JMenuItem();
        jTablaVehiculos = new javax.swing.JMenuItem();
        jRegistrar = new javax.swing.JMenu();
        jRegistrarEntrada = new javax.swing.JMenuItem();
        jRegistrarSalida = new javax.swing.JMenuItem();
        jRegistrarNuevoUsuario = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jReportes = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(600, 400));

        jTablas.setText("TABLAS");
        jTablas.setPreferredSize(new java.awt.Dimension(150, 25));

        jTablaEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empleados_icon.png"))); // NOI18N
        jTablaEmpleados.setText("Empleados");
        jTablaEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTablaEmpleadosActionPerformed(evt);
            }
        });
        jTablas.add(jTablaEmpleados);

        jTablaClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/xliente_icono.png"))); // NOI18N
        jTablaClientes.setText("Clientes");
        jTablaClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTablaClientesActionPerformed(evt);
            }
        });
        jTablas.add(jTablaClientes);

        jTablaVehiculos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carro_icono.png"))); // NOI18N
        jTablaVehiculos.setText("Vehiculos");
        jTablaVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTablaVehiculosActionPerformed(evt);
            }
        });
        jTablas.add(jTablaVehiculos);

        jMenuBar1.add(jTablas);

        jRegistrar.setText("REGISTRAR");
        jRegistrar.setPreferredSize(new java.awt.Dimension(150, 25));

        jRegistrarEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/entra_icono.png"))); // NOI18N
        jRegistrarEntrada.setText("Entrada");
        jRegistrarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistrarEntradaActionPerformed(evt);
            }
        });
        jRegistrar.add(jRegistrarEntrada);

        jRegistrarSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida_icono.png"))); // NOI18N
        jRegistrarSalida.setText("Salida");
        jRegistrarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistrarSalidaActionPerformed(evt);
            }
        });
        jRegistrar.add(jRegistrarSalida);

        jRegistrarNuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empleados_icon.png"))); // NOI18N
        jRegistrarNuevoUsuario.setText("Nuevo Usuario");
        jRegistrarNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistrarNuevoUsuarioActionPerformed(evt);
            }
        });
        jRegistrar.add(jRegistrarNuevoUsuario);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/xliente_icono.png"))); // NOI18N
        jMenuItem1.setText("Nuevo Cliente");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jRegistrar.add(jMenuItem1);

        jMenuBar1.add(jRegistrar);

        jReportes.setText("REPORTES");
        jReportes.setPreferredSize(new java.awt.Dimension(150, 25));

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/boleta.png"))); // NOI18N
        jMenuItem3.setText("Boletas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jReportes.add(jMenuItem3);

        jMenuBar1.add(jReportes);

        jMenu1.setBackground(new java.awt.Color(204, 51, 0));
        jMenu1.setText("CERRAR SESION");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logout_icono.png"))); // NOI18N
        jMenuItem2.setText("Log Out");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 676, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablaEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTablaEmpleadosActionPerformed
        JInternalFrameTablaEmpleados interGestEmp= new JInternalFrameTablaEmpleados();
        JDesktopPane_menu.add(interGestEmp);
        interGestEmp.setVisible(true);
    }//GEN-LAST:event_jTablaEmpleadosActionPerformed

    private void jRegistrarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRegistrarEntradaActionPerformed
        JInternalFrameRegEntrada interRegEntrada= new JInternalFrameRegEntrada();
        JDesktopPane_menu.add(interRegEntrada);
        interRegEntrada.setVisible(true);
    }//GEN-LAST:event_jRegistrarEntradaActionPerformed

    private void jTablaClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTablaClientesActionPerformed
       JInternalFrameTablaClientes interGestClientes= new JInternalFrameTablaClientes();
        JDesktopPane_menu.add(interGestClientes);
        interGestClientes.setVisible(true);
    }//GEN-LAST:event_jTablaClientesActionPerformed

    private void jRegistrarNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRegistrarNuevoUsuarioActionPerformed
        JInternalFrameRegNuevoUsuario interRegNuevoUsuario = new JInternalFrameRegNuevoUsuario();
        JDesktopPane_menu.add(interRegNuevoUsuario);
        interRegNuevoUsuario.setVisible(true);
        
    }//GEN-LAST:event_jRegistrarNuevoUsuarioActionPerformed

    private void jRegistrarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRegistrarSalidaActionPerformed
        JInternalFrameRegSalida interRegSalida = new JInternalFrameRegSalida();
        JDesktopPane_menu.add(interRegSalida);
        interRegSalida.show();
    }//GEN-LAST:event_jRegistrarSalidaActionPerformed

    private void jTablaVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTablaVehiculosActionPerformed
        JInternalFrameTablaVehiculos interVhc = new JInternalFrameTablaVehiculos();
        JDesktopPane_menu.add(interVhc);
        interVhc.setVisible(true);
    }//GEN-LAST:event_jTablaVehiculosActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
 JInternalFrameRegNuevoCliente interNuevoCliente = new JInternalFrameRegNuevoCliente();
        JDesktopPane_menu.add(interNuevoCliente);
        interNuevoCliente.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.dispose();
         frmLogin login =new frmLogin();
        login.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenu jRegistrar;
    private javax.swing.JMenuItem jRegistrarEntrada;
    private javax.swing.JMenuItem jRegistrarNuevoUsuario;
    private javax.swing.JMenuItem jRegistrarSalida;
    private javax.swing.JMenu jReportes;
    private javax.swing.JMenuItem jTablaClientes;
    private javax.swing.JMenuItem jTablaEmpleados;
    private javax.swing.JMenuItem jTablaVehiculos;
    private javax.swing.JMenu jTablas;
    // End of variables declaration//GEN-END:variables
}

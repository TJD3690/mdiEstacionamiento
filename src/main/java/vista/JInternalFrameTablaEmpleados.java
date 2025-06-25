/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import conexion.Conexion;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.JTextField;
import java.sql.*;
import java.util.Vector;

public class JInternalFrameTablaEmpleados extends javax.swing.JInternalFrame {

    
    public JInternalFrameTablaEmpleados() {
        initComponents();
        this.setSize(new Dimension(894, 553));
        this.CargarTablaEmpleado();
        this.setTitle("Empleados");
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup1.add(HombreEmpleado);
buttonGroup1.add(MujerEmpleado);

        cargarDistritosAutoCompletar(DistritoEmpleado);
        EliminarBotonEmpleado.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        int fila = jTableEmpleados.getSelectedRow();

        if (fila >= 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este empleado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int idEmpleado = Integer.parseInt(jTableEmpleados.getValueAt(fila, 0).toString());

                try (Connection cn = Conexion.establecerConexion();
                     PreparedStatement pst = cn.prepareStatement("DELETE FROM Empleado WHERE IdEmpleado = ?")) {

                    pst.setInt(1, idEmpleado);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente.");
                    CargarTablaEmpleado(); // Refresca la tabla

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un empleado para eliminar.");
        }
    }
});

        BuscarEmpleadoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String dni = BuscarEmpleado.getText().trim();

                System.out.println("Valor ingresado: '" + dni + "'");

                if (dni.isEmpty()) {
                    CargarTablaEmpleado(); // Muestra todos
                } else {
                    buscarEmpleadoPorDni(dni); // Filtro por NumDoc que empieza con...
                }
            }
        });
    

    }
    private void cargarDistritosAutoCompletar(JComboBox comboBox) {
    try {
        Connection con = Conexion.establecerConexion();
        String sql = "SELECT Nombre FROM Distrito ORDER BY Nombre";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        final Vector<String> distritos = new Vector<>();
        while (rs.next()) {
            distritos.add(rs.getString("Nombre"));
        }

        comboBox.setEditable(true);
        comboBox.setModel(new DefaultComboBoxModel(distritos));
        comboBox.setSelectedItem(null); // para que empiece en blanco

        JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();

        editor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String texto = editor.getText();

                Vector<String> coincidencias = new Vector<>();
                for (String item : distritos) {
                    if (item.toLowerCase().startsWith(texto.toLowerCase())) {
                        coincidencias.add(item);
                    }
                }

                if (!coincidencias.isEmpty()) {
                    comboBox.setModel(new DefaultComboBoxModel(coincidencias));
                    comboBox.setSelectedItem(texto);
                    comboBox.showPopup();
                } else {
                    comboBox.hidePopup();
                }
            }
        });

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar distritos: " + e.getMessage());
    }
}
private void buscarEmpleadoPorDni(String dni) {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("IdEmpleado");
    model.addColumn("Nacionalidad");
    model.addColumn("Sexo");
    model.addColumn("Distrito");
    model.addColumn("Nombres");
    model.addColumn("Apellidos");
    model.addColumn("Password");
    model.addColumn("NumDoc");
    model.addColumn("Telefono");
    model.addColumn("Correo");
    model.addColumn("Direccion");
    model.addColumn("Usuario");

    String sql = "SELECT E.IdEmpleado, N.Nombre AS Nacionalidad, S.Nombre AS Sexo, D.Nombre AS Distrito, " +
                 "E.Nombres, E.Apellidos, E.Password, E.NumDoc, E.Telefono, E.Correo, E.Direccion, E.Usuario " +
                 "FROM Empleado E " +
                 "JOIN Nacionalidad N ON E.CodNacio = N.CodNacio " +
                 "JOIN Sexo S ON E.CodSexo = S.CodSexo " +
                 "JOIN Distrito D ON E.CodDistrito = D.CodDistrito " +
                 "WHERE E.NumDoc LIKE ?";

    try (Connection cn = Conexion.establecerConexion();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        pst.setString(1, dni + "%");  // Buscar DNIs que comienzan con ese texto

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Object[] fila = new Object[12];
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace(); // sin JOptionPane
    }

    jTableEmpleados.setModel(model);
}





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmpleados = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;  // nunca editable
            }
        };
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        EliminarBotonEmpleado = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxNacionalidad = new javax.swing.JComboBox<>();
        DistritoEmpleado = new javax.swing.JComboBox<>();
        HombreEmpleado = new javax.swing.JRadioButton();
        MujerEmpleado = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        BuscarEmpleado = new javax.swing.JTextField();
        BuscarEmpleadoBoton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Administrar Empleados");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTableEmpleadosKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTableEmpleados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 710, 250));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("ACTUALIZAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        EliminarBotonEmpleado.setText("ELIMINAR");
        jPanel4.add(EliminarBotonEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 100, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 60, 140, 90));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Apellidos:");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });
        jPanel6.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 180, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Nombres:");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombresActionPerformed(evt);
            }
        });
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresKeyTyped(evt);
            }
        });
        jPanel6.add(txtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 180, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Nacionalidad:");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Distrito:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Contraseña:");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyTyped(evt);
            }
        });
        jPanel6.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 180, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Numero de Doc:");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, -1));

        txtNumDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumDocKeyTyped(evt);
            }
        });
        jPanel6.add(txtNumDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 180, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Teléfono:");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        jPanel6.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 180, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Correo:");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, -1, -1));

        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoKeyTyped(evt);
            }
        });
        jPanel6.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 180, -1));

        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });
        jPanel6.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 180, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Dirección:");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, -1, -1));

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        jPanel6.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 180, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Usuario:");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

        cbxNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Especificado", "Peruana", "Extranjera" }));
        jPanel6.add(cbxNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 180, -1));

        DistritoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Especificado", "Peruana", "Extranjera" }));
        jPanel6.add(DistritoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 180, -1));

        HombreEmpleado.setText("Masculino");
        HombreEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HombreEmpleadoActionPerformed(evt);
            }
        });
        jPanel6.add(HombreEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 150, -1, -1));

        MujerEmpleado.setText("Femenino");
        MujerEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MujerEmpleadoActionPerformed(evt);
            }
        });
        jPanel6.add(MujerEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 150, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Sexo");
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 860, 180));

        BuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarEmpleadoActionPerformed(evt);
            }
        });
        jPanel1.add(BuscarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 200, 110, -1));

        BuscarEmpleadoBoton.setText("BUSCAR ");
        BuscarEmpleadoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarEmpleadoBotonActionPerformed(evt);
            }
        });
        jPanel1.add(BuscarEmpleadoBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 110, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    int selectedRow = jTableEmpleados.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Seleccione un empleado para actualizar.");
        return;
    }
    int idEmpleado = Integer.parseInt(jTableEmpleados.getValueAt(selectedRow, 0).toString());

    String nombres = txtNombres.getText();
    String apellidos = txtApellidos.getText();
    String nacionalidad = (String) cbxNacionalidad.getSelectedItem();
    String distrito = (String) DistritoEmpleado.getSelectedItem();
    String password = txtContraseña.getText();
    String numDoc = txtNumDoc.getText();
    String telefono = txtTelefono.getText();
    String correo = txtCorreo.getText();
    String direccion = txtDireccion.getText();
    String usuario = txtUsuario.getText();

    if (nombres.isEmpty() || apellidos.isEmpty() || numDoc.isEmpty() || telefono.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Complete los campos obligatorios.");
        return;
    }

    int codNacio = getCodNacio(nacionalidad);
    // Asumimos que el sexo se obtiene de otro campo o es fijo (ajústalo según tu interfaz)
    int codSexo = getCodSexo("Masculino"); // Cambia "Masculino" por el valor real si lo tienes
    int codDistrito = getCodDistrito(distrito);

    Connection cn = null;
    PreparedStatement pst = null;
    try {
        cn = Conexion.establecerConexion();
        String sql = "UPDATE dbo.Empleado SET Nombres = ?, Apellidos = ?, CodNacio = ?, CodSexo = ?, CodDistrito = ?, Password = ?, NumDoc = ?, Telefono = ?, Correo = ?, Direccion = ?, Usuario = ? WHERE IdEmpleado = ?";
        pst = cn.prepareStatement(sql);
        pst.setString(1, nombres);
        pst.setString(2, apellidos);
        pst.setInt(3, codNacio);
        pst.setInt(4, codSexo);
        pst.setInt(5, codDistrito);
        pst.setString(6, password);
        pst.setString(7, numDoc);
        pst.setString(8, telefono);
        pst.setString(9, correo);
        pst.setString(10, direccion);
        pst.setString(11, usuario);
        pst.setInt(12, idEmpleado);

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Empleado actualizado correctamente.");
            CargarTablaEmpleado();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el empleado.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (pst != null) pst.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped
        String text= txtNombres.getText();//texto actual
        int lim=30;
        if(text.length()>= lim) evt.consume();
        
        char c= evt.getKeyChar();//c= caracter actual
        // Permite solo letras
        if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
        return;
        }
        // Permite máximo un espacio
        if (c == ' ' && text.contains(" ")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        String text= txtApellidos.getText();//texto actual
        int lim=30;
        if(text.length()>= lim) evt.consume();
        
        char c= evt.getKeyChar();//c= caracter actual
        // Permite solo letras
        if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
        return;
        }
        // Permite máximo un espacio
        if (c == ' ' && text.contains(" ")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
              String text = txtUsuario.getText(); // texto actual
int lim = 30;
if (text.length() >= lim) evt.consume();

char c = evt.getKeyChar(); // carácter actual

// Permite solo letras y máximo un espacio
if (!Character.isLetter(c) && c != ' ') {
    evt.consume();
    return;
}

// Solo se permite un espacio en todo el texto
if (c == ' ' && text.contains(" ")) {
    evt.consume();
}

    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyTyped
                String text = txtContraseña.getText();
int lim = 24;
if (text.length() >= lim) {
    evt.consume();
}

char c = evt.getKeyChar();
String allowedSymbols = "@_-!.#";

// Permitir letras, números y algunos símbolos comunes
if (!Character.isLetterOrDigit(c) && allowedSymbols.indexOf(c) == -1) {
    evt.consume();
}

    }//GEN-LAST:event_txtContraseñaKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
                String text= txtTelefono.getText();
        int lim=9;
        if(text.length() >=9) evt.consume();
        char c=evt.getKeyChar(); //caracter actual
        if(!(Character.isDigit(c))){evt.consume();}
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyTyped
        String text = txtCorreo.getText(); // texto actual
int lim = 40;
if (text.length() >= lim) evt.consume();

char c = evt.getKeyChar(); // carácter actual

// Permitidos: letras, números y símbolos básicos de correo
String permitidos = "@._-";
if (!Character.isLetterOrDigit(c) && permitidos.indexOf(c) == -1) {
    evt.consume();
}
    }//GEN-LAST:event_txtCorreoKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        String text = txtDireccion.getText(); // texto actual
int lim = 50;
char c = evt.getKeyChar(); // carácter actual

// Limitar longitud
if (text.length() >= lim) {
    evt.consume();
    return;
}

// Permitir letras, números y un solo espacio entre palabras
if (!Character.isLetterOrDigit(c) && c != ' ') {
    evt.consume();
    return;
}

// Evitar espacio inicial
if (text.isEmpty() && c == ' ') {
    evt.consume();
    return;
}

// Evitar más de un espacio seguido
if (c == ' ' && text.endsWith(" ")) {
    evt.consume();
}
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtNumDocKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocKeyTyped
        String text = txtNumDoc.getText();
        int lim = 12; // Ajusta el límite según el tipo de documento

        char c = evt.getKeyChar();
        if (text.length() >= lim || !Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumDocKeyTyped

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    private void jTableEmpleadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableEmpleadosKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableEmpleadosKeyTyped

    private void HombreEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HombreEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HombreEmpleadoActionPerformed

    private void MujerEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MujerEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MujerEmpleadoActionPerformed

    private void BuscarEmpleadoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarEmpleadoBotonActionPerformed
        String dni = BuscarEmpleado.getText().trim();

        if (dni.isEmpty()) {
            CargarTablaEmpleado(); // Método que carga todos los empleados
        } else {
            buscarEmpleadoPorDni(dni);
        }
    
    }//GEN-LAST:event_BuscarEmpleadoBotonActionPerformed

    private void BuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarEmpleadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BuscarEmpleado;
    private javax.swing.JButton BuscarEmpleadoBoton;
    private javax.swing.JComboBox<String> DistritoEmpleado;
    private javax.swing.JButton EliminarBotonEmpleado;
    private javax.swing.JRadioButton HombreEmpleado;
    private javax.swing.JRadioButton MujerEmpleado;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxNacionalidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableEmpleados;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    /*Metodo para mostrar los empleados*/
    private void CargarTablaEmpleado() {
    // preparacion de modelo
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("IdEmpleado");
    model.addColumn("Nacionalidad");
    model.addColumn("Sexo");
    model.addColumn("Distrito");
    model.addColumn("Nombres");
    model.addColumn("Apellidos");
    model.addColumn("Password");
    model.addColumn("NumDoc");
    model.addColumn("Telefono");
    model.addColumn("Correo");
    model.addColumn("Direccion");
    model.addColumn("Usuario");

    // rellenando con datos de base
    String sql = 
        "SELECT E.IdEmpleado, N.Nombre AS Nacionalidad, S.Nombre AS Sexo, " +
        "       D.Nombre AS Distrito, E.Nombres, E.Apellidos, E.Password, " +
        "       E.NumDoc, E.Telefono, E.Correo, E.Direccion, E.Usuario " +
        "FROM Empleado E " +
        "  JOIN Nacionalidad N ON E.CodNacio   = N.CodNacio " +
        "  JOIN Sexo        S ON E.CodSexo    = S.CodSexo " +
        "  JOIN Distrito    D ON E.CodDistrito= D.CodDistrito";

    try ( Connection cn = Conexion.establecerConexion();
          Statement st = cn.createStatement();
          ResultSet rs = st.executeQuery(sql) ) {

        while (rs.next()) {
            Object[] fila = new Object[12];
            for (int i = 0; i < fila.length; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            model.addRow(fila);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    // modelo a tabla
    jTableEmpleados.setModel(model);

    // listener de seleccion de fila
    jTableEmpleados.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && jTableEmpleados.getSelectedRow() != -1) {
            int row = jTableEmpleados.getSelectedRow();
            // la columna 0 es IdEmpleado
            int id = Integer.parseInt(jTableEmpleados.getValueAt(row, 0).toString());
            EnviarDatosEmpleadoSeleccionado(id);
        }
    });
}

    /////
    private void EnviarDatosEmpleadoSeleccionado(int idEmpleado) {
    String sql = ""
    + "SELECT E.Nombres, E.Apellidos, N.Nombre AS Nacionalidad, "
    + "       S.Nombre AS Sexo, D.Nombre AS Distrito, E.Password, E.NumDoc, "
    + "       E.Telefono, E.Correo, E.Direccion, E.Usuario "
    + "FROM Empleado E "
    + "JOIN Nacionalidad N ON E.CodNacio = N.CodNacio "
    + "JOIN Sexo S ON E.CodSexo = S.CodSexo "  // 👈 IMPORTANTE
    + "JOIN Distrito D ON E.CodDistrito = D.CodDistrito "
    + "WHERE E.IdEmpleado = ?";

    try ( Connection cn = Conexion.establecerConexion();
          PreparedStatement pst = cn.prepareStatement(sql) ) {

        pst.setInt(1, idEmpleado);
        try ( ResultSet rs = pst.executeQuery() ) {
            if (rs.next()) {
                txtNombres.setText      (rs.getString("Nombres"));
                txtApellidos.setText    (rs.getString("Apellidos"));
                cbxNacionalidad.setSelectedItem(rs.getString("Nacionalidad"));
                DistritoEmpleado.setSelectedItem(rs.getString("Distrito"));
                txtContraseña.setText   (rs.getString("Password"));
                txtNumDoc.setText       (rs.getString("NumDoc"));
                txtTelefono.setText     (rs.getString("Telefono"));
                txtCorreo.setText       (rs.getString("Correo"));
                txtDireccion.setText    (rs.getString("Direccion"));
                txtUsuario.setText      (rs.getString("Usuario"));
String sexo = rs.getString("Sexo");
if ("Masculino".equalsIgnoreCase(sexo)) {
    HombreEmpleado.setSelected(true);
} else if ("Femenino".equalsIgnoreCase(sexo)) {
    MujerEmpleado.setSelected(true);
}


            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private int getCodNacio(String nacionalidad) {
    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int codNacio = 0; // Valor por defecto si no se encuentra
    try {
        cn = Conexion.establecerConexion();
        String sql = "SELECT CodNacio FROM Nacionalidad WHERE Nombre = ?";
        pst = cn.prepareStatement(sql);
        pst.setString(1, nacionalidad);
        rs = pst.executeQuery();
        if (rs.next()) {
            codNacio = rs.getInt("CodNacio");
        } else {
            // Manejo si no se encuentra la nacionalidad (puedes lanzar un error o usar un valor por defecto)
            System.out.println("Nacionalidad no encontrada: " + nacionalidad);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener CodNacio: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return codNacio;
}
    private int getCodSexo(String sexo) {
    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int codSexo = 0; // Valor por defecto si no se encuentra
    try {
        cn = Conexion.establecerConexion();
        String sql = "SELECT CodSexo FROM Sexo WHERE Nombre = ?";
        pst = cn.prepareStatement(sql);
        pst.setString(1, sexo); // Por ejemplo, "Masculino" o "Femenino"
        rs = pst.executeQuery();
        if (rs.next()) {
            codSexo = rs.getInt("CodSexo");
        } else {
            System.out.println("Sexo no encontrado: " + sexo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener CodSexo: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return codSexo;
}
    private int getCodDistrito(String distrito) {
    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int codDistrito = 0; // Valor por defecto si no se encuentra
    try {
        cn = Conexion.establecerConexion();
        String sql = "SELECT CodDistrito FROM Distrito WHERE Nombre = ?";
        pst = cn.prepareStatement(sql);
        pst.setString(1, distrito);
        rs = pst.executeQuery();
        if (rs.next()) {
            codDistrito = rs.getInt("CodDistrito");
        } else {
            System.out.println("Distrito no encontrado: " + distrito);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener CodDistrito: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return codDistrito;
}
}

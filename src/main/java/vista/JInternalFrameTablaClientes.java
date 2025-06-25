/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.JTextField;
import java.sql.*;
import java.util.Vector;
import conexion.Conexion;
import javax.swing.table.DefaultTableModel;




/**
 *
 * @author LENOVO
 */
public class JInternalFrameTablaClientes extends javax.swing.JInternalFrame {

    
    public JInternalFrameTablaClientes() {
        initComponents();
        
        this.setSize(new Dimension(894, 553));
        //this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setTitle("Clientes");
        cargarDistritosAutoCompletar(DistritoCLiente);
        this.CargarTablaClientes();
        
        EliminarBotonCliente.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        int fila = jTableEmpleados.getSelectedRow();

        if (fila >= 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int idCliente = Integer.parseInt(jTableEmpleados.getValueAt(fila, 0).toString());

                try (Connection cn = Conexion.establecerConexion();
                     PreparedStatement pst = cn.prepareStatement("DELETE FROM Cliente WHERE IdCliente = ?")) {

                    pst.setInt(1, idCliente);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    CargarTablaClientes(); // Refresca tabla

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente para eliminar.");
        }
    }
});

        BuscarCLienteBoton.setText("BUSCAR");
    BuscarCLienteBoton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BuscarCLienteBotonActionPerformed(evt);
        }
    });

    // Añadir el botón de búsqueda y el campo de texto al panel
    jPanel1.add(BuscarCLienteBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 100, -1));
    jPanel1.add(BuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 200, 100, -1));
    }
    // Método para buscar clientes por DNI
private void buscarClientePorDni(String dni) {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("IdCliente");
    model.addColumn("Nacionalidad");
    model.addColumn("Sexo");
    model.addColumn("Distrito");
    model.addColumn("Nombres");
    model.addColumn("Apellidos");
    model.addColumn("NumDoc");
    model.addColumn("NroLicencia");
    model.addColumn("Telefono");
    model.addColumn("Correo");
    model.addColumn("Direccion");
    model.addColumn("Usuario");

    // Consulta SQL para buscar clientes por el DNI
    String sql = "SELECT C.IdCliente, N.Nombre AS Nacionalidad, " +
             "       S.Nombre AS Sexo, D.Nombre AS Distrito, " +
             "       C.Nombres, C.Apellidos, C.NumDoc, C.NroLicencia, C.Telefono, " +
             "       C.Correo, C.Direccion, C.Usuario " +
             "FROM Cliente C " +
             "JOIN Nacionalidad N ON C.CodNacio = N.CodNacio " +
             "JOIN Sexo S ON C.CodSexo = S.CodSexo " +  // Relacionamos con la tabla Sexo
             "JOIN Distrito D ON C.CodDistrito = D.CodDistrito " +
             "WHERE C.NumDoc LIKE ?";  // Usamos LIKE para buscar coincidencias parciales



    try (Connection cn = Conexion.establecerConexion();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        // Establecer el valor del DNI en la consulta
        pst.setString(1, dni + "%");  // "%" permite buscar cualquier coincidencia

        try (ResultSet rs = pst.executeQuery()) {
            // Recorrer el resultado y agregar los datos a la tabla
            while (rs.next()) {
                Object[] fila = new Object[12];  // 112 columnas en la tabla
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getObject(i + 1);  // Llenar cada fila con los datos de la consulta
                }
                model.addRow(fila);  // Agregar la fila al modelo de tabla
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Actualizar la tabla con los resultados de la búsqueda
    jTableEmpleados.setModel(model);  // Actualiza la tabla con los resultados
}
    

    private void cargarDistritosAutoCompletar(JComboBox comboBox) {
    try {
        Connection con = Conexion.establecerConexion();
        String sql = "SELECT Nombre FROM Distrito ORDER BY Nombre";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Guardamos todos los nombres
        Vector<String> distritos = new Vector<>();
        while (rs.next()) {
            distritos.add(rs.getString("Nombre"));
        }

        comboBox.setEditable(true);
        comboBox.setModel(new DefaultComboBoxModel(distritos));
        comboBox.setSelectedItem(null); // 👈 Aquí está el cambio que evita mostrar "Abancay" u otro por defecto

        JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();

        editor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String texto = editor.getText();

                if (texto.length() == 0) {
                    comboBox.setModel(new DefaultComboBoxModel(distritos));
                    comboBox.hidePopup();
                    comboBox.setSelectedItem(null); // También reinicia si se borra todo
                    return;
                }

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
            public boolean isCellEditable(int row,int col){
                return false;//nunca editable
            }}
            ;
            jPanel4 = new javax.swing.JPanel();
            jButton1 = new javax.swing.JButton();
            EliminarBotonCliente = new javax.swing.JButton();
            jPanel6 = new javax.swing.JPanel();
            jLabel4 = new javax.swing.JLabel();
            txtApellidosNClientes = new javax.swing.JTextField();
            jLabel5 = new javax.swing.JLabel();
            txtNombresCliente = new javax.swing.JTextField();
            jLabel6 = new javax.swing.JLabel();
            jLabel7 = new javax.swing.JLabel();
            jLabel8 = new javax.swing.JLabel();
            txtContraNCliente = new javax.swing.JTextField();
            jLabel9 = new javax.swing.JLabel();
            txtNumDocNCliente = new javax.swing.JTextField();
            jLabel10 = new javax.swing.JLabel();
            txtTelefonoNCliente = new javax.swing.JTextField();
            jLabel11 = new javax.swing.JLabel();
            txtCorreoNCliente = new javax.swing.JTextField();
            txtDireccionNCliente = new javax.swing.JTextField();
            jLabel12 = new javax.swing.JLabel();
            txtNUsuarioCliente = new javax.swing.JTextField();
            jLabel13 = new javax.swing.JLabel();
            jLabel14 = new javax.swing.JLabel();
            NumLicencia = new javax.swing.JTextField();
            DistritoCLiente = new javax.swing.JComboBox<>();
            cbxNacionaNCliente1 = new javax.swing.JComboBox<>();
            jLabel15 = new javax.swing.JLabel();
            HombreCliente = new javax.swing.JRadioButton();
            MujerCliente = new javax.swing.JRadioButton();
            BuscarCLienteBoton = new javax.swing.JButton();
            BuscarCliente = new javax.swing.JTextField();

            setClosable(true);
            setIconifiable(true);
            getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
            jLabel1.setText("Administrar Clientes");
            jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

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

            EliminarBotonCliente.setText("ELIMINAR");
            EliminarBotonCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    EliminarBotonClienteKeyTyped(evt);
                }
            });
            jPanel4.add(EliminarBotonCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 100, -1));

            jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, 140, 90));

            jPanel6.setBackground(new java.awt.Color(255, 255, 255));
            jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel4.setText("Apellidos:");
            jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

            txtApellidosNClientes.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtApellidosNClientesKeyTyped(evt);
                }
            });
            jPanel6.add(txtApellidosNClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 180, -1));

            jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel5.setText("Nombres:");
            jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

            txtNombresCliente.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtNombresClienteActionPerformed(evt);
                }
            });
            txtNombresCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtNombresClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtNombresCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 180, -1));

            jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel6.setText("Nacionalidad:");
            jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

            jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel7.setText("Distrito:");
            jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, -1, -1));

            jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel8.setText("Contraseña:");
            jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

            txtContraNCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtContraNClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtContraNCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 180, -1));

            jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel9.setText("Numero de Doc:");
            jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, -1));

            txtNumDocNCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtNumDocNClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtNumDocNCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 180, -1));

            jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel10.setText("Teléfono:");
            jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, -1, -1));

            txtTelefonoNCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtTelefonoNClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtTelefonoNCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 180, -1));

            jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel11.setText("Correo:");
            jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, -1, -1));

            txtCorreoNCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtCorreoNClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtCorreoNCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 180, -1));

            txtDireccionNCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtDireccionNClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtDireccionNCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 180, -1));

            jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel12.setText("Dirección:");
            jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

            txtNUsuarioCliente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtNUsuarioClienteKeyTyped(evt);
                }
            });
            jPanel6.add(txtNUsuarioCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 180, -1));

            jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel13.setText("Usuario:");
            jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

            jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel14.setText("Sexo");
            jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, -1, -1));

            NumLicencia.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    NumLicenciaKeyTyped(evt);
                }
            });
            jPanel6.add(NumLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 180, -1));

            DistritoCLiente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Especificado", "Peruana", "Extranjera" }));
            jPanel6.add(DistritoCLiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 180, -1));

            cbxNacionaNCliente1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Especificado", "Peruana", "Extranjera" }));
            jPanel6.add(cbxNacionaNCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 180, -1));

            jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            jLabel15.setText("Numero de licencia:");
            jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

            buttonGroup1.add(HombreCliente);
            HombreCliente.setText("Masculino");
            HombreCliente.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    HombreClienteActionPerformed(evt);
                }
            });
            jPanel6.add(HombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, -1, -1));

            buttonGroup1.add(MujerCliente);
            MujerCliente.setText("Femenino");
            MujerCliente.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    MujerClienteActionPerformed(evt);
                }
            });
            jPanel6.add(MujerCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 150, -1, -1));

            jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 860, 180));

            BuscarCLienteBoton.setText("BUSCAR");
            BuscarCLienteBoton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    BuscarCLienteBotonActionPerformed(evt);
                }
            });
            jPanel1.add(BuscarCLienteBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 100, -1));
            jPanel1.add(BuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 200, 100, -1));

            getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 520));

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNombresClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresClienteKeyTyped
        String text= txtNombresCliente.getText();//texto actual
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
    }//GEN-LAST:event_txtNombresClienteKeyTyped

    private void txtNombresClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresClienteActionPerformed

    private void txtApellidosNClientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosNClientesKeyTyped
        String text= txtApellidosNClientes.getText();//texto actual
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
        
    }//GEN-LAST:event_txtApellidosNClientesKeyTyped

    private void txtNUsuarioClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNUsuarioClienteKeyTyped
              String text = txtNUsuarioCliente.getText(); // texto actual
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

    }//GEN-LAST:event_txtNUsuarioClienteKeyTyped

    private void txtContraNClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraNClienteKeyTyped
                String text = txtContraNCliente.getText();
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

    }//GEN-LAST:event_txtContraNClienteKeyTyped

    private void txtTelefonoNClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoNClienteKeyTyped
                String text= txtTelefonoNCliente.getText();
        int lim=9;
        if(text.length() >=9) evt.consume();
        char c=evt.getKeyChar(); //caracter actual
        if(!(Character.isDigit(c))){evt.consume();}
    }//GEN-LAST:event_txtTelefonoNClienteKeyTyped

    private void txtCorreoNClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoNClienteKeyTyped
        String text = txtCorreoNCliente.getText(); // texto actual
int lim = 40;
if (text.length() >= lim) evt.consume();

char c = evt.getKeyChar(); // carácter actual

// Permitidos: letras, números y símbolos básicos de correo
String permitidos = "@._-";
if (!Character.isLetterOrDigit(c) && permitidos.indexOf(c) == -1) {
    evt.consume();
}
    }//GEN-LAST:event_txtCorreoNClienteKeyTyped

    private void txtDireccionNClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionNClienteKeyTyped
        String text = txtDireccionNCliente.getText(); // texto actual
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
    }//GEN-LAST:event_txtDireccionNClienteKeyTyped

    private void txtNumDocNClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocNClienteKeyTyped
        String text = txtNumDocNCliente.getText();
int lim = 12; // Ajusta el límite según el tipo de documento

char c = evt.getKeyChar();
if (text.length() >= lim || !Character.isDigit(c)) {
    evt.consume();
}
    }//GEN-LAST:event_txtNumDocNClienteKeyTyped

    private void NumLicenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NumLicenciaKeyTyped
        String text = NumLicencia.getText(); // texto actual
int lim = 15;
if (text.length() >= lim) evt.consume();

char c = evt.getKeyChar(); // carácter actual
// Permitir solo números y letras, sin espacios ni símbolos
if (!Character.isLetterOrDigit(c)) {
    evt.consume();
}

    }//GEN-LAST:event_NumLicenciaKeyTyped

    private void BuscarCLienteBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCLienteBotonActionPerformed
  // Obtener el texto del campo de búsqueda
    String dni = BuscarCliente.getText().trim();  // Obtener el DNI o número de documento

    if (dni.isEmpty()) {
        // Si el campo está vacío, mostrar todos los clientes
        CargarTablaClientes();  // Este método cargará todos los clientes
    } else {
        // Si se ha ingresado un DNI, realizar la búsqueda
        buscarClientePorDni(dni);
    }    }//GEN-LAST:event_BuscarCLienteBotonActionPerformed

    private void MujerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MujerClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MujerClienteActionPerformed

    private void HombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HombreClienteActionPerformed

    private void EliminarBotonClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EliminarBotonClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminarBotonClienteKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarCLienteBoton;
    private javax.swing.JTextField BuscarCliente;
    private javax.swing.JComboBox<String> DistritoCLiente;
    private javax.swing.JButton EliminarBotonCliente;
    private javax.swing.JRadioButton HombreCliente;
    private javax.swing.JRadioButton MujerCliente;
    private javax.swing.JTextField NumLicencia;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxNacionaNCliente1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    public static volatile javax.swing.JTable jTableEmpleados;
    private javax.swing.JTextField txtApellidosNClientes;
    private javax.swing.JTextField txtContraNCliente;
    private javax.swing.JTextField txtCorreoNCliente;
    private javax.swing.JTextField txtDireccionNCliente;
    private javax.swing.JTextField txtNUsuarioCliente;
    private javax.swing.JTextField txtNombresCliente;
    private javax.swing.JTextField txtNumDocNCliente;
    private javax.swing.JTextField txtTelefonoNCliente;
    // End of variables declaration//GEN-END:variables
private void CargarTablaClientes() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("IdCliente");
    model.addColumn("Nacionalidad");
    model.addColumn("Sexo");  // Agregar la columna Sexo
    model.addColumn("Distrito");
    model.addColumn("Nombres");
    model.addColumn("Apellidos");
    model.addColumn("NumDoc");
    model.addColumn("NroLicencia");
    model.addColumn("Telefono");
    model.addColumn("Correo");
    model.addColumn("Direccion");
    model.addColumn("Usuario");  // Agregar la columna Usuario

    // La consulta SQL que ahora incluye el campo 'Usuario'
    String sql = "SELECT C.IdCliente, N.Nombre AS Nacionalidad, S.Nombre AS Sexo, " +
                 "       D.Nombre AS Distrito, C.Nombres, C.Apellidos, C.NumDoc, " +
                 "       C.NroLicencia, C.Telefono, C.Correo, C.Direccion, C.Usuario " +
                 "FROM Cliente C " +
                 "  JOIN Nacionalidad N ON C.CodNacio = N.CodNacio " +
                 "  JOIN Sexo S ON C.CodSexo = S.CodSexo " +  // Relacionamos con la columna Sexo
                 "  JOIN Distrito D ON C.CodDistrito = D.CodDistrito";

    try (Connection cn = Conexion.establecerConexion();
         Statement st = cn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            Object[] fila = new Object[12];  

    // Recuperar los datos de las columnas
    fila[0] = rs.getObject(1);  // IdCliente
    fila[1] = rs.getObject(2);  // Nacionalidad
    fila[2] = rs.getObject(3);  // Sexo
    fila[3] = rs.getObject(4);  // Distrito
    fila[4] = rs.getObject(5);  // Nombres
    fila[5] = rs.getObject(6);  // Apellidos
    fila[6] = rs.getObject(7);  // NumDoc
    fila[7] = rs.getObject(8);  // NroLicencia
    fila[8] = rs.getObject(9);  // Telefono
    fila[9] = rs.getObject(10); // Correo
    fila[10] = rs.getObject(11); // Direccion
    fila[11] = rs.getObject(12); // Usuario

    // Añadir la fila al modelo
    model.addRow(fila);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    jTableEmpleados.setModel(model);  // Este es el JTable de clientes
    // Añadir el evento para manejar la selección de una fila en la tabla
    jTableEmpleados.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && jTableEmpleados.getSelectedRow() != -1) {
            int row = jTableEmpleados.getSelectedRow();
            int id = Integer.parseInt(jTableEmpleados.getValueAt(row, 0).toString());
            EnviarDatosClienteSeleccionado(id);
        }
    });
}



    
    
private void EnviarDatosClienteSeleccionado(int idCliente) {
    // Verificar que la consulta SQL esté obteniendo los datos correctos
    String sql = "SELECT C.Nombres, C.Apellidos, N.Nombre AS Nacionalidad, " +
                 "       S.Nombre AS Sexo, D.Nombre AS Distrito, C.NumDoc, " +
                 "       C.NroLicencia, C.Telefono, C.Correo, C.Direccion, C.Usuario " +
                 "FROM Cliente C " +
                 "  JOIN Nacionalidad N ON C.CodNacio = N.CodNacio " +
                 "  JOIN Sexo S ON C.CodSexo = S.CodSexo " + 
                 "  JOIN Distrito D ON C.CodDistrito = D.CodDistrito " +
                 "WHERE C.IdCliente = ?";  // Solo seleccionamos el cliente por ID

    try (Connection cn = Conexion.establecerConexion();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        pst.setInt(1, idCliente);  // Establecer el idCliente
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                // Asignar los valores de la consulta a los campos correspondientes
                txtNombresCliente.setText(rs.getString("Nombres"));
                txtApellidosNClientes.setText(rs.getString("Apellidos"));
                cbxNacionaNCliente1.setSelectedItem(rs.getString("Nacionalidad"));
                DistritoCLiente.setSelectedItem(rs.getString("Distrito"));
                txtNumDocNCliente.setText(rs.getString("NumDoc"));
                NumLicencia.setText(rs.getString("NroLicencia"));
                txtTelefonoNCliente.setText(rs.getString("Telefono"));
                txtCorreoNCliente.setText(rs.getString("Correo"));
                txtDireccionNCliente.setText(rs.getString("Direccion"));
                
                // Depuración: Verificar si se obtiene el valor de 'Usuario' correctamente
                String usuario = rs.getString("Usuario");
                System.out.println("Usuario recuperado: " + usuario);  // Depuración para verificar si el valor es correcto
                txtNUsuarioCliente.setText(usuario != null ? usuario : "");  // Si 'Usuario' es nulo, lo dejamos vacío

                // Asignar el género
                String sexo = rs.getString("Sexo");
                if ("Masculino".equals(sexo)) {
                    HombreCliente.setSelected(true);
                } else if ("Femenino".equals(sexo)) {
                    MujerCliente.setSelected(true);
                }
            } else {
                System.out.println("No se encontró el cliente con ID: " + idCliente);  // Depuración: Si no se encuentra el cliente
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();  // Imprimir errores para ayudar con la depuración
    }
}










}


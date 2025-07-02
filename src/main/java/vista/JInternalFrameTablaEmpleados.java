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
import java.util.Vector;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import javax.swing.JComboBox;


public class JInternalFrameTablaEmpleados extends javax.swing.JInternalFrame {

    // Constructor de la clase JInternalFrameTablaEmpleados
    public JInternalFrameTablaEmpleados() {
        initComponents();  // Inicializa los componentes de la interfaz gráfica
        this.setSize(new Dimension(894, 553));  // Establece el tamaño de la ventana
        this.setTitle("Empleados");  // Establece el título de la ventana

        // Configuración del grupo de botones para seleccionar el sexo
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup1.add(HombreEmpleado);  // Añadir el botón de Hombre
        buttonGroup1.add(MujerEmpleado);  // Añadir el botón de Mujer

        // Cargar distritos en el combo box con autocompletado
        cargarDistritosAutoCompletar(DistritoEmpleado);
        
        // Cargar la tabla con los empleados
        CargarTablaEmpleado();

        // Acción del botón EliminarEmpleado
        EliminarBotonEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int fila = jTableEmpleados.getSelectedRow();  // Obtener la fila seleccionada en la tabla

                // Verificar si se ha seleccionado una fila
                if (fila >= 0) {
                    // Mostrar un cuadro de confirmación antes de eliminar
                    int confirm = JOptionPane.showConfirmDialog(null, 
                                                                "¿Estás seguro de eliminar este empleado?", 
                                                                "Confirmar eliminación", 
                                                                JOptionPane.YES_NO_OPTION);
                    
                    // Si se confirma la eliminación
                    if (confirm == JOptionPane.YES_OPTION) {
                        int idEmpleado = Integer.parseInt(jTableEmpleados.getValueAt(fila, 0).toString());  // Obtener el ID del empleado

                        // Conectar a la base de datos y ejecutar la eliminación
                        try (Connection cn = Conexion.establecerConexion();
                             PreparedStatement pst = cn.prepareStatement("DELETE FROM Empleado WHERE IdEmpleado = ?")) {
                            pst.setInt(1, idEmpleado);  // Establecer el ID del empleado en la consulta
                            pst.executeUpdate();  // Ejecutar la consulta de eliminación

                            JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente.");
                            CargarTablaEmpleado();  // Refrescar la tabla de empleados

                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado para eliminar.");
                }
            }   
        });

        // Acción del botón BuscarVentana
        BuscarVentana.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear la ventana de búsqueda
                Buscador buscadorVentana = new Buscador();

                // Añadir la ventana de búsqueda al JDesktopPane
                MenuPrincipal.JDesktopPane_menu.add(buscadorVentana);

                // Hacer que la ventana de búsqueda esté al frente
                buscadorVentana.toFront();

                // Mostrar la ventana de búsqueda
                buscadorVentana.setVisible(true);
            }
        });

        // Acción del botón BuscarEmpleado
        // Acción del botón BuscarEmpleado
// Acción del botón BuscarEmpleado
BuscarEmpleadoBoton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        String busqueda = BuscarEmpleado.getText().trim();  // Obtener el texto del campo de búsqueda
        String criterioBusqueda = (String) EmpleadoComboBox.getSelectedItem();  // Obtener el valor seleccionado en el ComboBox

        // Verificar si el campo de búsqueda está vacío
        if (busqueda.isEmpty()) {
            CargarTablaEmpleado();  // Si está vacío, mostrar todos los empleados
        } else {
            // Realizar la búsqueda con el campo seleccionado como criterio
            buscarEmpleadoPorCriterio(criterioBusqueda, busqueda); 
        }
    }
});


    }

private void buscarEmpleadoPorCriterio(String campo, String valor) {
    // Crear un modelo de tabla para mostrar los resultados
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

    // Construcción de la consulta SQL
    String sql = "SELECT E.IdEmpleado, N.Nombre AS Nacionalidad, S.Nombre AS Sexo, D.Nombre AS Distrito, " +
                 "E.Nombres, E.Apellidos, E.Password, E.NumDoc, E.Telefono, E.Correo, E.Direccion, E.Usuario " +
                 "FROM Empleado E " +
                 "JOIN Nacionalidad N ON E.CodNacio = N.CodNacio " +
                 "JOIN Sexo S ON E.CodSexo = S.CodSexo " +
                 "JOIN Distrito D ON E.CodDistrito = D.CodDistrito " +
                 "WHERE ";

    // Identificar el campo a filtrar, si es comboBox o un JTextField
    if (campo.equals("Nacionalidad")) {
        sql += "N.Nombre LIKE ?";  // Buscar por Nacionalidad
    } else if (campo.equals("Sexo")) {
        sql += "S.Nombre LIKE ?";  // Buscar por Sexo
    } else if (campo.equals("Distrito")) {
        sql += "D.Nombre LIKE ?";  // Buscar por Distrito
    } else if (campo.equals("Telefono")) {
        sql += "E.Telefono LIKE ?";  // Buscar por Teléfono
    } else if (campo.equals("NumDoc")) {
        sql += "E.NumDoc LIKE ?";  // Buscar por Número de Documento
    } else if (campo.equals("Direccion")) {
        sql += "E.Direccion LIKE ?";  // Buscar por Dirección
    } else if (campo.equals("Password")) {
        sql += "E.Password LIKE ?";  // Buscar por Contraseña
    } else {
        // Para campos de texto libre, como Nombres o Apellidos
        sql += "E." + campo + " LIKE ?";  // Usamos el campo directamente
    }

    // Conexión a la base de datos
    try (Connection cn = Conexion.establecerConexion();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        // Configuración de la búsqueda parcial para que solo busque los valores que empiecen con el valor ingresado
        pst.setString(1, valor + "%");

        // Ejecutar la consulta y recorrer los resultados
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Object[] fila = new Object[12];  // Crear una fila para los datos del empleado
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getObject(i + 1);  // Asignar datos al arreglo fila
                }
                model.addRow(fila);  // Agregar la fila al modelo de la tabla
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();  // Si ocurre un error, mostrar la traza del error
    }

    // Actualizar el modelo de la tabla con los resultados de la búsqueda
    jTableEmpleados.setModel(model);  // Establecer el modelo de la tabla
}
















// Método para cargar distritos con autocompletado en un JComboBox
    private void cargarDistritosAutoCompletar(JComboBox comboBox) {
    try {
        // Establecer la conexión con la base de datos
        Connection con = Conexion.establecerConexion();
        
        // Consulta SQL para obtener los nombres de los distritos
        String sql = "SELECT Nombre FROM Distrito ORDER BY Nombre";
        
        // Preparar la sentencia SQL
        PreparedStatement ps = con.prepareStatement(sql);
        
        // Ejecutar la consulta y obtener los resultados
        ResultSet rs = ps.executeQuery();

        // Crear un Vector para almacenar los nombres de los distritos
        final Vector<String> distritos = new Vector<>();
        
        // Recorrer los resultados de la consulta y añadir los nombres de los distritos al Vector
        while (rs.next()) {
            distritos.add(rs.getString("Nombre"));
        }

        // Configurar el JComboBox para que sea editable
        comboBox.setEditable(true);
        
        // Establecer el modelo del JComboBox con los distritos obtenidos
        comboBox.setModel(new DefaultComboBoxModel(distritos));
        
        // Asegurarse de que el JComboBox esté vacío al iniciar (sin selección)
        comboBox.setSelectedItem(null);

        // Obtener el editor del JComboBox para capturar las teclas presionadas
        JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();

        // Agregar un KeyListener para detectar cuando el usuario escribe en el campo de texto
        editor.addKeyListener(new java.awt.event.KeyAdapter() {
            // Este método se ejecuta cada vez que se escribe una tecla
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String texto = editor.getText();  // Obtener el texto ingresado en el JComboBox

                // Crear un Vector para almacenar las coincidencias encontradas
                Vector<String> coincidencias = new Vector<>();
                
                // Buscar distritos que coincidan con lo que el usuario ha escrito
                for (String item : distritos) {
                    if (item.toLowerCase().startsWith(texto.toLowerCase())) {  // Compara sin importar mayúsculas/minúsculas
                        coincidencias.add(item);  // Añadir coincidencia al Vector
                    }
                }

                // Si hay coincidencias, actualizar el JComboBox con los resultados
                if (!coincidencias.isEmpty()) {
                    comboBox.setModel(new DefaultComboBoxModel(coincidencias));
                    comboBox.setSelectedItem(texto);  // Establecer el texto ingresado
                    comboBox.showPopup();  // Mostrar las coincidencias en un popup
                } else {
                    comboBox.hidePopup();  // Si no hay coincidencias, ocultar el popup
                }
            }
        });

        // Cerrar la conexión a la base de datos
        con.close();
    } catch (Exception e) {
        // Si ocurre un error, mostrar un mensaje de error
        JOptionPane.showMessageDialog(null, "Error al cargar distritos: " + e.getMessage());
    }
}


// Método para buscar empleados por su DNI








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
        BuscarVentana = new javax.swing.JButton();
        EmpleadoComboBox = new javax.swing.JComboBox<>();

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
        jPanel1.add(BuscarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 170, 120, -1));

        BuscarEmpleadoBoton.setText("BUSCAR ");
        BuscarEmpleadoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarEmpleadoBotonActionPerformed(evt);
            }
        });
        jPanel1.add(BuscarEmpleadoBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 230, 120, -1));

        BuscarVentana.setText("jButton2");
        BuscarVentana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVentanaActionPerformed(evt);
            }
        });
        jPanel1.add(BuscarVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        EmpleadoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombres", "Apellidos", "Nacionalidad", "Distrito", "Direccion", "Password", "NumDoc", "Telefono", "Usuario", "Correo", "Sexo" }));
        jPanel1.add(EmpleadoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 200, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // Obtener la fila seleccionada en la tabla
    int selectedRow = jTableEmpleados.getSelectedRow();

    // Verificar si no se ha seleccionado ninguna fila
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Seleccione un empleado para actualizar.");
        return;  // Salir del método si no hay fila seleccionada
    }

    // Obtener el ID del empleado seleccionado
    int idEmpleado = Integer.parseInt(jTableEmpleados.getValueAt(selectedRow, 0).toString());

    // Obtener los datos ingresados en los campos de texto
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

    // Verificar si los campos obligatorios están completos
    if (nombres.isEmpty() || apellidos.isEmpty() || numDoc.isEmpty() || telefono.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Complete los campos obligatorios.");
        return;  // Salir si los campos obligatorios no están completos
    }

    // Obtener los códigos correspondientes a la nacionalidad, sexo y distrito
    int codNacio = getCodNacio(nacionalidad);
    int codSexo = getCodSexo("Masculino");  // Asumir que el sexo es "Masculino"; ajustarlo si es necesario
    int codDistrito = getCodDistrito(distrito);

    // Declarar la conexión y el PreparedStatement
    Connection cn = null;
    PreparedStatement pst = null;

    try {
        // Establecer la conexión a la base de datos
        cn = Conexion.establecerConexion();
        
        // Consulta SQL para actualizar los datos del empleado
        String sql = "UPDATE dbo.Empleado SET Nombres = ?, Apellidos = ?, CodNacio = ?, CodSexo = ?, CodDistrito = ?, Password = ?, NumDoc = ?, Telefono = ?, Correo = ?, Direccion = ?, Usuario = ? WHERE IdEmpleado = ?";

        // Preparar la sentencia SQL
        pst = cn.prepareStatement(sql);

        // Establecer los valores de los parámetros en la consulta
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
        pst.setInt(12, idEmpleado);  // Establecer el ID del empleado a actualizar

        // Ejecutar la actualización
        int rowsAffected = pst.executeUpdate();

        // Verificar si la actualización fue exitosa
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Empleado actualizado correctamente.");
            CargarTablaEmpleado();  // Refrescar la tabla de empleados
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el empleado.");
        }

    } catch (SQLException e) {
        // Mostrar mensaje de error si ocurre alguna excepción
        JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // Cerrar la conexión y el PreparedStatement en el bloque finally
        try {
            if (pst != null) pst.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir cualquier error al cerrar los recursos
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
        }
    
    }//GEN-LAST:event_BuscarEmpleadoBotonActionPerformed

    private void BuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarEmpleadoActionPerformed

    private void BuscarVentanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVentanaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarVentanaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BuscarEmpleado;
    private javax.swing.JButton BuscarEmpleadoBoton;
    private javax.swing.JButton BuscarVentana;
    private javax.swing.JComboBox<String> DistritoEmpleado;
    private javax.swing.JButton EliminarBotonEmpleado;
    private javax.swing.JComboBox<String> EmpleadoComboBox;
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

//1) CargarTablaEmpleado    
private void CargarTablaEmpleado() {
    // Preparación del modelo de la tabla (estructura de las columnas)
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

    // SQL para obtener los empleados, con JOIN para traer los datos relacionados de otras tablas (Nacionalidad, Sexo, Distrito)
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

        // Itera sobre los resultados y añade las filas a la tabla
        while (rs.next()) {
            Object[] fila = new Object[12];  // Crea un array para almacenar los datos de cada empleado
            for (int i = 0; i < fila.length; i++) {
                fila[i] = rs.getObject(i + 1);  // Asigna el valor de cada columna a la fila
            }
            model.addRow(fila);  // Añade la fila al modelo de la tabla
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Si hay error, imprime la traza del error
    }

    // Establece el modelo de la tabla
    jTableEmpleados.setModel(model);

    // Listener de selección de fila: cuando seleccionas una fila, se obtienen los datos del empleado
    jTableEmpleados.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && jTableEmpleados.getSelectedRow() != -1) {
            int row = jTableEmpleados.getSelectedRow();
            // La columna 0 es el IdEmpleado
            int id = Integer.parseInt(jTableEmpleados.getValueAt(row, 0).toString());
            EnviarDatosEmpleadoSeleccionado(id);  // Llama al método para mostrar los detalles del empleado seleccionado
        }
    });
}
//1)CargarTablaEmpleado

//2)EnviarDatosEmpleadoSeleccionado
private void EnviarDatosEmpleadoSeleccionado(int idEmpleado) {
    // Consulta SQL para obtener los datos del empleado según su IdEmpleado
    String sql = "SELECT E.Nombres, E.Apellidos, N.Nombre AS Nacionalidad, " +
                 "       S.Nombre AS Sexo, D.Nombre AS Distrito, E.Password, E.NumDoc, " +
                 "       E.Telefono, E.Correo, E.Direccion, E.Usuario " +
                 "FROM Empleado E " +
                 "JOIN Nacionalidad N ON E.CodNacio = N.CodNacio " +
                 "JOIN Sexo S ON E.CodSexo = S.CodSexo " +
                 "JOIN Distrito D ON E.CodDistrito = D.CodDistrito " +
                 "WHERE E.IdEmpleado = ?";

    try ( Connection cn = Conexion.establecerConexion();
          PreparedStatement pst = cn.prepareStatement(sql) ) {

        pst.setInt(1, idEmpleado);  // Establece el IdEmpleado en la consulta
        try ( ResultSet rs = pst.executeQuery() ) {
            if (rs.next()) {
                // Asigna los datos de la base de datos a los campos de texto
                txtNombres.setText(rs.getString("Nombres"));
                txtApellidos.setText(rs.getString("Apellidos"));
                cbxNacionalidad.setSelectedItem(rs.getString("Nacionalidad"));
                DistritoEmpleado.setSelectedItem(rs.getString("Distrito"));
                txtContraseña.setText(rs.getString("Password"));
                txtNumDoc.setText(rs.getString("NumDoc"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtCorreo.setText(rs.getString("Correo"));
                txtDireccion.setText(rs.getString("Direccion"));
                txtUsuario.setText(rs.getString("Usuario"));

                // Asigna el valor del género (sexo) a los radio buttons
                String sexo = rs.getString("Sexo");
                if ("Masculino".equalsIgnoreCase(sexo)) {
                    HombreEmpleado.setSelected(true);  // Si el sexo es "Masculino", selecciona el radio button "Hombre"
                } else if ("Femenino".equalsIgnoreCase(sexo)) {
                    MujerEmpleado.setSelected(true);  // Si el sexo es "Femenino", selecciona el radio button "Mujer"
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Imprime la traza del error
    }
}

//2)EnviarDatosEmpleadoSeleccionado
//3)getCodNacio
    private int getCodNacio(String nacionalidad) {
    int codNacio = 0;  // Valor por defecto si no se encuentra la nacionalidad
    try ( Connection cn = Conexion.establecerConexion();
          PreparedStatement pst = cn.prepareStatement("SELECT CodNacio FROM Nacionalidad WHERE Nombre = ?") ) {

        pst.setString(1, nacionalidad);  // Establece la nacionalidad en la consulta
        try ( ResultSet rs = pst.executeQuery() ) {
            if (rs.next()) {
                codNacio = rs.getInt("CodNacio");  // Obtiene el código de la nacionalidad
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Imprime la traza del error
    }
    return codNacio;
}
//4)getCodSexo
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



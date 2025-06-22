package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import Gestion.Empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
public class control_Usuario {
    //----------------Metodo para LOGIN--------------------
    public boolean loginExiste(Empleado emp){
        boolean rspta=false;
        
        Connection cn = Conexion.establecerConexion();
        String sql = "select Usuario,Password from Empleado where Usuario='"+emp.getUsuario()+"' and Password='"+emp.getPassword()+"'";        
        Statement st;
        try{
            
            st=cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){//
                rspta=true;
            
            }
            
        } catch(SQLException e){
            System.out.println("Error al inicial sesión");
            JOptionPane.showMessageDialog(null, "Error al inicial sesión");
        }
        return rspta;
    }
    //-----------------Metodo para Guardar Usuario/Empleado-------------------------------
    public boolean guardar(Empleado emp){
        boolean rpta=false;
        Connection cn=conexion.Conexion.establecerConexion();
        try {
            PreparedStatement consulta =cn.prepareStatement("insert into dbo.Empleado (IdEmpleado,CodNacio, CodSexo, CodDistrito, Nombres, Apellidos, Password, NumDoc, Telefono, Correo, Direccion) "
                    + "                                      values(?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1, emp.getIdEmpleado());
            consulta.setInt(2, emp.getCodNacio());
            consulta.setInt(3, emp.getCodSexo());
            consulta.setInt(4, emp.getCodDistrito());
            consulta.setString(5, emp.getNombres());
            consulta.setString(6, emp.getApellidos());
            consulta.setString(6, emp.getUsuario());
            consulta.setString(7, emp.getPassword());
            consulta.setString(8, emp.getNumDoc());
            consulta.setInt(9, emp.getTelefono());
            consulta.setString(10, emp.getCorreo());
            consulta.setString(11, emp.getDireccion());
            
            if(consulta.executeUpdate()>0)rpta=true;
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar Nuevo Usuario: "+ e);
        }
        return rpta;
    }
    
    //-------------Metodo guardado2-------------
    public DefaultTableModel getTablaEmpleados() {
    
    String[] columnas = {
        "ID","Nacionalidad","Sexo","Distrito",
        "Nombres","Apellidos","Usuario","Password",
        "NumDoc","Teléfono","Correo","Dirección"
    };
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    
    String sql = "SELECT IdEmpleado, CodNacio, CodSexo, CodDistrito, " +
                 "Nombres, Apellidos, Usuario, Password, NumDoc, Telefono, Correo, Direccion " +
                 "FROM dbo.Empleado";

    try (Connection cn = Conexion.establecerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {


        while (rs.next()) {
            Object[] fila = new Object[12];
            fila[0] = rs.getInt("IdEmpleado");
            fila[1] = rs.getInt("CodNacio");
            fila[2] = rs.getInt("CodSexo");
            fila[3] = rs.getInt("CodDistrito");
            fila[4] = rs.getString("Nombres");
            fila[5] = rs.getString("Apellidos");
            fila[6] = rs.getString("Usuario");
            fila[7] = rs.getString("Password");
            fila[8] = rs.getString("NumDoc");
            fila[9] = rs.getInt("Telefono");
            fila[10] = rs.getString("Correo");
            fila[11] = rs.getString("Direccion");
            modelo.addRow(fila);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return modelo;
}

    
}

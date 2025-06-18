package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import Gestion.Empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
}

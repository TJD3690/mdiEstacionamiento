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
    /*public boolean guardar(Empleado emp) {
        boolean respuesta = false;
        Connection cn = conexion.Conexion.establecerConexion();
        try {
            String sqlMax = "SELECT COALESCE(MAX(IdEmpleado), 0) FROM dbo.Empleado";
            PreparedStatement psMax = cn.prepareStatement(sqlMax);
            ResultSet rs = psMax.executeQuery();
            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
            rs.close();
            psMax.close();
            
            PreparedStatement consulta = cn.prepareStatement("INSERT INTO dbo.Empleado " +
            "(IdEmpleado, CodNacio, CodSexo, CodDistrito,Nombres, Apellidos, Password, NumDoc, Telefono, Correo, Direccion, Usuario) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1, nextId);
            consulta.setInt(2, emp.getCodNacio());
            consulta.setInt(3, emp.getCodSexo());
            consulta.setInt(4, emp.getCodDistrito());
            consulta.setString(5, emp.getNombres());
            consulta.setString(6, emp.getApellidos());
            consulta.setString(7, emp.getPassword());
            consulta.setString(8, emp.getNumDoc());
            consulta.setInt(9, emp.getTelefono());
            consulta.setString(10, emp.getCorreo());
            consulta.setString(11, emp.getDireccion());
            consulta.setString(12, emp.getUsuario());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario/empleado: " + e);
        }

        return respuesta;
    }
*/
    /* 
    public boolean existeEmpleado(String categoria) {
        boolean respuesta = false;
        String sql = "select descripcion from tb_categoria where descripcion = '" + categoria + "';";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar cartegoria: " + e);
        }
        return respuesta;
    }*/
        
}
